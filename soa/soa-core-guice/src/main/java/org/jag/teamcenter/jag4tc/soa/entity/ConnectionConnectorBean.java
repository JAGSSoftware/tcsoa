/*
 * MIT License
 *
 * Copyright (c) 2018 José A. García Sánchez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.jag.teamcenter.jag4tc.soa.entity;

import com.google.inject.Inject;
import com.teamcenter.schemas.soa._2006_03.exceptions.InvalidCredentialsException;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.strong.core.SessionService;
import com.teamcenter.soa.SoaConstants;
import com.teamcenter.soa.client.Connection;
import com.teamcenter.soa.client.ExceptionHandler;
import com.teamcenter.soa.client.RequestListener;
import com.teamcenter.soa.client.model.ModelEventListener;
import com.teamcenter.soa.client.model.PartialErrorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ConnectionConnectorBean implements ConnectionConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionConnectorBean.class);
    @Inject
    private ConnectionPoolBean connectionPool;

    @Inject
    private SessionServiceProviderBean sessionServiceProvider;

    @Inject
    private ExceptionHandler exceptionHandler;

    @Inject
    private PartialErrorListener partialErrorListener;

    @Inject
    private ModelEventListener modelEventListener;

    @Inject
    private RequestListener requestListener;

    @Override
    public void connect(final ConnectionConfiguration connectionConfiguration, final Credentials credentials) {
        final Connection tcConnection = new Connection(connectionConfiguration.getHost(),
                new CredentialManagerBean(credentials, connectionConfiguration.getDiscriminator()),
                SoaConstants.REST, connectionConfiguration.getProtocol().getProtocolValue());

        if (SoaConstants.TCCS.equals(connectionConfiguration.getProtocol().getProtocolValue())) {
            tcConnection.setOption(Connection.TCCS_ENV_NAME,
                    ((TccsConnectionConfiguration) connectionConfiguration).getEnvName());
        }
        tcConnection.setExceptionHandler(exceptionHandler);
        tcConnection.getModelManager().addPartialErrorListener(partialErrorListener);
        tcConnection.getModelManager().addModelEventListener(modelEventListener);
        Connection.addRequestListener(requestListener);

        final ConnectionBean connection = new ConnectionBean();
        connection.setConnection(tcConnection);
        connection.setCredentials(credentials);
        connection.setDiscriminator(connectionConfiguration.getDiscriminator());

        connectionPool.setConnectionBean(connection);
    }

    @Override
    public void login() throws SessionLoginException {
        final ConnectionBean connectionBean = connectionPool.getConnectionBean();
        final SessionService sessionService = sessionServiceProvider.getService();
        final Credentials credentials = connectionBean.getCredentials();
        try {
            sessionService.login(credentials.getUsername(), credentials.getPassword(), credentials.getGroup(),
                    credentials.getRole(), "", connectionBean.getDiscriminator());
        } catch (InvalidCredentialsException e) {
            LOGGER.error("An exception happened: {}", e.getMessage());
            throw new SessionLoginException(credentials, e);
        }
    }

    @Override
    public void logout() {
        final SessionService sessionService = sessionServiceProvider.getService();
        try {
            sessionService.logout();
        } catch (ServiceException e) {
            LOGGER.error("An exception happened when logging out", e);
        }
    }
}
