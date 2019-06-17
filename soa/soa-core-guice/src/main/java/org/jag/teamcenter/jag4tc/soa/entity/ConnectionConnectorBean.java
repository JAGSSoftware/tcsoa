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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ConnectionConnectorBean implements ConnectionConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionConnectorBean.class);
    private final ConnectionPoolBean connectionPool;
    private final SessionServiceProviderBean sessionServiceProvider;

    @Inject
    ConnectionConnectorBean(final ConnectionPoolBean connectionPool, final SessionServiceProviderBean sessionService) {
        this.connectionPool = connectionPool;
        this.sessionServiceProvider = sessionService;
    }

    @Override
    public void connect(final ConnectionConfiguration connectionConfiguration, final Credentials credentials) {
        // TODO Completar función
    }

    @Override
    public void login() throws SessionLoginException {
        // TODO Completar función
    }

    @Override
    public void logout() {
        // TODO Completar función
    }
}
