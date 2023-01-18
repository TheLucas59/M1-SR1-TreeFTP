package com.lucasple.treeftp.utils;

import java.util.List;

public class Displayer {
	
	public void displayFTPArchitecture(List<FTPFile> architecture) {
		for(FTPFile file : architecture) {
			if(file.getContent() != null) { 
				for(FTPFile fileContent : file.getContent()) {
					if(fileContent.isDirectory()) {
						this.display(fileContent.getPath());
						this.displayFTPArchitecture(fileContent.getContent());
					}
					else {
						this.display(fileContent.getPath());
					}
				}
			}
			else {
				this.display(file.getPath());
			}
		}
	}
	
	private void display(String msg) {
		System.out.println(msg);
	}

}
