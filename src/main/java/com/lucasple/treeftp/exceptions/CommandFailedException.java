package com.lucasple.treeftp.exceptions;

/**
 * Exception describing the fact that a FTP command has failed when called on the distant server
 * @author lucas
 *
 */
public class CommandFailedException extends Exception {

	private static final long serialVersionUID = -6707794885323528847L;

	public CommandFailedException(String message) {
		super(message);
	}
}
