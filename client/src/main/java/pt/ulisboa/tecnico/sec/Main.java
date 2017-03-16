package pt.ulisboa.tecnico.sec;

import java.io.Console;

import pt.ulisboa.tecnico.sec.client.Session;
import pt.ulisboa.tecnico.sec.Client;

import java.util.logging.*;

public class Main {

	private static final String COMMANDS = "\n>>> Available commands: \n" +
		"1: INIT \n" +
		"2: REGISTER USER \n" +
		"3: SAVE PASSWORD \n" +
		"4: RETRIEVE PASSWORD \n" +
		"0: CLOSE \n";

	private static boolean invalid(String command){
		try{
			int number = Integer.parseInt(command);
			return number<0 || number>4;
		}catch(Exception e){
			return true;
		}
	}

	public static void main( String[] args ){

		String address = args[0];
		int port = Integer.parseInt(args[1]);
		Console c = System.console();

		System.out.println("\nRequests are going to "+address+":"+port);
		System.out.println("====================\nCLIENT\n====================");

		Client client = null;

		while(true){
			String command = "";
			while(invalid(command))
				command = c.readLine(COMMANDS+"Enter command: ");

			int option = Integer.parseInt(command);

			while(option==1){
				System.out.println("\n===== INIT =====");
				String username = c.readLine("Master username: ");
				String password = c.readLine("Master password: ");
				Session session = Session.newInstance();
				if(session.login(username,password)){
					client = new Client(session, address, port);
					System.out.println(">>> Login successful!");
					break;
				}
				else System.out.println(">>> Login error!");
			}

			if(client==null){
				System.out.println(">>> Call INIT!");
				continue;
			}

			if(option==2){
				System.out.println("\n===== REGISTER =====");

				if(client.register())
					System.out.println(">>> User registered successful!");
				else
					System.out.println(">>> Register failed! Already registered?");
			}
			if(option==3){
				System.out.println("\n===== SAVE PASSWORD =====");
				String domain = c.readLine("Domain: ");
				String username = c.readLine("Username: ");
				String password = c.readLine("Password: ");

				if(client.savePassword(domain,username,password))
					System.out.println(">>> Password saved successful!");
				else
					System.out.println(">>> Error! Did you registered?");
			}
			if(option==4){
				System.out.println("\n===== GET PASSWORD =====");
				String domain = c.readLine("Domain: ");
				String username = c.readLine("Username: ");
				String password = client.retrievePassword(domain,username);

				if(password!=null)
					System.out.println(">>> PASSWORD: "+password);
				else
					System.out.println(">>> Domain or Username does not exists!");
			}
			if(option==0){
				client = null;
				System.out.println("\n====================\n>>> Goodbye!");
				System.out.println(">>> To log to another user call INIT again...");
			}
		}
	}
}
