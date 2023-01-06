package com.lucasple.treeftp.commands;

import java.io.PrintWriter;

import com.lucasple.treeftp.utils.SocketUtils;

public class FTPPass extends FTPCommand {
	
	private String password;
	
	public FTPPass(String password) {
		this.commandName = "PASS";
		this.expectedStatusCode = 230;
		this.password = password;
	}

	@Override
	protected void callCommand(PrintWriter writer) {
		StringBuilder commandBuilder = new StringBuilder();
		commandBuilder.append(this.commandName);
		commandBuilder.append(" ");
		commandBuilder.append(this.password);
		SocketUtils.sendMessageWithFlush(writer, commandBuilder.toString());
	}

}
