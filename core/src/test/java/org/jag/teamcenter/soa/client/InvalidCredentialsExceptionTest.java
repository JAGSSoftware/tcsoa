/*
 * (c) 2019 - José A. García Sánchez
 */
package org.jag.teamcenter.soa.client;

import com.teamcenter.services.strong.core._2011_06.Session;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidCredentialsExceptionTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private InvalidCredentialsException underTest;

    @Before
    public void setUp() {
        final Session.Credentials credentials = new Session.Credentials();
        credentials.user = "User";
        credentials.password = "P4ssw0rd";
        credentials.descrimator = "MyApp";
        credentials.group = "My Group";
        credentials.role = "My role";
        credentials.locale = "es";

        final com.teamcenter.schemas.soa._2006_03.exceptions.InvalidCredentialsException invalidCredentialsException =
                new com.teamcenter.schemas.soa._2006_03.exceptions.InvalidCredentialsException("Fake underTest");

        underTest = new InvalidCredentialsException(credentials, invalidCredentialsException);
    }

    @Test
    public void constructWithNullCredentials() {
        final com.teamcenter.schemas.soa._2006_03.exceptions.InvalidCredentialsException invalidCredentialsException =
                new com.teamcenter.schemas.soa._2006_03.exceptions.InvalidCredentialsException("Fake exception");

        expectedException.expect(NullPointerException.class);

        new InvalidCredentialsException(null, invalidCredentialsException);
    }

    @Test
    public void username() {
        assertThat(underTest.username()).isEqualTo("User");
    }

    @Test
    public void password() {
        assertThat(underTest.password()).isEqualTo("P4ssw0rd");
    }

    @Test
    public void group() {
        assertThat(underTest.group()).isEqualTo("My Group");
    }

    @Test
    public void role() {
        assertThat(underTest.role()).isEqualTo("My role");
    }

    @Test
    public void testToString() {
        assertThat(underTest.toString())
                .isEqualTo("Credentials{user: [User], password: [P4ssw0rd], group: [My Group], role: [My role]}");
    }
}
