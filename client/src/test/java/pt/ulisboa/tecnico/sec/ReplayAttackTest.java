package pt.ulisboa.tecnico.sec;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import pt.ulisboa.tecnico.sec.Client;
import pt.ulisboa.tecnico.sec.client.*;

public class ReplayAttackTest extends TestCase {

	private String address;
	private int port;
	private int replicas;
	private Session session;

	protected void setUp(){
		address = "localhost";
		port = 8080;
		replicas = 4;
		session = Session.newInstance();
	}

	// =======================================
	// WARNING:
	// edit RequestWork.call on Client!!!
	// =======================================
	public void test(){

		session.login("username","password");
		Client client = new Client(session, address, port, replicas);

		client.register();

		String domain = "gmail.com";
		String username = "example@gmail.com";
		String password = "123456789";

		Boolean put = client.savePassword(domain,username,password);

		// Exception catched
		// client received error
		// server stop secound savePassword!
		assertFalse(put);

	}
}
