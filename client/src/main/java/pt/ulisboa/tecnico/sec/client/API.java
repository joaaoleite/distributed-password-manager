package pt.ulisboa.tecnico.sec.client;

import java.security.PublicKey;
import java.util.Base64;
import org.json.JSONObject;

import pt.ulisboa.tecnico.sec.lib.http.*;

import pt.ulisboa.tecnico.sec.exceptions.*;

public class API{

	private String endpoint;
	private HTTP http;
	private String publicKey;

	public API(String address, int port, PublicKey key){
		this.endpoint = "http://" + address + ":" + port;
		this.publicKey = Base64.getEncoder().encodeToString(key.getEncoded());
		this.http = new HTTP(new Nounces(key));
	}
	public void enableDigitalSignature(String key){
		http.sign(key);
	}

	public String[] register() throws RegisterFailException{
		try {
			JSONObject params = new JSONObject();
			params.put("publicKey", publicKey);
			JSONObject response = http.post(endpoint+"/register", params);
			String key = response.getString("key");
			String id = response.getString("id");
			return new String[] {key, id};
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
			JSONObject response = http.signedPost(endpoint+"/confirm", params);
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
			JSONObject response = http.signedPost(endpoint+"/put", params);
			String status = response.getString("status");
			if(!status.equals("ok"))
				throw new PutFailException(status);
		}
		catch(Exception e){
			throw new PutFailException();
		}
	}

	public String get(String domain, String username) throws GetFailException{
		try {
			JSONObject params = new JSONObject();
			params.put("publicKey", publicKey);
			params.put("domain", domain);
			params.put("username", username);
			JSONObject response = http.signedPost(endpoint+"/get", params);
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
