package com.lucasple.treeftp.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucasple.treeftp.communication.SocketData;
import com.lucasple.treeftp.exceptions.CommandFailedException;
import com.lucasple.treeftp.utils.FTPFile;
import com.lucasple.treeftp.utils.FTPUtils;
import com.lucasple.treeftp.utils.SocketUtils;

public class FTPList extends FTPCommand {
	
	private static final Log LOGGER = LogFactory.getLog(FTPList.class);
	
	private int expectedStatusCodeEndData;
	private SocketData socketData;
	private FTPFile architecture;
	
	public FTPList(SocketData socketData, FTPFile architecture) {
		this.commandName = "LIST";
		this.socketData = socketData;
		this.expectedStatusCode = 150;
		this.expectedStatusCodeEndData = 226;
		this.architecture = architecture;
	}
	
	@Override
	public Entry<Integer, String> run(Socket s) throws CommandFailedException {
		BufferedReader reader = SocketUtils.getReadableInputStream(s);
		PrintWriter writer = SocketUtils.getWritableOutputStream(s);
		
		callCommand(writer);
		Entry<Integer, String> response = FTPUtils.getFTPResponse(reader);
		checkResponse(response);
		
		Entry<Integer, String> responseAfterData = FTPUtils.getFTPResponse(reader);
		checkResponseListAfterData(responseAfterData);
		
		return response;
	}

	@Override
	protected void callCommand(PrintWriter writer) {		
		SocketUtils.sendMessageWithFlush(writer, this.commandName);
		this.socketData.open();
		BufferedReader reader = SocketUtils.getReadableInputStream(this.socketData.getSocket());
		String line = "";
		try {
			while((line = reader.readLine()) != null) {
				boolean fileIsDirectory = line.startsWith("d");
				String[] splitLine = line.split(" ");
				String fileName = architecture.getPath() + splitLine[splitLine.length-1];
				if(fileIsDirectory) {
					fileName += "/";
				}
				FTPFile file = new FTPFile(fileName, fileIsDirectory);
				architecture.getContent().add(file);
			}
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	private void checkResponseListAfterData(Entry<Integer, String> response) {
		if(response.getKey() == expectedStatusCodeEndData) {
			LOGGER.info("Data retrieved successfully from LIST");
		}
		else {
			LOGGER.warn(commandName + " command returned an unexpected status : " + response.getKey() + " : " + response.getValue());
		}
	}
}
