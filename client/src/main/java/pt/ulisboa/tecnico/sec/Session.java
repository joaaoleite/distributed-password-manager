package pt.ulisboa.tecnico.sec;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public class Session {
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private X509Certificate certificate;

	public PrivateKey getPrivateKey(){
		return privateKey;
	}
	public PublicKey getPublicKey(){
		return publicKey;
	}
	public KeyPair getKeyPair(){
		return new KeyPair(publicKey,privateKey);
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
			certificate = (X509Certificate) ksa.getCertificate(username);
			certificate.checkValidity();
			publicKey = certificate.getPublicKey();
			return true;
		}catch(Exception e){
			System.out.println(e);
			return false;
		}
	}
}
