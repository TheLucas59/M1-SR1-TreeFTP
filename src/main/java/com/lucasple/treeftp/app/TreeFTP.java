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

public class TreeFTP {
	
	private static final Log LOGGER = LogFactory.getLog(TreeFTP.class);
	
	private static final int NB_PARAMS_MIN_EXPECTED = 1;
	private static final int NB_PARAMS_MAX_EXPECTED = 4;
	private static final int FTP_PORT = 21;
	private static final int DEFAULT_DEPTH = 99;
	
    public static void main(String[] args) {
    	String address = "";
    	String login = "";
    	String password = "";
    	int maxDepth = DEFAULT_DEPTH;
    	
    	if(args.length == NB_PARAMS_MIN_EXPECTED) {
    		address = args[0];
    		try {
				FTPClient.handle(address, FTP_PORT);
			} catch (CommandFailedException e) {
				LOGGER.error("Error during the FTP process", e);
			}
    	}
    	else if(args.length == NB_PARAMS_MAX_EXPECTED) {
    		address = args[0];
    		login = args[1];
    		password = args[2];
    		maxDepth = Integer.parseInt(args[3]);
    		try {
				FTPClient.handle(address, FTP_PORT, login, password, maxDepth);
			} catch (CommandFailedException e) {
				LOGGER.error("Error during the FTP process", e);
			}
    	}
    	else {
    		LOGGER.info("Provide at least the arguments required : <address> and if you want the two following : <login> <password>");
    		System.exit(1);
    	}
    }
}
