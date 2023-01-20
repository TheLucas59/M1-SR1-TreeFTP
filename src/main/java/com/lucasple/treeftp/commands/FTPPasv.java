package com.lucasple.treeftp.commands;

import java.io.PrintWriter;

import com.lucasple.treeftp.utils.SocketUtils;

/**
 * Class defining the PASV FTP command
 * @author Lucas Plé
 *
 */
public class FTPPasv extends FTPCommand {

	public FTPPasv() {
		this.commandName = "PASV";
		this.expectedStatusCode = 227;
	}
	
	@Override
	protected void callCommand(PrintWriter writer) {
		SocketUtils.sendMessageWithFlush(writer, this.commandName);
	}

}
