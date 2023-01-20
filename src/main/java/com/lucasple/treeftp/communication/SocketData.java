package com.lucasple.treeftp.communication;

import java.io.IOException;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class that encapsulate a socket and a port. Used to create the data socket after using the PASV command
 * @author Lucas Plé
 *
 */
public class SocketData {
	
	private static final Log LOGGER = LogFactory.getLog(SocketData.class);
	
	private String address;
	private int port;
	private Socket socket;
	
	/**
	 * Opens the data socket
	 */
	public void open() {
		try {
			this.socket = new Socket(this.address, this.port);
			LOGGER.info("Data socket now open");
		} catch (IOException e) {
			LOGGER.error("Error when opening socket", e);
		}
	}
	
	/**
	 * Close the socket
	 */
	public void close() {
		try {
			this.socket.close();
		}
		catch(IOException e) {
			LOGGER.error("Error when closing the socket", e);
		}
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}
