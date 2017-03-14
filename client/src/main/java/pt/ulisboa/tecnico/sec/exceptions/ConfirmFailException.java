package pt.ulisboa.tecnico.sec.exceptions;

import java.util.logging.Logger;

public class ConfirmFailException extends Exception{
	private static final Logger log = Logger.getLogger(ConfirmFailException.class.getName());

	private String status;

	public ConfirmFailException(){
		super("Confirmation failed");
		this.status = "";

		log.warning("Confirm Failed");
	}

	public ConfirmFailException(String status){
		super("Confirmation failed: "+status);
		this.status = status;

		log.warning("Confirm Failed: " + status);
	}

	public String getMessage(){
		return "Confirmation failed: "+status;
	}
}
