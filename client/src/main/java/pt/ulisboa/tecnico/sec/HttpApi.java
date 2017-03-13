package pt.ulisboa.tecnico.sec;

import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Base64;
import pt.ulisboa.tecnico.sec.security.Nounce;

public class HttpApi{

	private String endpoint;

	public HttpApi(String address, int port){
		this.endpoint = "http://" + address + ":" + port + "/";
	}

	public void register(PublicKey publicKey, Nounce nounce){

		System.out.println("HTTP POST /register");

		try {
			JSONObject json = new JSONObject();
			json.put("nounce", nounce.toString());
			json.put("publicKey", publicKey);

			String body = json.toString();

			HttpResponse<JsonNode> request = Unirest.post(endpoint + "/register")
				.header("accept", "application/json")
				.body(body)
				.asJson();
			JSONObject response = request.getBody().getObject();
			String[] parts = request.getHeaders().get("Authorization").getValue().split(" ");
			String token = parts[parts.length-1];

			return new HttpResponse(token, response);
		}
		catch(UnirestException e){
			System.out.println("Request error!");
			return;  // TODO: throw Exception fail to register
		}
	}

	// TODO: change 'String publicKey' to 'Key publicKey'
	// TODO: throw Exception if fail to put
	public void put(String publicKey, byte[] domain, byte[] username, byte[] password){

		System.out.println("HTTP PUT /put");

		try {
			JSONObject json = new JSONObject();
			json.put("publicKey", publicKey);
			json.put("domain", new String(domain, StandardCharsets.UTF_8));
			json.put("username", new String(username, StandardCharsets.UTF_8));
			json.put("password", new String(password, StandardCharsets.UTF_8));

			String body = json.toString();

			HttpResponse<JsonNode> request = Unirest.post(endpoint + "/put")
				.header("accept", "application/json")
				.body(body)
				.asJson();
			JSONObject response = request.getBody().getObject();
			String status = response.getString("status");
			if(status!=null && status.equals("success"))
				return;  // TODO: throw Exception fail to put
		}
		catch(UnirestException e){
			System.out.println("Request error!");
			return;  // TODO: throw Exception fail to put
		}
	}

	// TODO: change 'String publicKey' to 'Key publicKey'
	// TODO: throw Exception if fail to get
	public byte[] get(String publicKey, byte[] domain, byte[] username){

		System.out.println("HTTP POST /get");

		try {
			JSONObject json = new JSONObject();
			json.put("publicKey", publicKey);
			json.put("domain", new String(domain, StandardCharsets.UTF_8));
			json.put("username", new String(username, StandardCharsets.UTF_8));

			String body = json.toString();

			HttpResponse<JsonNode> request = Unirest.post(endpoint + "/get")
				.header("accept", "application/json")
				.body(body)
				.asJson();
			JSONObject response = request.getBody().getObject();
			String passString = response.getString("password");

			if(passString!=null && passString.equals(""))
				return passString.getBytes(StandardCharsets.UTF_8);
			else
				return null;  // TODO: throw Exception fail to get
		}
		catch(UnirestException e){
			System.out.println("Request error!");
			return null;  // TODO: throw Exception fail to get
		}
	}
}
