package pt.ulisboa.tecnico.sec.client;

import java.security.PublicKey;
import org.json.JSONObject;
import java.security.Key;

import pt.ulisboa.tecnico.sec.lib.http.*;
import pt.ulisboa.tecnico.sec.lib.crypto.RSA;

import pt.ulisboa.tecnico.sec.exceptions.*;
import pt.ulisboa.tecnico.sec.client.http.Request;

public class API {

	private String publicKey;

	public API(String address, int port, int replicas){
		try{
			this.publicKey = RSA.publicKeyToString(Session.getInstance().getPublicKey());
		}catch(Exception e){ }
		Request.settings(address, port, replicas);
	}

	public String register() throws RegisterFailException{
		try {
			JSONObject params = new JSONObject();
			params.put("publicKey", publicKey);

			Request register = new Request("/register");
			register.make(params);
			JSONObject response = register.getResponse();

			String hmacKey = response.getString("key");
			return hmacKey;
		}
		catch(Exception e){
			e.printStackTrace();
			throw new RegisterFailException();
		}
	}

	public String init() throws RegisterFailException{
		try {
			JSONObject params = new JSONObject();
			params.put("publicKey", publicKey);

			Request init = new Request("/init");
			init.make(params);
			JSONObject response = init.getResponse();

			String hmacKey = response.getString("key");
			return hmacKey;
		}
		catch(Exception e){
			throw new RegisterFailException();
		}
	}

	/*public void confirm(String id) throws ConfirmFailException{
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
	}*/

	public void put(String domain, String username, String password) throws PutFailException {
		try {
			JSONObject params = new JSONObject();
			params.put("publicKey", publicKey);
			params.put("domain", domain);
			params.put("username", username);

			Request get = new Request("/get");
			get.make(params);
			long ts = get.getHigherTs();

			ts++;
			params = new JSONObject();
			params.put("publicKey", publicKey);
			params.put("domain", domain);
			params.put("username", username);
			params.put("timestamp", ts);
			params.put("password", password);

			Request put = new Request("/put");
			put.make(params);
			JSONObject response = put.getResponse();

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

			Request get = new Request("/get");
			get.make(params);
			JSONObject response = get.getResponse();

			params.put("timestamp", response.getString("timestamp"));
			String password = response.getString("password");
			params.put("password", password);

			Request put = new Request("/put");
			put.make(params);
			response = put.getResponse();

			if(password.equals("") || password==null)
				throw new GetFailException();
			return password;
		}
		catch(Exception e){
			System.out.println(e);
			throw new GetFailException();
		}
	}
}
