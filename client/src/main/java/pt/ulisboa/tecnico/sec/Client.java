package pt.ulisboa.tecnico.sec;

import sun.print.resources.serviceui;
import java.security.Key;

import pt.ulisboa.tecnico.sec.client.API;
import pt.ulisboa.tecnico.sec.client.Session;

public class Client {

	private Session session;
	private API api;

	public Client(Session session, String address, int port){
		this.api = new API(address, port, session.getPublicKey());
		this.session = session;
	}

	public boolean register(){
		int n = 0;
		while(true){
			n++;
			if(n > 3){
				System.out.println("Error on register!");
				return false;
			}
			try{
				String[] response = api.register();
				String key = response[0];
				String id = response[1];
				api.confirm(id);
				api.enableDigitalSignature(key);
			}
			catch(Exception e){
				System.out.println("Error on register! Trying again...");
			}
		}
	}

	public boolean savePassword(String dmain, String usrname, String passwd){
		String domain = rsa.encrypt(dmain);
		String username = rsa.encrypt(usrname);
		String password = rsa.encrypt(passwd);
		return api.put(session.getPublicKey(),domain,username,password);
	}

	public String retrievePassword(String dmain, String usrname){
		String domain = rsa.encrypt(dmain);
		String username = rsa.encrypt(usrname);
		return api.get(session.getPublicKey(),domain,username);
	}
}
