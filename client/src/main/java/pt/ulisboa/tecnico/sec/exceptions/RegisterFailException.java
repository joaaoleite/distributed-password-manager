package pt.ulisboa.tecnico.sec.exceptions;

import java.util.logging.Logger;

public class RegisterFailException extends Exception{
	private static final Logger log = Logger.getLogger(RegisterFailException.class.getName());

	public RegisterFailException(){
		super("REGISTER failed");
		
		log.warning("REGISTER failed");
	}

	public String getMessage(){
		return "REGISTER failed";
	}
}
