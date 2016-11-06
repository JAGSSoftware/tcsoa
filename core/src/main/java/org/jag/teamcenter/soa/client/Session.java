/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.client;

import java.util.Locale;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.jag.teamcenter.soa.types.Protocol;

import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.strong.core.SessionService;
import com.teamcenter.services.strong.core._2011_06.Session.Credentials;
import com.teamcenter.services.strong.core._2011_06.Session.LoginResponse;
import com.teamcenter.soa.client.Connection;
import com.teamcenter.soa.client.CredentialManager;

/**
 * @author Jose A. Garcia Sanchez
 */
public class Session {
    private static final Logger LOGGER = Logger.getLogger(Session.class);
    private final String hostPath;
    private final CredentialManager credentialManager;
    private final Credentials credentials;
    private final Connection connection;

    /**
     * @param builder
     */
    private Session(final Builder builder) {
        this.hostPath = builder.uri;
        this.credentialManager = Factory.newCredentialManager(new FactoryBuilder(builder));
        this.credentials = Factory.newCredentials(new FactoryBuilder(builder));

        this.connection = new Connection(hostPath, credentialManager, "REST", Protocol
                .getProtocolFromHostpath(hostPath).value());
    }

    /**
     * @return
     */
    public Connection connection() {
        return this.connection;
    }

    /**
     * @return
     */
    public SessionService sessionService() {
        return SessionService.getService(connection);
    }

    /**
     * @throws InvalidCredentialsException
     */
    public void login() throws InvalidCredentialsException {
        try {
			printLoginResponse(sessionService().login(credentials));
		} catch (com.teamcenter.schemas.soa._2006_03.exceptions.InvalidCredentialsException e) {
			throw new InvalidCredentialsException(credentials, e);
		}
    }

    /**
     * @throws InvalidCredentialsException
     */
    public void loginSSO() throws InvalidCredentialsException {
        try {
			printLoginResponse(sessionService().loginSSO(credentials));
		} catch (com.teamcenter.schemas.soa._2006_03.exceptions.InvalidCredentialsException e) {
			throw new InvalidCredentialsException(credentials, e);
		}
    }

    /**
     * @param response
     */
    private void printLoginResponse(final LoginResponse response) {
        for (int i = 0, n = response.partialErrors.sizeOfPartialErrors(); i < n; i++) {
            LOGGER.info(String.format("Partial Error #%d: [%s]", i + 1, response.partialErrors.getPartialError(i)));
        }

        for (final Object key : response.serverInfo.keySet()) {
            LOGGER.info(String.format("[%s]: [%s]", key, response.serverInfo.get(key)));
        }
    }

    /**
     *
     */
    public void logout() {
        try {
			sessionService().logout();
		} catch (ServiceException e) {
			throw new LogoutException(e);
		}
    }

    /**
     * @author Jose A. Garcia Sanchez
     */
    public static class Builder {
        private final String uri;

        private String username = "";
        private String password = "";
        private String group = "";
        private String role = "";
        private String discriminator = "";
        private Locale locale = Locale.getDefault();
        private boolean ssoCredentials = false;
        private String ssoServerUrl = "";
        private String ssoAppId = "";

        /**
         * @param uri
         */
        public Builder(final String uri) {
            Validate.notBlank(uri);
            this.uri = uri;
        }

        /**
         * @param username
         * @return
         */
        public Builder username(final String username) {
            Validate.notBlank(username);
            this.username = username;

            return this;
        }

        /**
         * @param password
         * @return
         */
        public Builder password(final String password) {
            Validate.notNull(password);
            this.password = password;

            return this;
        }

        /**
         * @param group
         * @return
         */
        public Builder group(final String group) {
            Validate.notNull(group);
            this.group = group;

            return this;
        }

        /**
         * @param role
         * @return
         */
        public Builder role(final String role) {
            Validate.notNull(role);
            this.role = role;

            return this;
        }

        /**
         * @param discriminator
         * @return
         */
        public Builder discriminator(final String discriminator) {
            Validate.notNull(discriminator);
            this.discriminator = discriminator;

            return this;
        }

        /**
         * @param locale
         * @return
         */
        public Builder locale(final Locale locale) {
            Validate.notNull(locale);
            this.locale = new Locale(locale.getLanguage(), locale.getCountry(), locale.getVariant());

            return this;
        }

        /**
         * @param ssoServerUrl
         * @param ssoAppId
         * @return
         */
        public Builder ssoCredentials(final String ssoServerUrl, final String ssoAppId) {
            Validate.notBlank(ssoServerUrl);
            Validate.notBlank(ssoAppId);
            this.ssoCredentials = true;
            this.ssoServerUrl = ssoServerUrl;
            this.ssoAppId = ssoAppId;

            return this;
        }

        /**
         * @return
         */
        public Builder stdCredentials() {
            this.ssoCredentials = false;

            return this;
        }

        /**
         * @return
         */
        public Session build() {
            return new Session(this);
        }


    }

    /**
     * @author Jose A. Garcia Sanchez
     *
     */
    static class FactoryBuilder implements Factory.Builder {
        private final Builder builder;

        FactoryBuilder(final Builder builder) {
            this.builder = builder;
        }

        @Override
        public String username() {
            return builder.username;
        }

        @Override
        public String password() {
            return builder.password;
        }

        @Override
        public String group() {
            return builder.group;
        }

        @Override
        public String role() {
            return builder.role;
        }

        @Override
        public String discriminator() {
            return builder.discriminator;
        }

        @Override
        public Locale locale() {
            return (Locale) builder.locale.clone();
        }

        @Override
        public boolean ssoCredentials() {
            return builder.ssoCredentials;
        }

        @Override
        public String ssoServerUrl() {
            return builder.ssoServerUrl;
        }

        @Override
        public String ssoAppId() {
            return builder.ssoAppId;
        }
    }
}
