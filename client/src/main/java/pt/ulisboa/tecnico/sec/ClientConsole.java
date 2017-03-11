package pt.ulisboa.tecnico.sec;
import java.io.Console;

public class ClientConsole {

	private static final String COMMANDS = ">>>Available commands: \n" +
		"1: INIT \n" +
		"2: REGISTER USER \n" +
		"3: SAVE PASSWORD \n" +
		"4: RETRIEVE PASSWORD \n" +
		"5: CLOSE \n";

	private static boolean invalid(String command){
		try{
			int number = Integer.parseInt(command);
			return number<1 || number>5;
		}catch(Exception e){
			return true;
		}
	}

	public ClientConsole(){
		Console c = System.console();

		String command = "";
		while(invalid(command))
			command = c.readLine(COMMANDS+">>> Enter command: ");

		System.out.println("Success!");

	}
}
