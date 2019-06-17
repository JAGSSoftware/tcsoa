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

import java.io.IOException;

import com.teamcenter.soa.SoaConstants;
import com.teamcenter.soa.client.Connection;
import com.teamcenter.soa.client.CredentialManager;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionBeanTest {

    private ConnectionBean underTest;

    @Mock
    private Credentials credentials;

    private Connection connection;

    @Before
    public void setUp() throws IOException {
        final CredentialManager credentialManager = mock(CredentialManager.class);
        underTest = new ConnectionBean();
        connection = new Connection("http://tc.host.com", credentialManager, SoaConstants.REST, SoaConstants.HTTP);
        underTest.setConnection(connection);
        underTest.setCredentials(credentials);
        underTest.setDiscriminator("App-X-discriminator");
    }

    @Test
    public void getCredentials() {
        assertThat(underTest.getCredentials()).isEqualTo(credentials);
    }

    @Test
    public void getDiscriminator() {
        assertThat(underTest.getDiscriminator()).isEqualTo("App-X-discriminator");
    }

    @Test
    @Ignore("Funcionalidad para ser evaluada y crear connection usando un fichero de propiedades")
    public void serializeConnection() throws IOException {
        connection.serialize("serializedConnection.properties");
    }

    @Test
    public void getConnection() {
        assertThat(underTest.getConnection()).isEqualTo(connection);
    }
}
