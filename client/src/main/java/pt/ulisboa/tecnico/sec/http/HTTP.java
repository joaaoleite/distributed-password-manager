package pt.ulisboa.tecnico.sec.http;

import java.security.Key;
import org.json.JSONObject;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;

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
	public JSONObject post(String url, JSONObject json) throws RepetedNounceException{
		json.put(nounces.generate());
		String body = json.toString();
		HttpResponse<JsonNode> request = Unirest.post(url)
			.header("accept", "application/json")
			.body(body)
			.asJson();

		// Verify Nounce
		String nounce = response.getString("nounce");
		if(!nounces.verify(nounce))
			throw new RepetedNounceException(nounce);

		return request.getBody().getObject().remove("nounce");
	}
	public JSONObject signedPost(String url, JSONObject json) throws DigitalSignatureErrorException, RepetedNounceException {

		json.put(nounces.generate());

		String reqToken = signature.sign(json);
		String body = json.toString();

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

		// Verify Digital Signature
		String[] parts = request.getHeaders().get("Authorization").getValue().split(" ");
		String token = parts[parts.length-1];
		if(!signature.verify(token, response))
			throw new DigitalSignatureErrorException(token);

		return response.remove("nounce");
	}
}
