package pt.ulisboa.tecnico.sec.exceptions;

import java.util.logging.Logger;

public class GetFailException extends Exception{
	private static final Logger log = Logger.getLogger(GetFailException.class.getName());

	private String status;

	public GetFailException(){
		super("GET Failed");
		this.status = "";

		log.warning("GET Failed");
	}

	public GetFailException(String status){
		super("GET Failed: " + status);
		this.status = status;

		log.warning("GET Failed: " + status);
	}

	public String getMessage(){
		return "GET Failed: " + status;
	}
}
