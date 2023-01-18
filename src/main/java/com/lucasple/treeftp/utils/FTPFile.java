package com.lucasple.treeftp.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to represent the FTP server file architecture
 * @author lucas
 *
 */
public class FTPFile {
	
	private String path;
	private boolean directory;
	private List<FTPFile> content;
	
	public FTPFile(String path, boolean directory) {
		this.path = path;
		this.directory = directory;
		if(directory) {
			content = new ArrayList<>();
		}
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isDirectory() {
		return directory;
	}
	public void setDirectory(boolean directory) {
		this.directory = directory;
	}

	public List<FTPFile> getContent() {
		return content;
	}
}
