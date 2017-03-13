
package pt.ulisboa.tecnico.sec;
import sun.print.resources.serviceui;
import java.security.Key;
import pt.ulisboa.tecnico.sec.security.RSA;

public class Client {

	private Session session;
	private HttpApi api;
	private RSA rsa;

	public Client(Session session, String address, int port){
		this.session = session;
		this.api = new HttpApi(address,port);
		this.rsa = new RSA(session.getKeyPair());
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
				Key key = api.register(session.getPublicKey());
				return api.confirm(key);
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
