package com.lucasple.treeftp.communication;

import java.io.IOException;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucasple.treeftp.commands.FTPList;
import com.lucasple.treeftp.commands.FTPPasv;
import com.lucasple.treeftp.exceptions.CommandFailedException;

/**
 * Class called in main method to handle the connection to the distant server and list the files on the server
 * @author lucas
 *
 */
public class FTPClient {
	
	private static final Log LOGGER = LogFactory.getLog(FTPClient.class);

	public static void handle(String address, int port) {
		FTPClient.handle(address, port, "", "");
	}
	
	/**
	 * Method called to handle all the process of connection and listing of files on the server
	 */
	public static void handle(String address, int port, String login, String password) {
		Socket connection = FTPClient.openConnection(address, port);
		authenticate(login, password, connection);
		
		String connectedPath = FTPClient.printWorkingDirectory(connection);
		
		LOGGER.info(connectedPath + " is the current working directory");
		
		FTPPasv pasv = FTPClient.passiveMode(connection);
		
		LOGGER.info("address : " + pasv.getAddress() + " port : " + pasv.getPort());

		FTPList list = new FTPList(pasv);
		try {
			list.run(connection);
		} catch (CommandFailedException e1) {
			e1.printStackTrace();
		}
		
		try {
			connection.close();
		}
		catch(IOException e) {
			LOGGER.error("Error when closing socket", e);
		}
	}

	/**
	 * Authenticate the client on the distant FTP server
	 * @param login The login to use on the distant server
	 * @param password The password to use on the distant server
	 * @param connection The socket connected to the distant server
	 */
	private static void authenticate(String login, String password, Socket connection) {
		ConnectionHandler.authenticate(connection, login, password);
	}

	/**
	 * Calls the handler to open the connection to the distant server
	 * @param address The address of the distant server
	 * @param port The port to connect to the distant server
	 * @param login The login to connect in FTP 
	 * @param password The password to connect in FTP
	 * @return The socket connected to the distant server
	 */
	public static Socket openConnection(String address, int port) {
		return ConnectionHandler.openConnection(address, port);
	}
	
	/**
	 * Method that returns the current directory you're in on the distant server
	 * @param s The socket connected to the distant server
	 * @return The name of the directory you're in
	 */
	public static String printWorkingDirectory(Socket s) {
		return ListingHandler.printWorkingDirectory(s);
	}
	
	public static FTPPasv passiveMode(Socket s) {
		return ListingHandler.passiveMode(s);
	}
}
