package com.lucasple.treeftp.communication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map.Entry;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lucasple.treeftp.exceptions.CommandFailedException;
import com.lucasple.treeftp.utils.FTPFile;
import com.lucasple.treeftp.utils.SocketData;

public class TestListingHandler {
	
	private static final String ADDRESS = "ftp.ubuntu.com";
	private static final int PORT = 21;
	
	private static final String LOGIN = "anonymous";
	private static final String PASSWORD = "anonymous";
	
	private static final String CORRECT_CWD = "cdimage";
	private static final String INCORRECT_CWD = "incorrect";

	
	private Socket connection;

	@BeforeEach
	public void openConnection() throws UnknownHostException, IOException, CommandFailedException {
		System.out.println("SALUT");
		this.connection = ConnectionHandler.openConnection(ADDRESS, PORT);
		ConnectionHandler.authenticate(connection, LOGIN, PASSWORD);
	}
	
	@AfterEach
	public void close() throws IOException {
		this.connection.close();
	}
	
	@Test
	public void testSuccessPrintWorkingDirectory() {
		assertNotNull(connection);
		
		assertDoesNotThrow(() -> ListingHandler.printWorkingDirectory(connection));;
	}
	
	@Test
	public void testSuccessChangeWorkingDirectory() {
		assertNotEquals(null, connection);
		
		assertDoesNotThrow(() -> ListingHandler.changeWorkingDirectory(connection, CORRECT_CWD));
	}
	
	@Test
	public void testFailChangeWorkingDirectory() {
		assertNotEquals(null, connection);
		
		assertThrows(CommandFailedException.class, () -> ListingHandler.changeWorkingDirectory(connection, INCORRECT_CWD));
	}
	
	@Test
	public void testSuccessPassiveMode() {
		assertNotEquals(null, connection);
		
		assertDoesNotThrow(() -> ListingHandler.passiveMode(connection));
	}
	
	@Test
	public void testSuccessListDirectory() throws CommandFailedException {
		assertNotEquals(null, connection);
		
		Entry<Integer, String> resultPasv = ListingHandler.passiveMode(connection);
		SocketData socketData = ListingHandler.createSocketData(resultPasv);
		FTPFile file = new FTPFile(ListingHandler.printWorkingDirectory(connection), true);
		assertDoesNotThrow(() -> ListingHandler.listDirectory(connection, socketData, file));
	}
	
}
