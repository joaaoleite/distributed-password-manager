package pt.ulisboa.tecnico.sec;


import java.security.Key;

import pt.ulisboa.tecnico.sec.client.API;
import pt.ulisboa.tecnico.sec.client.Session;
import pt.ulisboa.tecnico.sec.lib.crypto.Hash;

public class Client {

	private Session session;
	private API api;
	private boolean started;

	public Client(Session session, String address, int port, int replicas){
		this.session = session;
		this.started = false;
		this.api = new API(address, port, replicas);
		this.init();
	}
	public boolean register(){
		if(started) return false;
		try{
			String hmacKey = api.register();
			session.HMAC(session.RSA().decrypt(hmacKey));
			this.started = true;
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	public boolean init(){
		try{
			String hmacKey = api.init();
			if(hmacKey != null){
				session.HMAC(session.RSA().decrypt(hmacKey));
				this.started = true;
			}
			else {
				this.started = false;
			}
			return this.started;
		}
		catch(Exception e){
			return false;
		}
	}

	public boolean savePassword(String dmain, String usrname, String passwd){
		try{
			String domain = Hash.digest(session.RSANoPadding().encrypt(dmain));
			String username = Hash.digest(session.RSANoPadding().encrypt(usrname));
			String password = session.RSA().encrypt(passwd);
			api.put(domain,username,password);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	public String retrievePassword(String dmain, String usrname){
		try{
			String domain = Hash.digest(session.RSANoPadding().encrypt(dmain));
			String username = Hash.digest(session.RSANoPadding().encrypt(usrname));
			String password = api.get(domain,username);
			return session.RSA().decrypt(password);
		}
		catch(Exception e){
			return null;
		}
	}
}
