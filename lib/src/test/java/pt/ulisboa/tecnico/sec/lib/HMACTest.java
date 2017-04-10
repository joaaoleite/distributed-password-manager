package pt.ulisboa.tecnico.sec.lib;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.json.JSONObject;

import pt.ulisboa.tecnico.sec.lib.crypto.*;
import pt.ulisboa.tecnico.sec.lib.http.*;
import pt.ulisboa.tecnico.sec.lib.exceptions.*;

public class HMACTest extends TestCase {

	public void test() throws Exception {

		System.out.println("---TESTING HMAC---");

		JSONObject json;
		String key;
    	boolean state;
    	String token;

		// --------------------------------------------------

    	// Server init (client registered)
		System.out.println("Server init...");
		HMAC hmac_server = new HMAC();
		key = hmac_server.getKey();

		// Server object to client
    	json = new JSONObject();
		// key should be ciphered with client public key
    	json.put("key", key);
		json.put("seqNumber", 0);
    	System.out.println("Server JSON: "+json);

		// Server signing
    	token = hmac_server.sign(json);
    	System.out.println("Server header token: "+token);

		// --------------------------------------------------

		// Client verification
    	key = json.get("key").toString();
		HMAC client_hmac = new HMAC(key);
    	state = client_hmac.verify(token, json);
    	System.out.println("Client verification: "+state);
		assertEquals(true, state);

	}
}
