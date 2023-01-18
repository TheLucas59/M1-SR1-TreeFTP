package com.lucasple.treeftp.utils;

import java.util.List;

public class Displayer {
	
	private StringBuilder sbTree = new StringBuilder();
	
	public void displayFTPArchitecture(List<FTPFile> architecture) {
		for(FTPFile file : architecture) {
			file.display(sbTree);
		}
		this.display();
	}
	
	public void display() {
		System.out.println(sbTree.toString());
	}

}
