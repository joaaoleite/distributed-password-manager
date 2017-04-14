package pt.ulisboa.tecnico.sec.client.http;

import java.util.concurrent.Callable;

import org.json.JSONObject;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;

import pt.ulisboa.tecnico.sec.lib.http.SeqNumber;
import pt.ulisboa.tecnico.sec.lib.exceptions.*;
import pt.ulisboa.tecnico.sec.client.Session;

public class RequestWork implements Callable<JSONObject> {

	private final String url;
	private Session session;
	private JSONObject json;
	private int replica;

	public RequestWork(String url, JSONObject json, int replica) {
		this.url = url;
		this.session = Session.getInstance();
		this.json = json;
		this.replica = replica;
	}

	public String getUrl() {
		return this.url;
	}

	public JSONObject call() throws Exception {

		if(this.json==null)
			this.json = new JSONObject();
		try{
			json = session.SeqNumber(replica).request(json);
		}
		catch(NullPointerException e){
			e.printStackTrace();
		}


		String reqToken = session.DigitalSignature().sign(json);

		String body = json.toString();

		HttpResponse<JsonNode> request = Unirest.post(url)
			.header("accept", "application/json")
			.header("Authorization", "Bearer "+reqToken)
			.body(body)
			.asJson();

		JSONObject response = request.getBody().getObject();

		System.out.println(response);

		try{
			String resToken = request.getHeaders().get("Authorization").get(0).split("Bearer ")[1];
			if(!session.HMAC().verify(resToken, response))
				throw new DigitalSignatureErrorException(resToken);
		}
		catch(NullPointerException e){ }
		catch(IndexOutOfBoundsException e){ }
		//catch(java.security.InvalidKeyException e){ }

		if(session.SeqNumber(replica)==null){
			session.SeqNumber(replica,response.getLong("seq"));
		}
		else if(!session.SeqNumber(replica).verify(json))
			throw new RepetedNounceException("");

		return response;
	}
}