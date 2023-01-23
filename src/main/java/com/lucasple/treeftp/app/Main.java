package com.lucasple.treeftp.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucasple.treeftp.communication.FTPClient;
import com.lucasple.treeftp.exceptions.CommandFailedException;

/**
 * Main class that launches the TreeFTP command
 * @author Lucas Plé
 *
 */

public class Main {
	
	private static final Log LOGGER = LogFactory.getLog(Main.class);
	
	private static final int FTP_PORT = 21;
	private static final int DEFAULT_DEPTH = 99;
	
	private static final String USER_OPTION = "-u";
	private static final String PASSWORD_OPTION = "-p";
	private static final String DEPTH_OPTION = "-d";
	private static final String JSON_OPTION = "--json";
	
	private static final String ANONYMOUS = "anonymous";
	
    public static void main(String[] args) {
    	String address = "";
    	String user = ANONYMOUS;
    	String password = ANONYMOUS;
    	int maxDepth = DEFAULT_DEPTH;
    	boolean json = false;
    	
    	if(args.length > 0) {
    		address = args[0];
	    	for(int i = 1; i < args.length; i++) {
	    		switch(args[i]) {
	    			case USER_OPTION:
	    				user = args[++i];
	    				break;
	    			case PASSWORD_OPTION :
	    				password = args[++i];
	    				break;
	    			case DEPTH_OPTION :
	    				maxDepth = Integer.parseInt(args[++i]);
	    				break;
	    			case JSON_OPTION :
	    				json = true;
	    				break;
	    			default :
	    				LOGGER.error("The option " + args[i] + " does not exist.");
	    				System.exit(1);
	    		}
	    	}
    	}
    	
		try {
			FTPClient.handle(address, FTP_PORT, user, password, maxDepth, json);
		} catch (CommandFailedException e) {
			LOGGER.error("Error during the FTP process", e);
		}
    }
}
