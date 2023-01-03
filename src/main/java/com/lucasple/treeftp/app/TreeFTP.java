package com.lucasple.treeftp.app;

import java.net.Socket;
import java.util.logging.Logger;

import com.lucasple.treeftp.connection.ConnectionHandler;

/**
 * 
 * @author Lucas Plé
 *
 */

public class TreeFTP {
	
	private static final int NB_PARAMS_MIN_EXPECTED = 1;
	private static final int NB_PARAMS_MAX_EXPECTED = 3;
	private static final int FTP_PORT = 21;
	
    public static void main(String[] args) {
    	String address = "";
    	String login = "";
    	String password = "";
    	
    	if(args.length == NB_PARAMS_MIN_EXPECTED) {
    		address = args[0];
    	}
    	else if(args.length == NB_PARAMS_MAX_EXPECTED) {
    		address = args[0];
    		login = args[1];
    		password = args[2];
    	}
    	else {
    		Logger.getAnonymousLogger().info("Provide at least the arguments required : <address> and if you want the two following : <login> <password>");
    		System.exit(1);
    	}
    	
    	Socket connection = ConnectionHandler.openConnection(address, FTP_PORT);
    	
    }
}
