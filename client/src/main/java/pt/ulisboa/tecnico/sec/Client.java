
package pt.ulisboa.tecnico.sec;

public class Client {

	private KeyStore keyStore;

	public Client(KeyStore keyStore, String address, int port){
		this.keyStore = keyStore;
	}

	public void register(){

	}

	public void savePassword(String domain, String username, String password){
	
	}

	public String retrievePassword(String domain, String username){
		return "123456789";
	}
}
