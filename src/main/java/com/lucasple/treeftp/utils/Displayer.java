package com.lucasple.treeftp.utils;

import java.util.List;

/**
 * Utility class to display the file architecture of the distant server
 * @author Lucas Plé
 *
 */
public class Displayer {
	
	private StringBuilder sbTree = new StringBuilder();
	
	/**
	 * Displays the architecture of a FTP Server that has been analysed
	 * @param architecture List of all the FTPFile to be displayed
	 */
	public void displayFTPArchitecture(List<FTPFile> architecture) {
		for(FTPFile file : architecture) {
			file.display(sbTree);
		}
		this.display();
	}
	
	private void display() {
		System.out.println(sbTree.toString());
	}

}
