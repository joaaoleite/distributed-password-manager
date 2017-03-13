package pt.ulisboa.tecnico.sec.client;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.crypto.SecretKey;
import java.security.KeyPair;

import pt.ulisboa.tecnico.sec.cryptography.*;

public class Session {
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private SecretKey secretKey;
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

	public AES AES(){
		try{
		 	return new AES(secretKey);
	 	}
		catch(Exception e){
			return null;
		}
	}
	public RSA RSA(){
		try{
			return new RSA(publicKey, privateKey);
		}
		catch(Exception e){
			return null;
		}
	}

	public boolean login(String username, String passwd){
		try{
			char[] password = passwd.toCharArray();

			FileInputStream fis = new FileInputStream("../keystore/data/" + username + ".jce");
			KeyStore ksa = KeyStore.getInstance("JCEKS");

			ksa.load(fis, password);
			fis.close();

			privateKey = (PrivateKey) ksa.getKey("privateKey", password);
			certificate = (X509Certificate) ksa.getCertificate(username);
			certificate.checkValidity();
			secretKey = (SecretKey) ksa.getKey("secretKey", password);
			publicKey = certificate.getPublicKey();
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
