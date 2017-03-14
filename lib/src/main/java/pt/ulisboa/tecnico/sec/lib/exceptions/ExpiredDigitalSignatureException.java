package pt.ulisboa.tecnico.sec.lib.exceptions;

import java.util.logging.Logger;

import java.util.Date;

public class ExpiredDigitalSignatureException extends Exception{
	private static final Logger log = Logger.getLogger(ExpiredDigitalSignatureException.class.getName());

	private Date validity;

	public ExpiredDigitalSignatureException(Date validity){
		super("MAC Digital Signature Expired on " + validity);
		this.validity = validity;

		log.warning("MAC Digital Signature Expired on " + validity);
	}

	public ExpiredDigitalSignatureException(){
		super("MAC Digital Signature Expired");

		log.warning("MAC Digital Signature Expired");
	}

	public String getMessage(){
		if(validity==null)
			return "MAC Digital Signature Expired";

		return "MAC Digital Signature Expired on " + validity;
	}
}
