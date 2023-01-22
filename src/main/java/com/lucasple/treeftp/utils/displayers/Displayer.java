package com.lucasple.treeftp.utils.displayers;

import java.util.List;

import com.lucasple.treeftp.utils.FTPFile;

public abstract class Displayer {
	
	protected static Displayer instance;
	protected StringBuilder sb = new StringBuilder();

	/**
	 * Abstract method to be implemented in the sub-classes to display the file architecture of the server
	 * @param architecture The file architecture of the server
	 */
	public abstract void displayFTPArchitecture(List<FTPFile> architecture);
	
	public StringBuilder getSb() {
		return sb;
	}
	
	/**
	 * Displays the String contained in the sb attribute of this class on standard output
	 */
	protected void display() {
		System.out.println(this.getSb().toString());
	}
}
