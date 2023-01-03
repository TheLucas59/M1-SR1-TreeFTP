package com.lucasple.treeftp.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ConnectionHandler {
	
	private ConnectionHandler() {}
	
	public static Socket openConnection(String address, int port) {
		Socket s = null;
		try {
			s = new Socket(address, port);
		} catch (IOException e) {
			Logger.getAnonymousLogger().severe(e.getMessage());
			System.exit(1);
		}
		return s;
	}

}
