/*
 * MIT License
 *
 * Copyright (c) 2019 José A. García Sánchez
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
package org.jag.teamcenter.jag4tc.soa;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jag.teamcenter.jag4tc.soa.boundary.BoundaryClientModule;
import org.jag.teamcenter.jag4tc.soa.boundary.ClientServiceBF;
import org.jag.teamcenter.jag4tc.soa.control.Arguments;
import org.jag.teamcenter.jag4tc.soa.control.ControlClientModule;
import org.jag.teamcenter.jag4tc.soa.entity.ConnectionConfiguration;
import org.jag.teamcenter.jag4tc.soa.entity.ConnectionConfigurationFactory;
import org.jag.teamcenter.jag4tc.soa.entity.ConnectionConnector;
import org.jag.teamcenter.jag4tc.soa.entity.Credentials;
import org.jag.teamcenter.jag4tc.soa.entity.EntityModule;
import org.jag.teamcenter.jag4tc.soa.entity.SessionLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private final ClientServiceBF clientService;
    private final String[] args;
    private final ConnectionConfigurationFactory connectionConfigurationFactory;
    private final ConnectionConnector connectionConnector;

    public static void main(String[] args) {
        final Injector injector =
                Guice.createInjector(new EntityModule(), new BoundaryClientModule(), new ControlClientModule());

        final ConnectionConfigurationFactory connectionConfigurationFactory =
                injector.getInstance(ConnectionConfigurationFactory.class);
        final ConnectionConnector connectionConnector = injector.getInstance(ConnectionConnector.class);
        final ClientServiceBF clientService = injector.getInstance(ClientServiceBF.class);

        new Main(clientService, args, connectionConfigurationFactory, connectionConnector).run();
    }

    public Main(final ClientServiceBF clientService, final String[] arguments,
            final ConnectionConfigurationFactory connectionConfigurationFactory,
            ConnectionConnector connectionConnector) {
        this.clientService = clientService;
        this.args = arguments;
        this.connectionConfigurationFactory = connectionConfigurationFactory;
        this.connectionConnector = connectionConnector;
    }

    public void run() {
        final Arguments arguments = clientService.parse(args);
        final ConnectionConfiguration connectionConfiguration = connectionConfigurationFactory
                .createConnectionConfiguration(arguments.getHost(), "discriminator");
        final Credentials credentials = clientService.getCredentialsFrom(arguments);

        connectionConnector.connect(connectionConfiguration, credentials);
        try {
            connectionConnector.login();
            connectionConnector.logout();
        } catch (SessionLoginException e) {
            LOGGER.info("Exception happened by login", e);
        }
    }
}
