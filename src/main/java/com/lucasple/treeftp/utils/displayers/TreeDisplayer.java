package com.lucasple.treeftp.utils.displayers;

import java.util.List;

import com.lucasple.treeftp.utils.FTPFile;

/**
 * Utility class to display as a tree the file architecture of the distant server
 * @author Lucas Plé
 *
 */
public class TreeDisplayer extends Displayer {
	
	private TreeDisplayer() {}
	
	public static Displayer getInstance() {
		if(instance == null) {
			instance = new TreeDisplayer();
		}
		return instance;
	}
	
	/**
	 * Displays the architecture of a FTP Server that has been analysed
	 * @param architecture List of all the FTPFile to be displayed
	 */
	@Override
	public void displayFTPArchitecture(List<FTPFile> architecture) {
		for(FTPFile file : architecture) {
			file.display(this.getSb());
		}
		this.display();
	}
	
}
