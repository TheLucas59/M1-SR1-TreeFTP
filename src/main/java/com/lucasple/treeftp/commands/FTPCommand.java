package com.lucasple.treeftp.commands;

import java.net.Socket;
import java.util.Map.Entry;

import com.lucasple.treeftp.exceptions.CommandFailedException;

/**
 * Interface defining any FTP command
 * @author lucas
 *
 */
public interface FTPCommand {

	/**
	 * Return the server response for the run command
	 * @param s The Socket to communicate with the server
	 * @return A Map Entry which will hae the status code as the key and the following message as the value
	 * @throws CommandFailedException If there is a problem while running the command
	 */
	public Entry<Integer, String> run(Socket s) throws CommandFailedException;
	
}
