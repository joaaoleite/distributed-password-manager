package pt.ulisboa.tecnico.sec.lib;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.json.JSONObject;

import pt.ulisboa.tecnico.sec.lib.crypto.*;
import pt.ulisboa.tecnico.sec.lib.http.*;
import pt.ulisboa.tecnico.sec.lib.exceptions.*;

public class DigitalSignatureTest extends TestCase {

	public void testClientToServer() throws Exception {
		System.out.println("---TESTING DigitalSignature---");
    JSONObject json;
    boolean state;
    String token;

    // Client init
		System.out.println("Client init...");
    RSA client_rsa = new RSA(RSA.generateKeys());
    DigitalSignature client_ds = new DigitalSignature(client_rsa.getPrivateKey());

    // Server init
		System.out.println("Server init...");
    RSA server_rsa = new RSA(RSA.generateKeys());
    DigitalSignature server_ds = new DigitalSignature(server_rsa.getPrivateKey());

    // Client request
    json = new JSONObject();
    json.put("Example","Request from client");
    System.out.println("Client JSON: "+json);
    token = client_ds.sign(json);
    System.out.println("Client header token: "+token);

    // Server verification
    server_ds.setPublicKey(client_rsa.getPublicKey());
    state = server_ds.verify(token, json);
    System.out.println("Server verification: "+state);
		assertEquals(true, state);


	}

	public void testServerToClient() throws Exception {
		System.out.println("---TESTING DigitalSignature---");
    JSONObject json;
    boolean state;
    String token;

    // Client init
		System.out.println("Client init...");
    RSA client_rsa = new RSA(RSA.generateKeys());
    DigitalSignature client_ds = new DigitalSignature(client_rsa.getPrivateKey());

    // Server init
		System.out.println("Server init...");
    RSA server_rsa = new RSA(RSA.generateKeys());
    DigitalSignature server_ds = new DigitalSignature(server_rsa.getPrivateKey());
		// Server response
		json = new JSONObject();
		json.put("Example","Response from server");
		System.out.println("Server JSON: "+json);
		token = server_ds.sign(json);
		System.out.println("Server header token: "+token);

		// Client verification
		client_ds.setPublicKey(server_rsa.getPublicKey());
		state = client_ds.verify(token, json);

		System.out.println("Client verification: "+state);

		System.out.println("\n\n\n");
		assertEquals(true, state);

	}

	public void testChangedServerToClient() throws Exception {
		System.out.println("---TESTING DigitalSignature---");
    JSONObject json;
    boolean state;
    String token;

    // Client init
		System.out.println("Client init...");
    RSA client_rsa = new RSA(RSA.generateKeys());
    DigitalSignature client_ds = new DigitalSignature(client_rsa.getPrivateKey());

    // Server init
		System.out.println("Server init...");
    RSA server_rsa = new RSA(RSA.generateKeys());
    DigitalSignature server_ds = new DigitalSignature(server_rsa.getPrivateKey());
		// Server response
		json = new JSONObject();
		json.put("Example","Response from server");
		System.out.println("Server JSON: "+json);
		token = server_ds.sign(json);
		System.out.println("Server header token: "+token);
		json = new JSONObject();
		json.put("Example","changed");

		// Client verification
		client_ds.setPublicKey(server_rsa.getPublicKey());
		state = client_ds.verify(token, json);

		System.out.println("Client verification: "+state);

		System.out.println("\n\n\n");
		assertEquals(false, state);

	}



}
