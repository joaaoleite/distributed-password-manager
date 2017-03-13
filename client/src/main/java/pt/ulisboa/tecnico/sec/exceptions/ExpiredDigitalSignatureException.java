package pt.ulisboa.tecnico.sec.security.exceptions;

import java.util.Date;

public class ExpiredDigitalSignatureException extends Exception{
	private static final Logger log = Logger.getLogger(ExpiredDigitalSignatureException.class.getName());

	public ExpiredDigitalSignatureException(Date validity){
		log.warning("MAC Digital Signature Expired on " + validity);
	}
}
