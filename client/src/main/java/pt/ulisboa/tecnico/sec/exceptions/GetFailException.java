package pt.ulisboa.tecnico.sec.security.exceptions;

public class GetFailException extends Exception{
	private static final Logger log = Logger.getLogger(GetFailException.class.getName());

	public GetFailException(){
		log.warning("Get Failed");
	}

	public GetFailException(String status){
		log.warning("Get Failed: " + status);
	}
}
