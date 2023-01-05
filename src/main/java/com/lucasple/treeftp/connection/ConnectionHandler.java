package com.lucasple.treeftp.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.lucasple.treeftp.commands.FTPUser;
import com.lucasple.treeftp.exceptions.CommandFailedException;
import com.lucasple.treeftp.utils.FTPUtils;
import com.lucasple.treeftp.utils.SocketUtils;

/**
 * Class handling the connection to the FTP Server
 * @author lucas
 *
 */
public class ConnectionHandler {
	
	private static final Logger LOGGER = Logger.getAnonymousLogger();
	
	private static final int EXPECTED_CONNECTION_STATUS_CODE = 220;
	
	private ConnectionHandler() {}
	
	/**
	 * Overload method that calls openConnection with no login and password
	 * @param address The address of the server to connect to
	 * @param port The port to connect to
	 * @return The Socket if the connection and authentication steps have been made, null otherwise
	 */
	public static Socket openConnection(String address, int port) {
		return openConnection(address, port, "", "");
	}
	
	/**
	 * Method that establish a connection between this client and an FTP Server and tries to authenticate with the 
	 * provided credentials. 
	 * @param address The address of the server to connect to
	 * @param port The port to connect to
	 * @param login The login meant to be used with the USER command once the Socket is connected
	 * @param password The password meant to be use with the PASS command once the Socket is connected
	 * @return The Socket if the connection and authentication steps have been made, null otherwise
	 */
	public static Socket openConnection(String address, int port, String login, String password) {
		Socket s = null;
		try {
			s = new Socket(address, port);
			Entry<Integer, String> response = FTPUtils.getFTPResponse(SocketUtils.getReadableInputStream(s));
			if(response.getKey() == EXPECTED_CONNECTION_STATUS_CODE) {
				LOGGER.info("Now Connected with " + address);
				authenticate(s, login, password);
				return s;
			}
			else {
				LOGGER.warning("Connection to server returned an unexpected status : " + response.getKey() + " : " + response.getValue());
				return null;
			}
		} catch (IOException | CommandFailedException e) {
			Logger.getAnonymousLogger().severe(e.getMessage());
			System.exit(1);
		}
		return null;
	}

	/**
	 * Method that calls the USER and PASS commands on the FTP server with the provided credentials to
	 * authenticate the client on the server
	 * @param s The Socket connected to the targeted server
	 * @param login The login that will be used with the USER command
	 * @param password The password that will be used with the PASS command
	 */
	private static void authenticate(Socket s, String login, String password) {
		FTPUser userCommand = new FTPUser(login);
		try {
			userCommand.run(s);
		}
		catch(CommandFailedException ce) {
			LOGGER.severe(ce.getMessage());
		}
	}
}
