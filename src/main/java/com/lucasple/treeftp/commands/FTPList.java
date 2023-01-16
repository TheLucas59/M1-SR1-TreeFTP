package com.lucasple.treeftp.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucasple.treeftp.communication.SocketData;
import com.lucasple.treeftp.utils.SocketUtils;

public class FTPList extends FTPCommand {
	
	private static final Log LOGGER = LogFactory.getLog(FTPList.class);
	
	private Socket dataSocket;
	private int expectedStatusCodeEndData;
	private SocketData socketData;
	
	public FTPList(SocketData socketData) {
		this.commandName = "LIST";
		this.socketData = socketData;
		this.expectedStatusCode = 150;
		this.expectedStatusCodeEndData = 226;
	}

	@Override
	protected void callCommand(PrintWriter writer) {
		SocketUtils.sendMessageWithFlush(writer, this.commandName);
		this.socketData.open();
		BufferedReader reader = SocketUtils.getReadableInputStream(this.socketData.getSocket());
		String line = "";
		try {
			while((line = reader.readLine()) != null) {
				LOGGER.info(line);
			}
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

}
