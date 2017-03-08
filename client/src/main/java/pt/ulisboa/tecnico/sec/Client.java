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
			HttpResponse<JsonNode> request = Unirest.get(endpoint + "/test")
				.header("accept", "application/json")
				.queryString("msg", "123")
				.asJson();
			JSONObject json = request.getBody().getObject();
			String msg = json.getString("message");
			System.out.println("Response message: "+msg);
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
