package pt.ulisboa.tecnico.sec.lib;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.json.JSONObject;

import pt.ulisboa.tecnico.sec.lib.crypto.*;
import pt.ulisboa.tecnico.sec.lib.http.*;
import pt.ulisboa.tecnico.sec.lib.exceptions.*;

public class NouncesTest extends TestCase {

	public void test() throws Exception {

		JSONObject json;

		// Client init
		System.out.println("Client init...");
    RSA rsa = new RSA(RSA.generateKeys());
    Nounces client = new Nounces(rsa);

		// Server init
		System.out.println("Server init...");
    Nounces server = new Nounces(rsa.getPublicKey());

    // Client request nounces to Server
		System.out.println("Client sent nounce request...");
    // ...

		// Server response with nounce
		System.out.println("Server received nounce request...");
    JSONObject nounceResponse = server.generate();
		System.out.println("Server nounce response: "+nounceResponse);

		// Client request (JSON with nounce)
		System.out.println("Client received nounce response...");

    json = new JSONObject();
    json.put("Example","Hello world");
		System.out.println("Client JSON: "+json);
    JSONObject result = client.request(nounceResponse, json);
		System.out.println("Client JSON with nounce: "+json);
		System.out.println("Client sent example request...");

		// Server verify request
		System.out.println("Server received example request...");
		boolean state = server.verify(result);
		System.out.println("Server verified nounce from client: "+state);

		// Server send response
		json = new JSONObject();
    json.put("Example","Hello world");
		System.out.println("Server JSON: "+json);
		json = server.response(json);
		System.out.println("Server JSON with nounce: "+json);

		// Client verification
		System.out.println("Client received response...");
		state = client.verify(json);
		System.out.println("Client verified nounce from server: "+state);

		System.out.println("\n\n\n");
	}
}
