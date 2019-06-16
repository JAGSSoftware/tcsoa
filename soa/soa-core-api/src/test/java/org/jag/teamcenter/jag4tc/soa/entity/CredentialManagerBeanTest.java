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
import com.teamcenter.schemas.soa._2006_03.exceptions.InvalidUserException;
import com.teamcenter.soa.client.CredentialManager;
import com.teamcenter.soa.exceptions.CanceledOperationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CredentialManagerBeanTest {

    private CredentialManagerBean underTest;

    @Mock
    private Credentials credentials;

    @Before
    public void setUp() {
        underTest = new CredentialManagerBean(credentials, "App-X-discriminator");
    }

    @Test
    public void getCredentialType() {
        assertThat(underTest.getCredentialType()).isEqualTo(CredentialManager.CLIENT_CREDENTIAL_TYPE_STD);
    }

    @Test
    public void setCredentialType() {
        assertThat(underTest.getCredentialType()).isEqualTo(CredentialManager.CLIENT_CREDENTIAL_TYPE_STD);

        underTest.setCredentialType(CredentialManager.CLIENT_CREDENTIAL_TYPE_SSO);
        assertThat(underTest.getCredentialType()).isEqualTo(CredentialManager.CLIENT_CREDENTIAL_TYPE_SSO);
    }

    @Test
    public void getCredentialsByInvalidCredentialsException() throws CanceledOperationException {
        when(credentials.getUsername()).thenReturn("MyUsername");
        when(credentials.getPassword()).thenReturn("MyPassword");
        when(credentials.getGroup()).thenReturn("MyGroup");
        when(credentials.getRole()).thenReturn("MyRole");

        assertThat(underTest.getCredentials(new InvalidCredentialsException("Fake exception")))
                .containsExactly("MyUsername", "MyPassword", "MyGroup", "MyRole", "App-X-discriminator");
    }

    @Test
    public void getCredentialsByInvalidUserException() throws CanceledOperationException {
        when(credentials.getUsername()).thenReturn("MyUsername");
        when(credentials.getPassword()).thenReturn("MyPassword");
        when(credentials.getGroup()).thenReturn("MyGroup");
        when(credentials.getRole()).thenReturn("MyRole");

        assertThat(underTest.getCredentials(new InvalidUserException("Fake exception")))
                .containsExactly("MyUsername", "MyPassword", "MyGroup", "MyRole", "App-X-discriminator");
    }

    @Test
    public void setUserPassword() {
        underTest.setUserPassword("MyUsername", "MyPassword", "New-App-X-discriminator");
    }

    @Test
    public void setGroupRole() {
        underTest.setGroupRole("MyGroup", "MyRole");
    }
}
