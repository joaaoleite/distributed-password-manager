package pt.ulisboa.tecnico.sec;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.PublicKey;

public class Session {
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private Certificate certificate;

	public PrivateKey getPrivateKey(){
		return privateKey;
	}
	public PublicKey getPublicKey(){
		return publicKey;
	}
	public Certificate getCertificate(){
		return certificate;
	}

	public boolean login(String username, String passwd){
		try{
			char[] password = passwd.toCharArray();

			FileInputStream fis = new FileInputStream("../keystore/data/" + username + ".jce");
			KeyStore ksa = KeyStore.getInstance("JCEKS");

			ksa.load(fis, password);
			fis.close();

			privateKey = (PrivateKey) ksa.getKey(username, password);
			certificate = ksa.getCertificate(username);
			publicKey = certificate.getPublicKey();
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
