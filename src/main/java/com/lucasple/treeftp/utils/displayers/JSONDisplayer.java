package com.lucasple.treeftp.utils.displayers;

import java.util.List;

import com.lucasple.treeftp.utils.FTPFile;

/**
 * Utility class to display in a JSON format the file architecture of the distant server
 * @author Lucas Plé
 *
 */
public class JSONDisplayer extends Displayer {
	
	private JSONDisplayer() {}
	
	public static Displayer getInstance() {
		if(instance == null) {
			instance = new JSONDisplayer();
		}
		return instance;
	}

	@Override
	public void displayFTPArchitecture(List<FTPFile> architecture) {
		for(FTPFile file : architecture) {
			file.displayAsJSON(this.getSb());
		}
		this.display();
	}

}
