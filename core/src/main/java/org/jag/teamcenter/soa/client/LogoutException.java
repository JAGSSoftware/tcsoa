/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.client;

/**
 * @author Jose A. Garcia
 */
public class LogoutException extends RuntimeException {

	/**  */
	private static final long serialVersionUID = -4896619426448597335L;

	/**
	 * @param throwable
	 */
	public LogoutException(final Throwable throwable) {
		super(throwable);
	}

	/**
	 * @param message
	 * @param throwable
	 */
	public LogoutException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
