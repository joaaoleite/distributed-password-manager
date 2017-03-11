package pt.ulisboa.tecnico.sec;

import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class Client{

	public static void main( String[] args ){

		String address = args[0];
		int port = Integer.parseInt(args[1]);

		System.out.println("Creating client...");
		System.out.println("Requests are going to "+address+":"+port);

		//HttpApi httpApi = new HttpApi(address, port);
		//httpApi.register("abc123");

		ClientConsole c = new ClientConsole();
	}
}
