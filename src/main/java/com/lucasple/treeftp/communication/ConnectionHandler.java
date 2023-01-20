package com.lucasple.treeftp.communication;

import java.io.IOException;
import java.net.Socket;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucasple.treeftp.commands.FTPPass;
import com.lucasple.treeftp.commands.FTPUser;
import com.lucasple.treeftp.exceptions.CommandFailedException;
import com.lucasple.treeftp.utils.FTPUtils;
import com.lucasple.treeftp.utils.SocketUtils;

/**
 * Class handling the connection to the FTP Server
 * @author Lucas Plé
 *
 */
public class ConnectionHandler {
	
	private static final Log LOGGER = LogFactory.getLog(ConnectionHandler.class);
	
	private static final int EXPECTED_CONNECTION_STATUS_CODE = 220;
	
	private ConnectionHandler() {}
	
	/**
	 * Method that establish a connection between this client and an FTP Server and tries to authenticate with the 
	 * provided credentials. 
	 * @param address The address of the server to connect to
	 * @param port The port to connect to
	 * @return The Socket if the connection and authentication steps have been made, null otherwise
	 */
	public static Socket openConnection(String address, int port) {
		Socket s = null;
		try {
			s = new Socket(address, port);
			Entry<Integer, String> response = FTPUtils.getFTPResponse(SocketUtils.getReadableInputStream(s));
			if(response.getKey() == EXPECTED_CONNECTION_STATUS_CODE) {
				LOGGER.info("Now Connected with " + address);
				return s;
			}
			else {
				LOGGER.warn("Connection to server returned an unexpected status : " + response.getKey() + " : " + response.getValue());
				return null;
			}
		} catch (IOException | CommandFailedException e) {
			LOGGER.error("The connection to the distant server has failed", e);
			System.exit(1);
		}
		return s;
	}

	/**
	 * Method that calls the USER and PASS commands on the FTP server with the provided credentials to
	 * authenticate the client on the server
	 * @param s The Socket connected to the targeted server
	 * @param login The login that will be used with the USER command
	 * @param password The password that will be used with the PASS command
	 */
	public static void authenticate(Socket s, String login, String password) {
		FTPUser userCommand = new FTPUser(login);
		FTPPass passCommand = new FTPPass(password);
		try {
			userCommand.run(s);
			passCommand.run(s);
		}
		catch(CommandFailedException ce) {
			LOGGER.error("Connection failed", ce);
		}
	}
}
