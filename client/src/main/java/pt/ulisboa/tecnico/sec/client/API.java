package pt.ulisboa.tecnico.sec.client;

import java.security.PublicKey;
import org.json.JSONObject;
import java.security.Key;

import pt.ulisboa.tecnico.sec.lib.http.*;
import pt.ulisboa.tecnico.sec.lib.crypto.RSA;

import pt.ulisboa.tecnico.sec.exceptions.*;

public class API{

	private String endpoint;
	private HTTP http;
	private String publicKey;

	public API(String address, int port){
		this.endpoint = "http://" + address + ":" + port;
		try{
			this.publicKey = RSA.publicKeyToString(Session.getInstance().getPublicKey());
		}catch(Exception e){ }
		this.http = new HTTP();
	}

	public String[] register() throws RegisterFailException{
		try {
			JSONObject params = new JSONObject();
			params.put("publicKey", publicKey);
			JSONObject response = http.post(endpoint+"/register", params);
			String seqNumber = response.getLong("seq")+"";
			String hmacKey = response.getString("key");
			return new String[]{seqNumber, hmacKey};
		}
		catch(Exception e){
			throw new RegisterFailException();
		}
	}

	public String[] init() throws RegisterFailException{
		try {
			JSONObject params = new JSONObject();
			params.put("publicKey", publicKey);
			JSONObject response = http.post(endpoint+"/init", params);
			String seqNumber = response.getLong("seq")+"";
			String hmacKey = response.getString("key");
			return new String[]{seqNumber, hmacKey};
		}
		catch(Exception e){
			throw new RegisterFailException();
		}
	}

	public void confirm(String id) throws ConfirmFailException{
		try {
			JSONObject params = new JSONObject();
			params.put("publicKey", publicKey);
			params.put("id", id);
			JSONObject response = http.post(endpoint+"/confirm", params);
			String status = response.getString("status");
			if(!status.equals("ok"))
				throw new ConfirmFailException(status);
		}
		catch(Exception e){
			throw new ConfirmFailException();
		}
	}

	public void put(String domain, String username, String password) throws PutFailException {
		try {
			JSONObject params = new JSONObject();
			params.put("publicKey", publicKey);
			params.put("domain", domain);
			params.put("username", username);
			params.put("password", password);
			JSONObject response = http.post(endpoint+"/put", params);
			String status = response.getString("status");
			if(!status.equals("ok"))
				throw new PutFailException(status);
		}
		catch(Exception e){
			e.printStackTrace();
			throw new PutFailException();
		}
	}

	public String get(String domain, String username) throws GetFailException{
		try {
			JSONObject params = new JSONObject();
			params.put("publicKey", publicKey);
			params.put("domain", domain);
			params.put("username", username);
			JSONObject response = http.post(endpoint+"/get", params);
			String password = response.getString("password");
			if(password.equals("") || password==null)
				throw new GetFailException();
			return password;
		}
		catch(Exception e){
			throw new GetFailException();
		}
	}
}
