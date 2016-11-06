/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.client;

import com.teamcenter.schemas.soa._2006_03.exceptions.InvalidCredentialsException;
import com.teamcenter.schemas.soa._2006_03.exceptions.InvalidUserException;
import com.teamcenter.soa.client.CredentialManager;
import com.teamcenter.soa.exceptions.CanceledOperationException;

/**
 * @author Jose A. Garcia Sanchez
 */
class CredentialManagerImpl implements CredentialManager {
    private final int credentialType;
    private final String username;
    private final String password;
    private final String group;
    private final String role;
    private final String discriminator;

    /**
     * 
     */
    private CredentialManagerImpl(final Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.group = builder.group;
        this.role = builder.role;
        this.discriminator = builder.discriminator;
        this.credentialType = builder.credentialType;
    }

    /*
     * (non-Javadoc)
     * @see com.teamcenter.soa.client.CredentialManager#getCredentialType()
     */
    @Override
    public int getCredentialType() {
        return credentialType;
    }

    /*
     * (non-Javadoc)
     * @see com.teamcenter.soa.client.CredentialManager#getCredentials(com.teamcenter.schemas.soa._2006_03.exceptions.
     * InvalidCredentialsException)
     */
    @Override
    public String[] getCredentials(final InvalidCredentialsException exception) throws CanceledOperationException {
        return new String[] {username, password, group, role };
    }

    /*
     * (non-Javadoc)
     * @see com.teamcenter.soa.client.CredentialManager#getCredentials(com.teamcenter.schemas.soa._2006_03.exceptions.
     * InvalidUserException)
     */
    @Override
    public String[] getCredentials(final InvalidUserException exception) throws CanceledOperationException {
        return new String[] {username, password, group, role, discriminator };
    }

    /*
     * (non-Javadoc)
     * @see com.teamcenter.soa.client.CredentialManager#setGroupRole(java.lang.String, java.lang.String)
     */
    @Override
    public void setGroupRole(final String group, final String role) {
    }

    /*
     * (non-Javadoc)
     * @see com.teamcenter.soa.client.CredentialManager#setUserPassword(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public void setUserPassword(final String username, final String password, final String discriminator) {
    }

    /**
     * @author Jose A. Garcia Sanchez
     */
    public static class Builder {
        private final String username;
        private final String password;
        
        private String group = "";
        private String role = "";
        private String discriminator = "";
        private int credentialType = CLIENT_CREDENTIAL_TYPE_STD;

        /**
         * @param username
         * @param password
         */
        public Builder(final String username, final String password) {
            this.username = username;
            this.password = password;
        }
        
        /**
         * @return
         */
        public CredentialManagerImpl build() {
            return new CredentialManagerImpl(this);
        }

        /**
         * @param group
         * @return
         */
        public Builder group(final String group) {
            this.group = group;
            
            return this;
        }
        
        /**
         * @param role
         * @return
         */
        public Builder role(final String role) {
            this.role = role;
            
            return this;
        }
        
        /**
         * @param discriminator
         * @return
         */
        public Builder discriminator(final String discriminator) {
            this.discriminator = discriminator;
            
            return this;
        }
        
        public Builder standardCredentialType() {
            this.credentialType = CLIENT_CREDENTIAL_TYPE_STD;
            
            return this;
        }
        
        /**
         * @return
         */
        public Builder ssoCredentialType() {
            this.credentialType = CLIENT_CREDENTIAL_TYPE_SSO;
            
            return this;
        }
    }        
}
