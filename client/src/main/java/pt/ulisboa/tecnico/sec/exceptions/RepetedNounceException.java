package pt.ulisboa.tecnico.sec.exceptions;

import java.util.logging.Logger;

public class RepetedNounceException extends Exception{
	private static final Logger log = Logger.getLogger(RepetedNounceException.class.getName());

	public RepetedNounceException(String nounce){
		log.warning("Repeated Nounce: " + nounce);
	}
}
