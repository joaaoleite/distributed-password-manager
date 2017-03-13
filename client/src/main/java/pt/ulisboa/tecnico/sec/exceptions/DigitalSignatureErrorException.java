package pt.ulisboa.tecnico.sec.security.exceptions;

import java.util.logging.Logger;

public class DigitalSignatureErrorException extends Exception{
	private static final Logger log = Logger.getLogger(DigitalSignatureErrorException.class.getName());

	public DigitalSignatureErrorException(String token){
		log.warning("Error on Digital Signature. Token: " + token);
	}
}
