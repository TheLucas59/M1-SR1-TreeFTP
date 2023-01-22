package com.lucasple.treeftp.communication;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucasple.treeftp.exceptions.CommandFailedException;
import com.lucasple.treeftp.utils.Displayer;
import com.lucasple.treeftp.utils.FTPFile;
import com.lucasple.treeftp.utils.SocketData;

/**
 * Class called in main method to handle the connection to the distant server and list the files on the server
 * @author Lucas Plé
 *
 */
public class FTPClient {
	
	private FTPClient() {}
	
	private static final Log LOGGER = LogFactory.getLog(FTPClient.class);
	
	private static final int DEFAULT_DEPTH = 99;
	private static int maxDepth;
	
	public static void handle(String address, int port) throws CommandFailedException {
		FTPClient.handle(address, port, "", "", DEFAULT_DEPTH);
	}
	
	/**
	 * Method called to handle all the process of connection and listing of files on the server
	 * @throws CommandFailedException 
	 */
	public static void handle(String address, int port, String login, String password, int maxDepth) throws CommandFailedException {
		FTPClient.maxDepth = maxDepth;
		
		Socket connection = FTPClient.openConnection(address, port);
		authenticate(login, password, connection);
		
		String connectedPath = FTPClient.printWorkingDirectory(connection);
		
		LOGGER.info(connectedPath + " is the current working directory");
		
		FTPFile architecture = new FTPFile(connectedPath, true);
		
		listFilesFTPServer(connection, architecture, 0);
		
		Displayer displayer = Displayer.getInstance();
		displayer.displayFTPArchitecture(Arrays.asList(architecture));
		
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
	 * @throws CommandFailedException If the authentication fails
	 */
	private static void authenticate(String login, String password, Socket connection) throws CommandFailedException {
		ConnectionHandler.authenticate(connection, login, password);
	}

	/**
	 * Calls the handler to open the connection to the distant server
	 * @param address The address of the distant server
	 * @param port The port to connect to the distant server
	 * @return The socket connected to the distant server
	 */
	public static Socket openConnection(String address, int port) {
		return ConnectionHandler.openConnection(address, port);
	}
	
	/**
	 * Method that returns the current directory you're in on the distant server
	 * @param s The socket connected to the distant server
	 * @return The name of the directory you're in
	 * @throws CommandFailedException 
	 */
	public static String printWorkingDirectory(Socket s) throws CommandFailedException {
		return ListingHandler.printWorkingDirectory(s);
	}
	
	/**
	 * Open a new socket to receive data from the distant server
	 * @param s The socket connected to the distant server
	 * @return A socket to receive data in passive mode from the server
	 * @throws CommandFailedException 
	 */
	public static SocketData passiveMode(Socket s) throws CommandFailedException {
		Entry<Integer, String> resultPASV = ListingHandler.passiveMode(s);
		return ListingHandler.createSocketData(resultPASV);
	}
	
	/**
	 * Calls the FTP LIST command on the distant server
	 * @param s The socket connected to the distant server
	 * @param socketData The socket connected to the distant server to receive data in passive mode
	 * @throws CommandFailedException 
	 */
	public static void listDirectory(Socket s, SocketData socketData, FTPFile architecture) throws CommandFailedException {
		ListingHandler.listDirectory(s, socketData, architecture);
	}
	
	/**
	 * List all the files and directories on the server recusively
	 * @param connection The socket connected to the distant server
	 * @param architecture The FTPFile object encapsulating all the files on the server
	 * @throws CommandFailedException 
	 */
	public static void listFilesFTPServer(Socket connection, FTPFile architecture, int depth) throws CommandFailedException {
		SocketData socketData = FTPClient.passiveMode(connection);
		FTPClient.listDirectory(connection, socketData, architecture);
		for(FTPFile file : architecture.getContent()) {
			if(file.isDirectory() && depth < FTPClient.maxDepth) {
				ListingHandler.changeWorkingDirectory(connection, file.getPath());
				FTPClient.listFilesFTPServer(connection, file, depth+1);
			}
		}
	}
}
