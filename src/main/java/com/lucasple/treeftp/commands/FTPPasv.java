package com.lucasple.treeftp.commands;

import java.io.PrintWriter;

import com.lucasple.treeftp.utils.SocketUtils;

public class FTPPasv extends FTPCommand {

	private String address;
	private int port;
	
	public FTPPasv() {
		this.commandName = "PASV";
		this.expectedStatusCode = 227;
	}
	
	@Override
	protected void callCommand(PrintWriter writer) {
		SocketUtils.sendMessageWithFlush(writer, this.commandName);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
