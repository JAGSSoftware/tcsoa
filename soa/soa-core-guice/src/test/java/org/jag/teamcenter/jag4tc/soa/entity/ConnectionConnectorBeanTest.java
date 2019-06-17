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

import com.teamcenter.schemas.soa._2006_03.exceptions.InvalidCredentialsException;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.strong.core.SessionService;
import com.teamcenter.soa.client.Connection;
import com.teamcenter.soa.exceptions.CanceledOperationException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionConnectorBeanTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private ConnectionConnectorBean underTest;

    @Mock
    private ConnectionPoolBean connectionPoolBean;

    @Mock
    private SessionServiceProviderBean sessionServiceProviderBean;

    @Captor
    private ArgumentCaptor<ConnectionBean> connectionBeanCaptor;

    @Test
    public void connectHttp() throws CanceledOperationException {
        final ConnectionConfiguration connectionConfiguration = mock(ConnectionConfiguration.class);
        final Credentials credentials = mock(Credentials.class);

        when(connectionConfiguration.getHost()).thenReturn("http://tc.host.com");
        when(connectionConfiguration.getDiscriminator()).thenReturn("App-X-Discriminator");
        when(connectionConfiguration.getProtocol()).thenReturn(Protocol.HTTP);

        underTest.connect(connectionConfiguration, credentials);

        verify(connectionPoolBean).setConnectionBean(connectionBeanCaptor.capture());

        final ConnectionBean connectionBean = connectionBeanCaptor.getValue();
        final Connection connection = connectionBean.getConnection();
        assertThat(connection.getHostPath(), equalTo("http://tc.host.com/"));
        assertThat(connection.getDiscriminator(), equalTo("App-X-Discriminator"));
        assertThat(connectionBean.getCredentials(), equalTo(credentials));
        assertThat(connectionBean.getDiscriminator(), equalTo("App-X-Discriminator"));
    }

    @Test
    @Ignore("Requires updating the dependencies")
    public void connectTccs() throws CanceledOperationException {
        final ConnectionConfiguration connectionConfiguration = mock(TccsConnectionConfiguration.class);
        final Credentials credentials = mock(Credentials.class);

        when(connectionConfiguration.getHost()).thenReturn("tccs://tc.host.com:8080/tc");
        when(connectionConfiguration.getDiscriminator()).thenReturn("App-X-Discriminator");
        when(connectionConfiguration.getProtocol()).thenReturn(Protocol.TCCS);

        underTest.connect(connectionConfiguration, credentials);

        verify((TccsConnectionConfiguration) connectionConfiguration).getEnvName();

        verify(connectionPoolBean).setConnectionBean(connectionBeanCaptor.capture());

        final ConnectionBean connectionBean = connectionBeanCaptor.getValue();
        final Connection connection = connectionBean.getConnection();
        assertThat(connection.getHostPath(), equalTo("tccs://tc.host.com:8080/tc/"));
        assertThat(connection.getDiscriminator(), equalTo("App-X-Discriminator"));
        assertThat(connectionBean.getCredentials(), equalTo(credentials));
        assertThat(connectionBean.getDiscriminator(), equalTo("App-X-Discriminator"));
        fail("Not yet implemented");
    }

    @Test
    public void login() throws SessionLoginException, InvalidCredentialsException {
        final ConnectionBean connectionBean = mock(ConnectionBean.class);
        final Credentials credentials = mock(Credentials.class);
        final SessionService sessionService = mock(SessionService.class);

        when(connectionPoolBean.getConnectionBean()).thenReturn(connectionBean);
        when(connectionBean.getCredentials()).thenReturn(credentials);
        when(connectionBean.getDiscriminator()).thenReturn("App-X-Discriminator");
        when(sessionServiceProviderBean.getService()).thenReturn(sessionService);
        when(credentials.getUsername()).thenReturn("MyUsername");
        when(credentials.getPassword()).thenReturn("MyPassword");
        when(credentials.getGroup()).thenReturn("MyGroup");
        when(credentials.getRole()).thenReturn("MyRole");

        underTest.login();

        verify(connectionBean).getCredentials();
        verify(connectionBean).getDiscriminator();
        verify(sessionService).login(eq("MyUsername"), eq("MyPassword"), eq("MyGroup"), eq("MyRole"),
                eq(""), eq("App-X-Discriminator"));
    }

    @Test
    public void loginWithException() throws InvalidCredentialsException, SessionLoginException {
        final ConnectionBean connectionBean = mock(ConnectionBean.class);
        final Credentials credentials = mock(Credentials.class);
        final SessionService sessionService = mock(SessionService.class);

        when(connectionPoolBean.getConnectionBean()).thenReturn(connectionBean);
        when(connectionBean.getCredentials()).thenReturn(credentials);
        when(connectionBean.getDiscriminator()).thenReturn("App-X-Discriminator");
        when(sessionServiceProviderBean.getService()).thenReturn(sessionService);
        when(credentials.getUsername()).thenReturn("MyUsername");
        when(credentials.getPassword()).thenReturn("MyPassword");
        when(credentials.getGroup()).thenReturn("MyGroup");
        when(credentials.getRole()).thenReturn("MyRole");
        when(sessionService.login(anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenThrow(new InvalidCredentialsException("Fake exception"));

        expectedException.expect(SessionLoginException.class);
        expectedException.expectMessage("Fake exception");

        underTest.login();

        verify(connectionBean).getCredentials();
        verify(connectionBean).getDiscriminator();
    }

    @Test
    public void logout() throws ServiceException {
        final SessionService sessionService = mock(SessionService.class);
        when(sessionServiceProviderBean.getService()).thenReturn(sessionService);

        underTest.logout();

        verify(sessionService).logout();
    }

    @Test
    public void logoutWithException() throws ServiceException {
        final SessionService sessionService = mock(SessionService.class);
        when(sessionServiceProviderBean.getService()).thenReturn(sessionService);
        when(sessionService.logout()).thenThrow(new ServiceException("Fake exception"));

        underTest.logout();

        verify(sessionService).logout();
    }
}
