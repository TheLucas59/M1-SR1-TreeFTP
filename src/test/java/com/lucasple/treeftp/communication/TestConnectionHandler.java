package com.lucasple.treeftp.communication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.Socket;

import org.junit.jupiter.api.Test;

import com.lucasple.treeftp.exceptions.CommandFailedException;

public class TestConnectionHandler {
	
	private static final String CORRECT_LOGIN = "anonymous";
	private static final String CORRECT_PASSWORD = "anonymous";

	private static final String INCORRECT_LOGIN = "incorrect";
	private static final String INCORRECT_PASSWORD = "incorrect";

	private static final String ADDRESS = "ftp.ubuntu.com";
	private static final int PORT = 21;

	@Test
	public void testSuccessConnectionWithUserAndPass() {
		Socket testConnection = ConnectionHandler.openConnection(ADDRESS, PORT);
		assertNotEquals(null, testConnection);
		assertDoesNotThrow(() -> ConnectionHandler.authenticate(testConnection, CORRECT_LOGIN, CORRECT_PASSWORD));
	}
	
	@Test
	public void testFailConnectionWithUserAndPass() {
		Socket testConnection = ConnectionHandler.openConnection(ADDRESS, PORT);
		assertNotEquals(null, testConnection);
		assertThrows(CommandFailedException.class, () -> ConnectionHandler.authenticate(testConnection, INCORRECT_LOGIN, INCORRECT_PASSWORD));
	}
}
