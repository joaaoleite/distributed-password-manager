package pt.ulisboa.tecnico.sec.security;

import java.util.Date;

public class DigitalSignatureExpiredException extends Exception{
	private Date validity;

	public DigitalSignatureExpiredException(Date validity){
		
		this.validity = validity;
	}
	public String getMessage(){
		return "Mac Digital Signature Expired on "+validity;
	}
}
