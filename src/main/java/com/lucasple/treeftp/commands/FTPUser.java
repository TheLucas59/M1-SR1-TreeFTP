package com.lucasple.treeftp.commands;

import java.io.PrintWriter;

import com.lucasple.treeftp.utils.SocketUtils;

/**
 * Class defining the USER FTP command
 * @author Lucas Plé
 *
 */
public class FTPUser extends FTPCommand {
	
	private String login;
	
	public FTPUser(String login) {
		this.commandName = "USER";
		this.expectedStatusCode = 331;
		this.login = login;
	}

	@Override
	protected void callCommand(PrintWriter writer) {
		StringBuilder commandBuilder = new StringBuilder();
		commandBuilder.append(this.commandName);
		commandBuilder.append(" ");
		commandBuilder.append(this.login);
		SocketUtils.sendMessageWithFlush(writer, commandBuilder.toString());
	}

}
