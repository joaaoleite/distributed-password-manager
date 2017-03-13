package pt.ulisboa.tecnico.sec.security;

import java.util.Date;

public class DigitalSignatureExpiredException extends Exception{
	private Date validity;

	public DigitalSignatureExpiredException(Date validity){
		super("Mac Digital Signature Expired on "+validity);
		this.validity = validity;
	}
}
