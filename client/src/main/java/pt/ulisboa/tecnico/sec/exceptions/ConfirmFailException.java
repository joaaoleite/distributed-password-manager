package pt.ulisboa.tecnico.sec.security.exceptions;

import java.util.logging.Logger;

public class ConfirmFailException extends Exception{
	private static final Logger log = Logger.getLogger(ConfirmFailException.class.getName());

	public ConfirmFailException(){
		log.warning("Confirm Failed");
	}

	public ConfirmFailException(String status){
		log.warning("Confirm Failed: " + status);
	}
}
