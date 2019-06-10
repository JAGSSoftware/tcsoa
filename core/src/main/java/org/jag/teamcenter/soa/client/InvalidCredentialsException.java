/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.client;

import org.apache.commons.lang3.Validate;

import com.teamcenter.services.strong.core._2011_06.Session.Credentials;

/**
 * @author Jose A. Garcia
 */
public class InvalidCredentialsException extends Exception {

	private static final long serialVersionUID = 3677748379607133391L;

	private transient final Credentials credentials;

	/**
	 * @param credentials
	 * @param exception
	 */
	protected InvalidCredentialsException(
			final Credentials credentials,
			final com.teamcenter.schemas.soa._2006_03.exceptions.InvalidCredentialsException exception) {
		super(exception);

		Validate.notNull(credentials);
		this.credentials = credentials;
	}

	/**
	 * @return
	 */
	public final String username() {
		return credentials.user;
	}

	/**
	 * @return
	 */
	public final String password() {
		return credentials.password;
	}

	/**
	 * @return
	 */
	public final String group() {
		return credentials.group;
	}

	/**
	 * @return
	 */
	public final String role() {
		return credentials.role;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return String
				.format("Credentials{user: [%s], password: [%s], group: [%s], role: [%s]}",
						username(), password(), group(), role());
	}
}
