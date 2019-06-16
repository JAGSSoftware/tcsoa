/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.client;

import java.util.Locale;

import com.teamcenter.services.strong.core._2011_06.Session.Credentials;
import com.teamcenter.soa.client.CredentialManager;
import com.teamcenter.soa.client.SsoCredentials;

/**
 * @author Jose A. Garcia Sanchez
 */
@Deprecated
final class Factory {
    public static CredentialManager newCredentialManager(final Builder builder) {
        final CredentialManager credentialManager;
        if (builder.ssoCredentials()) {
            credentialManager = new SsoCredentials(builder.ssoServerUrl(), builder.ssoAppId());
        } else {
            credentialManager = new CredentialManagerImpl.Builder(builder.username(), builder.password())
                    .group(builder.group()).role(builder.role()).discriminator(builder.discriminator())
                    .standardCredentialType().build();
        }
        return credentialManager;
    }

    public static Credentials newCredentials(final Builder builder) {
        final Credentials credentials = new Credentials();

        credentials.user = builder.username();
        credentials.password = builder.password();
        credentials.group = builder.group();
        credentials.role = builder.role();
        credentials.locale = builder.locale().getLanguage();
        credentials.descrimator = builder.discriminator();

        return credentials;
    }

    /**
     * @author Jose A. Garcia Sanchez
     */
    public interface Builder {
        String username();
        String password();
        String group();
        String role();
        String discriminator();
        Locale locale();
        boolean ssoCredentials();
        String ssoServerUrl();
        String ssoAppId();
    }
}
