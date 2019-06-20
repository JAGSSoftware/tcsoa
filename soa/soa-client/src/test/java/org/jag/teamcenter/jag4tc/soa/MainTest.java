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

import com.teamcenter.schemas.soa._2006_03.exceptions.InvalidCredentialsException;
import org.jag.teamcenter.jag4tc.soa.boundary.ClientServiceBF;
import org.jag.teamcenter.jag4tc.soa.control.Arguments;
import org.jag.teamcenter.jag4tc.soa.entity.ConnectionConfiguration;
import org.jag.teamcenter.jag4tc.soa.entity.ConnectionConfigurationFactory;
import org.jag.teamcenter.jag4tc.soa.entity.ConnectionConnector;
import org.jag.teamcenter.jag4tc.soa.entity.Credentials;
import org.jag.teamcenter.jag4tc.soa.entity.SessionLoginException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainTest {

    private Main main;
    @Mock
    private ClientServiceBF clientService;
    @Mock
    private ConnectionConfigurationFactory connectionConfigurationFactory;
    @Mock
    private ConnectionConnector connectionConnector;
    private final String[] args = {};

    @Before
    public void setUp() {

        main = new Main(clientService, args, connectionConfigurationFactory, connectionConnector);
    }

    @Test
    public void test() throws SessionLoginException {
        final Arguments arguments = mock(Arguments.class);
        final ConnectionConfiguration connectionConfiguration = mock(ConnectionConfiguration.class);
        final Credentials credentials = mock(Credentials.class);

        when(clientService.parse(any(String[].class))).thenReturn(arguments);
        when(arguments.getHost()).thenReturn("tccs://host");
        when(connectionConfigurationFactory.createConnectionConfiguration(anyString(), anyString()))
                .thenReturn(connectionConfiguration);
        when(clientService.getCredentialsFrom(eq(arguments))).thenReturn(credentials);

        main.run();

        verify(clientService).parse(eq(args));
        verify(connectionConnector).connect(eq(connectionConfiguration), eq(credentials));
        verify(connectionConnector).login();
        verify(connectionConnector).logout();
    }

    @Test
    public void testWithLoginException() throws SessionLoginException {
        final Arguments arguments = mock(Arguments.class);
        final ConnectionConfiguration connectionConfiguration = mock(ConnectionConfiguration.class);
        final Credentials credentials = mock(Credentials.class);

        when(clientService.parse(any(String[].class))).thenReturn(arguments);
        when(arguments.getHost()).thenReturn("tccs://host");
        when(connectionConfigurationFactory.createConnectionConfiguration(anyString(), anyString()))
                .thenReturn(connectionConfiguration);
        when(clientService.getCredentialsFrom(eq(arguments))).thenReturn(credentials);
        doThrow(new SessionLoginException(credentials, new InvalidCredentialsException("Fake exception")))
                .when(connectionConnector).login();

        main.run();

        verify(connectionConnector).connect(eq(connectionConfiguration), eq(credentials));
        verify(connectionConnector).login();
        verify(connectionConnector, never()).logout();
    }

    @Test
    public void runWithNullArguments() throws SessionLoginException {
        final ConnectionConfiguration connectionConfiguration = mock(ConnectionConfiguration.class);
        final Credentials credentials = mock(Credentials.class);

        when(clientService.parse(any(String[].class))).thenReturn(null);

        main.run();

        verify(connectionConfigurationFactory, never()).createConnectionConfiguration(anyString(), anyString());
        verify(clientService, never()).getCredentialsFrom(any(Arguments.class));
        verify(connectionConnector, never()).connect(eq(connectionConfiguration), eq(credentials));
        verify(connectionConnector, never()).login();
        verify(connectionConnector, never()).logout();
    }
}