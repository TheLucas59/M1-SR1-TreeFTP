package com.lucasple.treeftp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to represent the FTP server file architecture
 * @author Lucas Plé
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
	
	/**
	 * Method that recursively display the content of an FTPFile instance in a tree format
	 * The display is similar to the unix "tree" command
	 * @param sb The StringBuilder used to construct the tree
	 */
	public void display(StringBuilder sb) {
		this.display(sb, 0, new HashMap<>());
	}
	
	private void display(StringBuilder sb, int depth, Map<Integer, Boolean> verticalBars) {
		String[] pathSplit = this.getPath().split("/");
		if(this.isDirectory()) {
			sb.append(pathSplit.length == 0 ? this.getPath() : pathSplit[pathSplit.length-1]);
			sb.append("\n");
		}
		else {
			sb.append(pathSplit[pathSplit.length-1]);
			sb.append("\n");
		}
		
		if(this.getContent() != null) {
			for(int i = 0; i < this.getContent().size(); i++) {
				FTPFile fileContent = this.getContent().get(i);
				for(int j = 0; j < depth; j++) {
					if(verticalBars.containsKey(j) && Boolean.TRUE.equals(verticalBars.get(j))) {
						sb.append("|   ");
					}
					else {
						sb.append("    ");
					}
					
				}
				
				if(i == this.getContent().size() -1) {
					sb.append("└── ");
					verticalBars.put(depth, false);
				}
				else {
					sb.append("├── ");
					verticalBars.put(depth, true);
				}
				
				fileContent.display(sb, depth+1, verticalBars);
			}
		}
	}
	
	/**
	 * Method that recursively display the content of an FTPFile instance in a JSON format
	 * @param sb The StringBuilder used to construct the tree
	 */
	public void displayAsJSON(StringBuilder sb) {
		this.displayAsJSON(sb, 0, true);
	}
	
	private void displayAsJSON(StringBuilder sb, int depth, boolean endOfDirectory) {
		String[] pathSplit = this.getPath().split("/");
		String name = "";
		if(pathSplit.length > 0) {
			name = pathSplit[pathSplit.length-1];
		}
		else {
			name = this.getPath();
		}
		
		for(int i = 0; i < depth; i++) {
			sb.append("    ");
		}
		
		if(this.isDirectory()) {
			sb.append("{\"type\" : \"directory\", \"name\" : \"");
			sb.append(name);
			sb.append("\", \"content\" : [");
			boolean newLine = false;
			if(!this.getContent().isEmpty()) {
				newLine = true;
				for(int i = 0; i < this.getContent().size(); i++) {
					FTPFile file = this.getContent().get(i);
					sb.append("\n");
					if(i == this.getContent().size()-1) {
						file.displayAsJSON(sb, depth+1, true);
					}
					else {
						file.displayAsJSON(sb, depth+1, false);
					}
				}
			}
			if(newLine) {
				sb.append("\n");
				for(int i = 0; i < depth; i++) {
					sb.append("    ");
				}
			}
			if(endOfDirectory) {
				sb.append("]}");
			}
			else {
				sb.append("]},");
			}
		}
		else {
			sb.append("{\"type\" : \"file\", \"name\" : \"");
			sb.append(name);
			sb.append("\"");
			if(endOfDirectory) {
				sb.append("}");
			}
			else {
				sb.append("},");
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
	public void setContent(List<FTPFile> content) {
		this.content = content;
	}
}
