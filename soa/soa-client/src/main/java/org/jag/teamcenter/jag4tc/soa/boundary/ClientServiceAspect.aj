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
package org.jag.teamcenter.jag4tc.soa.boundary;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Timer;
import org.jag.teamcenter.jag4tc.soa.control.Arguments;
import org.jag.teamcenter.jag4tc.soa.entity.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public aspect ClientServiceAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceAspect.class);
    private static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();
    private static final ScheduledReporter REPORTER = Slf4jReporter.forRegistry(METRIC_REGISTRY)
            .outputTo(LOGGER)
            .build();

    static {
        REPORTER.start(10, TimeUnit.SECONDS);
    }

    private Timer.Context context;

    pointcut callGetCredentialsFrom(Arguments arguments, ClientServiceBF clientService)
            : call(Credentials ClientServiceBF.getCredentialsFrom(Arguments)) && args(arguments)
            && target(clientService);

    before(Arguments arguments, ClientServiceBF clientService): callGetCredentialsFrom(arguments, clientService) {
        context = METRIC_REGISTRY.timer(MetricRegistry.name(ClientService.class, "getCredentialsFrom")).time();
    }

    Credentials around(Arguments arguments, ClientServiceBF clientService):
            callGetCredentialsFrom(arguments, clientService) {
        return proceed(arguments, clientService);
    }

    after(Arguments arguments, ClientServiceBF clientService): callGetCredentialsFrom(arguments, clientService) {
        context.stop();
    }
}
