package com.lucasple.treeftp.commands;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.lucasple.treeftp.exceptions.CommandFailedException;
import com.lucasple.treeftp.utils.FTPUtils;
import com.lucasple.treeftp.utils.SocketUtils;

public class FTPUser implements FTPCommand {
	
	private static final Logger LOGGER = Logger.getAnonymousLogger();
	
	private static final String COMMAND_NAME = "USER";
	private static int EXPECTED_USER_STATUS_CODE = 331;
	
	private String login;
	
	public FTPUser(String login) {
		this.login = login;
	}

	@Override
	public Entry<Integer, String> run(Socket s) throws CommandFailedException {
		BufferedReader reader = SocketUtils.getReadableInputStream(s);
		PrintWriter writer = SocketUtils.getWritableOutputStream(s);
		
		callUserCommand(writer);
		Entry<Integer, String> response = FTPUtils.getFTPResponse(reader);
		if(response.getKey() == EXPECTED_USER_STATUS_CODE) {
			LOGGER.info(COMMAND_NAME + " command successfull");
		}
		else {
			LOGGER.warning(COMMAND_NAME + " command returned an unexpected status : " + response.getKey() + " : " + response.getValue());
		}
		
		return response;
	}

	private void callUserCommand(PrintWriter writer) {
		StringBuilder commandBuilder = new StringBuilder();
		commandBuilder.append(COMMAND_NAME);
		commandBuilder.append(" ");
		commandBuilder.append(this.login);
		SocketUtils.sendMessageWithFlush(writer, commandBuilder.toString());
	}

}
