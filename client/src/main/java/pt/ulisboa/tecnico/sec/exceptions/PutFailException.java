package pt.ulisboa.tecnico.sec.exceptions;

import java.util.logging.Logger;

public class PutFailException extends Exception{
	private static final Logger log = Logger.getLogger(PutFailException.class.getName());

	public PutFailException(){
		log.warning("Put Failed");
	}

	public PutFailException(String status){
		log.warning("Put Failed: " + status);
	}
}
