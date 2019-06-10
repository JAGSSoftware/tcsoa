/*
 * (c) 2019 - José A. García Sánchez
 */
package org.jag.teamcenter.soa.client;

import com.teamcenter.schemas.soa._2006_03.exceptions.InvalidCredentialsException;
import com.teamcenter.schemas.soa._2006_03.exceptions.InvalidUserException;
import com.teamcenter.soa.client.CredentialManager;
import com.teamcenter.soa.exceptions.CanceledOperationException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CredentialManagerImplTest {

    private CredentialManagerImpl underTest;

    @Before
    public void setUp() {
        underTest = new CredentialManagerImpl.Builder("myUserName", "myPassword")
                .group("myGroup")
                .role("myRole")
                .discriminator("appDiscriminator")
                .standardCredentialType()
                .build();
    }

    @Test
    public void getCredentialTypeStandard() {
        final CredentialManagerImpl credentialManager =
                new CredentialManagerImpl.Builder("myUserName", "myPassword").build();

        assertThat(credentialManager.getCredentialType()).isEqualTo(CredentialManager.CLIENT_CREDENTIAL_TYPE_STD);
    }

    @Test
    public void getCredentialTypeSso() {
        final CredentialManagerImpl credentialManager =
                new CredentialManagerImpl.Builder("myUserName", "myPassword").ssoCredentialType().build();

        assertThat(credentialManager.getCredentialType()).isEqualTo(CredentialManager.CLIENT_CREDENTIAL_TYPE_SSO);
    }

    @Test
    public void getCredentialsWithInvalidCredentialsException() throws CanceledOperationException {
        final InvalidCredentialsException exception = new InvalidCredentialsException();

        final String[] credentials = underTest.getCredentials(exception);

        assertThat(credentials).containsExactly("myUserName", "myPassword", "myGroup", "myRole");
    }

    @Test
    public void getCredentialsWithInvalidUserException() throws CanceledOperationException {
        final InvalidUserException exception = new InvalidUserException();

        final String[] credentials = underTest.getCredentials(exception);

        assertThat(credentials).containsExactly("myUserName", "myPassword", "myGroup", "myRole", "appDiscriminator");
    }

    @Test
    public void setGroupRole() {
        // It does nothing
        underTest.setGroupRole("", "");
    }

    @Test
    public void setUserPassword() {
        // It does nothing
        underTest.setUserPassword("", "", "");
    }
}
