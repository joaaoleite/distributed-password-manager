
package pt.ulisboa.tecnico.sec;

public class Client {

	private Session session;

	public Client(Session session, String address, int port){
		this.session = session;
	}

	public void register(){

	}

	public void savePassword(String domain, String username, String password){

	}

	public String retrievePassword(String domain, String username){
		return "123456789";
	}
}
