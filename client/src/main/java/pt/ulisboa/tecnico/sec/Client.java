package pt.ulisboa.tecnico.sec;

import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class Client{

	private String endpoint;

	public Client(String address, int port){
		this.endpoint = "http://" + address + ":" + port + "/";
	}

	public void test(){
		try {

			JSONObject json = new JSONObject();
			json.put("message", "Example message from client!");

			String body = json.toString();

			HttpResponse<JsonNode> request = Unirest.post(endpoint + "/test")
				.header("accept", "application/json")
				.body(body)
				.asJson();
			JSONObject response = request.getBody().getObject();
			String msg = response.getString("message");
			System.out.println("Response from server: "+msg);
		}
		catch(UnirestException e){
			System.out.println("Request error!");
		}
	}

	public static void main( String[] args ){

		String address = args[0];
		int port = Integer.parseInt(args[1]);

		System.out.println("Creating client...");
		System.out.println("Requests are going to "+address+":"+port);

		Client client = new Client(address, port);

		System.out.println("Making test request...");

		client.test();
	}
}
