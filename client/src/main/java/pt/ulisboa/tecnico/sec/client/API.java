package pt.ulisboa.tecnico.sec.client;

import java.security.PublicKey;
import java.util.Base64;

import pt.ulisboa.tecnico.sec.security.Nounces;
import pt.ulisboa.tecnico.sec.security.DigitalSignature;

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

	public String[] register(){
		try {
			JSONObject params = new JSONObject();
			params.put("publicKey", publicKey);
			JSONObject response = http.post(endpoint+"/register", params);
			String key = response.getString("key");
			String id = response.getString("key");
			return new String[] {key, id};
		}
		catch(Exception e){
			throw new RegisterFailException();
		}
	}

	public void confirm(String id){
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

	public void put(String domain, String username, String password){
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

	public String get(String domain, String username){
		try {
			JSONObject params = new JSONObject();
			params.put("publicKey", publicKey);
			params.put("domain", domain);
			params.put("username", username);
			JSONObject response = http.signedPost(endpoint+"/put", params);
			String password = response.getString("password");
			if(password.equals("") || password==null)
				throw new GetFailException(status);
		}
		catch(Exception e){
			throw new GetFailException();
		}
	}
}
