package com.lucasple.treeftp.commands;

import java.io.PrintWriter;

import com.lucasple.treeftp.utils.SocketUtils;

/**
 * Class defining the CWD FTP command
 * @author Lucas Plé
 *
 */
public class FTPCwd extends FTPCommand {
	
	private String directory;
	
	public FTPCwd(String directory) {
		this.commandName = "CWD";
		this.expectedStatusCode = 250;
		this.directory = directory;
	}

	@Override
	protected void callCommand(PrintWriter writer) {
		StringBuilder commandBuilder = new StringBuilder();
		commandBuilder.append(this.commandName);
		commandBuilder.append(" ");
		commandBuilder.append(this.directory);
		SocketUtils.sendMessageWithFlush(writer, commandBuilder.toString());
	}

}
