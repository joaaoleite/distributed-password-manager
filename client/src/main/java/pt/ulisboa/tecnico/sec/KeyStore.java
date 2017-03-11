package pt.ulisboa.tecnico.sec;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public class KeyStore {

	public static KeyStore getInstance(String username, String password){
		return new KeyStore();
	}

	public PublicKey getPublicKey(){
		return null;
	}
	public PrivateKey getPrivateKey(){
		return null;
	}
	public X509Certificate getCertificate(){
		return null;
	}
}
