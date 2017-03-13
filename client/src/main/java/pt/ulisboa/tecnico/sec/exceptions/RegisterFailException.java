package pt.ulisboa.tecnico.sec.security.exceptions;

import java.util.logging.Logger;

public class RegisterFailException extends Exception{
	private static final Logger log = Logger.getLogger(RegisterFailException.class.getName());

	public RegisterFailException(){
		log.warning("Register failed");
	}
}
