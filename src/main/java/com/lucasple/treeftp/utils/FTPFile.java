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
	
	public void display(StringBuilder sb) {
		this.display(sb, 0);
	}
	
	private void display(StringBuilder sb, int depth) {
		String[] pathSplit = this.getPath().split("/");
		if(this.isDirectory()) {
			sb.append(pathSplit.length == 0 ? "/" : pathSplit[pathSplit.length-1]);
			sb.append("\n");
		}
		else {
			sb.append(pathSplit[pathSplit.length-1]);
			sb.append("\n");
		}
		
		if(this.getContent() != null) {
			for(FTPFile fileContent : this.getContent()) {
				for(int i = 0; i < depth; i++) {
					sb.append("|   ");
				}
				sb.append("├── ");
				fileContent.display(sb, depth+1);
			}
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
