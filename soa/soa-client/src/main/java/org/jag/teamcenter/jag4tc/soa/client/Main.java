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
package org.jag.teamcenter.jag4tc.soa.client;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jag.teamcenter.jag4tc.soa.boundary.CommandLineClientModule;
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
    private final ConnectionConfigurationFactory connectionConfigurationFactory;
    private final ConnectionConnector connectionConnector;

    public static void main(String[] args) {
        final Injector injector = Guice.createInjector(new EntityModule(), new CommandLineClientModule());

        final ConnectionConfigurationFactory connectionConfigurationFactory =
                injector.getInstance(ConnectionConfigurationFactory.class);
        final ConnectionConnector connectionConnector = injector.getInstance(ConnectionConnector.class);

        new Main(connectionConfigurationFactory, connectionConnector).run();
    }

    private Main(final ConnectionConfigurationFactory connectionConfigurationFactory,
            ConnectionConnector connectionConnector) {
        this.connectionConfigurationFactory = connectionConfigurationFactory;
        this.connectionConnector = connectionConnector;
    }

    private void run() {
        final ConnectionConfiguration connectionConfiguration = connectionConfigurationFactory
                .createConnectionConfiguration("http://141.77.189.132:8080/tc", "discriminator");
        final Credentials credentials = new Credentials() {
            @Override
            public String getUsername() {
                return "infodba";
            }

            @Override
            public String getPassword() {
                return "infodba";
            }

            @Override
            public String getGroup() {
                return "";
            }

            @Override
            public String getRole() {
                return "";
            }
        };

        connectionConnector.connect(connectionConfiguration, credentials);
        try {
            connectionConnector.login();
        } catch (SessionLoginException e) {
            LOGGER.info("Exception happened by login", e);
        }

        connectionConnector.logout();
    }
}
