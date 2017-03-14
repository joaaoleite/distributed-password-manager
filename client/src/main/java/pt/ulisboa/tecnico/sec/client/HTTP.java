package pt.ulisboa.tecnico.sec.client;

import java.security.Key;
import org.json.JSONObject;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;

import pt.ulisboa.tecnico.sec.lib.http.*;
import pt.ulisboa.tecnico.sec.lib.exceptions.*;

import pt.ulisboa.tecnico.sec.exceptions.*;

public class HTTP {

	private Nounces nounces;
	private DigitalSignature signature;

	// Constructor
	public HTTP(Nounces nounces, DigitalSignature signature){
		this.signature = signature;
		this.nounces = nounces;
	}
	public HTTP(Nounces nounces){
		this.nounces = nounces;
	}

	// Signature
	public void sign(String key) {
		try{
			this.signature = new DigitalSignature(key);
		}
		catch(Exception e){ }
	}

	// POST
	public JSONObject post(String url, JSONObject json) throws UnirestException, RepetedNounceException{
		json.put("nounce",nounces.generate());
		String body = json.toString();
		HttpResponse<JsonNode> request = Unirest.post(url)
			.header("accept", "application/json")
			.body(body)
			.asJson();

		JSONObject response = request.getBody().getObject();

		// Verify Nounce
		String nounce = response.getString("nounce");
		if(!nounces.verify(nounce))
			throw new RepetedNounceException(nounce);

		JSONObject res = request.getBody().getObject();
		res.remove("nounce");
		return res;
	}
	public JSONObject signedPost(String url, JSONObject json) throws UnirestException, ExpiredDigitalSignatureException, DigitalSignatureErrorException, RepetedNounceException {

		json.put("nounce",nounces.generate());

		String reqToken = signature.sign(json);
		String body = json.toString();

		System.out.println("BODY: "+body);

		HttpResponse<JsonNode> request = Unirest.post(url)
			.header("accept", "application/json")
			.header("Authorization", "Bearer "+reqToken)
			.body(body)
			.asJson();

		JSONObject response = request.getBody().getObject();

		// Verify Nounce
		String nounce = response.getString("nounce");
		if(!nounces.verify(nounce))
			throw new RepetedNounceException(nounce);

		String token = request.getHeaders().get("Authorization").get(0).split("Bearer ")[1];
		if(!signature.verify(token, response))
			throw new DigitalSignatureErrorException(token);

		response.remove("nounce");
		return response;
	}
}
