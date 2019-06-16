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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CredentialManagerBean implements CredentialManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialManagerBean.class);
    private final Credentials credentials;
    private String discriminator;
    private int credentialType;

    public CredentialManagerBean(final Credentials credentials, final String discriminator) {
        this.credentials = credentials;
        this.discriminator = discriminator;
        this.credentialType = CredentialManager.CLIENT_CREDENTIAL_TYPE_STD;
    }

    @Override
    public int getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(final int credentialType) {
        this.credentialType = credentialType;
    }

    @Override
    public String[] getCredentials(final InvalidCredentialsException e) throws CanceledOperationException {
        LOGGER.warn("getCredentials() by invalidCredentialsException", e);
        return credentialsAsArray();
    }

    private String[] credentialsAsArray() {
        return new String[]{credentials.getUsername(), credentials.getPassword(), credentials.getGroup(),
                credentials.getRole(), discriminator};
    }

    @Override
    public String[] getCredentials(final InvalidUserException e) throws CanceledOperationException {
        LOGGER.warn("getCredentials() by invalidUserException", e);
        return credentialsAsArray();
    }

    @Override
    public void setUserPassword(final String username, final String password, final String discriminator) {
        LOGGER.trace("setUserPassword(username: [{}], password: [{}], discriminator: [{}])",
                username, password, discriminator);
    }

    @Override
    public void setGroupRole(final String group, final String role) {
        LOGGER.trace("setGroupRole(group: [{}], role: [{}])", group, role);
    }
}
