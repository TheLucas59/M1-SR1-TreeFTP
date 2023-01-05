package com.lucasple.treeftp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Utility class used to easily get readable and writable streams from a socket
 * @author lucas
 *
 */
public class SocketUtils {

	/**
	 * Gets the OutputStream from the Socket and create a PrintWriter. This makes you able to send messages
	 * to the connected server
	 * @param s The socket to use
	 * @return A PrintWriter you can use to send messages to the connected server
	 */
	public static PrintWriter getWritableOutputStream(Socket s) {
		OutputStream out = null;
		try {
			out = s.getOutputStream();
		} catch (IOException e) {
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
		return new PrintWriter(out);
	}
	
	/**
	 * Gets the InputStream from the Socket and create a BufferedReader in which you can read messages that
	 * the server sends you.
	 * @param s The socket to use
	 * @return A BufferedReader you can use to read messages coming from the connected server
	 */
	public static BufferedReader getReadableInputStream(Socket s) {
		InputStream in = null;
		try {
			in = s.getInputStream();
		}
		catch(IOException e) {
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
		return new BufferedReader(new InputStreamReader(in));
	}
	
	public static void sendMessageWithFlush(PrintWriter writer, String message) {
		writer.println(message);
		writer.flush();
	}
}
