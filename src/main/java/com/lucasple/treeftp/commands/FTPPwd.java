package com.lucasple.treeftp.commands;

import java.io.PrintWriter;

import com.lucasple.treeftp.utils.SocketUtils;

/**
 * Class defining the PWD FTP command
 * @author Lucas Plé
 *
 */
public class FTPPwd extends FTPCommand {
	
	public FTPPwd() {
		this.commandName = "PWD";
		this.expectedStatusCode = 257;
	}

	@Override
	protected void callCommand(PrintWriter writer) {
		StringBuilder commandBuilder = new StringBuilder();
		commandBuilder.append(this.commandName);
		SocketUtils.sendMessageWithFlush(writer, commandBuilder.toString());
	}

}
