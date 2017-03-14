package pt.ulisboa.tecnico.sec.exceptions;

import java.util.logging.Logger;

public class PutFailException extends Exception{
	private static final Logger log = Logger.getLogger(PutFailException.class.getName());

	private String status;

	public PutFailException(){
		super("PUT Failed");
		this.status = "";

		log.warning("PUT Failed");
	}

	public PutFailException(String status){
		super("PUT Failed: " + status);
		this.status = status;

		log.warning("PUT Failed: " + status);
	}

	public String getMessage(){
		return "PUT Failed: " + status;
	}
}
