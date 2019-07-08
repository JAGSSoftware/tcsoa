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

import java.util.Arrays;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jag.teamcenter.jag4tc.soa.boundary.BoundaryClientModule;
import org.jag.teamcenter.jag4tc.soa.boundary.ClientServiceBF;
import org.jag.teamcenter.jag4tc.soa.boundary.PingESI;
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
    private static final MetricRegistry METRICS = new MetricRegistry();
    private final ClientServiceBF clientService;
    private final String[] args;
    private final ConnectionConfigurationFactory connectionConfigurationFactory;
    private final ConnectionConnector connectionConnector;
    private final PingESI pingService;

    public static void main(String[] args) {
        final Injector injector =
                Guice.createInjector(new EntityModule(), new BoundaryClientModule(), new ControlClientModule());

        final ConnectionConfigurationFactory connectionConfigurationFactory =
                injector.getInstance(ConnectionConfigurationFactory.class);
        final ConnectionConnector connectionConnector = injector.getInstance(ConnectionConnector.class);
        final ClientServiceBF clientService = injector.getInstance(ClientServiceBF.class);
        final PingESI pingService = injector.getInstance(PingESI.class);

        new Main(args, clientService, connectionConfigurationFactory, connectionConnector, pingService).run();
    }

    public Main(final String[] arguments, final ClientServiceBF clientService,
            final ConnectionConfigurationFactory connectionConfigurationFactory,
            final ConnectionConnector connectionConnector, final PingESI pingService) {
        this.args = Arrays.copyOf(arguments, arguments.length);
        this.clientService = clientService;
        this.connectionConfigurationFactory = connectionConfigurationFactory;
        this.connectionConnector = connectionConnector;
        this.pingService = pingService;
    }

    public void run() {
        final Arguments arguments = clientService.parse(args);
        if (arguments == null) {
            return;
        }
        final ConnectionConfiguration connectionConfiguration = connectionConfigurationFactory
                .createConnectionConfiguration(arguments.getHost(), "discriminator");
        final Credentials credentials = clientService.getCredentialsFrom(arguments);

        pingService.ping(connectionConfiguration.getHost());
        connectionConnector.connect(connectionConfiguration, credentials);
        try {
            connectionConnector.login();
        } catch (SessionLoginException e) {
            LOGGER.info("Exception happened by login", e);
            return;
        }

        connectionConnector.logout();
    }
}
