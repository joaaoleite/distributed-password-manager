package pt.ulisboa.tecnico.sec;

import java.io.Console;

public class Main {

	private static final String COMMANDS = ">>> Available commands: \n" +
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
				command = c.readLine(COMMANDS+" Enter command: ");

			int option = Integer.parseInt(command);

			if(option==1){
				String username = c.readLine("Master username: ");
				String password = c.readLine("Master password: ");
				try{
					KeyStore ks = KeyStore.getInstance(username,password);
					client = new Client(ks,address,port);
					System.out.println(">>> Login successful");
				}catch(Exception e){
					System.out.println(">>> Login error");
				}
			}

			if(client==null){
				System.out.println(">>> Call INIT!");
				continue;
			}

			if(option==2){
				client.register();
			}
			if(option==3){
				String domain = c.readLine("Domain: ");
				String username = c.readLine("Username: ");
				String password = c.readLine("Password: ");
				client.savePassword(domain,username,password);
			}
			if(option==4){
				String domain = c.readLine("Domain: ");
				String username = c.readLine("Username: ");
				client.retrievePassword(domain,username);
			}
			if(option==0){
				client = null;
				System.out.println("====================\nGoodbye!");
				System.out.println(">>> To log to another user call INIT again...");
			}
		}
	}
}
