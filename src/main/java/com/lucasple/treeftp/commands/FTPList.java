package com.lucasple.treeftp.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucasple.treeftp.communication.ConnectionHandler;
import com.lucasple.treeftp.communication.ListingHandler;
import com.lucasple.treeftp.utils.SocketUtils;

public class FTPList extends FTPCommand {
	
	private static final Log LOGGER = LogFactory.getLog(FTPList.class);
	
	private Socket dataSocket;
	private int expectedStatusCodeEndData;
	FTPPasv pasv;
	
	public FTPList(FTPPasv pasv) {
		this.commandName = "LIST";
		this.pasv = pasv;
		this.expectedStatusCode = 150;
		this.expectedStatusCodeEndData = 226;
	}

	@Override
	protected void callCommand(PrintWriter writer) {
		SocketUtils.sendMessageWithFlush(writer, this.commandName);
		Socket dataSocket = null;
		try {
			dataSocket = new Socket(pasv.getAddress(), pasv.getPort());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader reader = SocketUtils.getReadableInputStream(dataSocket);
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
