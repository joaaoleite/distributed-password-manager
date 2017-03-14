package pt.ulisboa.tecnico.sec.client;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.PublicKey;
import java.security.Key;
import java.security.cert.X509Certificate;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.util.Base64;

import pt.ulisboa.tecnico.sec.lib.crypto.*;

public class Session {
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private SecretKey secretKey;
	private X509Certificate certificate;
	private KeyStore ksa;
	private char[] password;
	private String username;
	private Key macKey;

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
	public Key getMacKey(){
		return macKey;
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

	public boolean login(String usrname, String passwd){
		try{
			this.password = passwd.toCharArray();
			this.username = usrname;

			FileInputStream fis = new FileInputStream("../keystore/data/" + username + ".jce");
			ksa = KeyStore.getInstance("JCEKS");

			ksa.load(fis, password);
			fis.close();

			privateKey = (PrivateKey) ksa.getKey("privateKey", password);
			certificate = (X509Certificate) ksa.getCertificate("privateKey");
			certificate.checkValidity();
			secretKey = (SecretKey) ksa.getKey("secretKey", password);
			publicKey = certificate.getPublicKey();
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public void saveMacKey(String stringKey){
		try{
			byte[] keyBytes = Base64.getDecoder().decode(stringKey);
			Key key = new SecretKeySpec(keyBytes, "AES");
			KeyStore.SecretKeyEntry keyEntry = new KeyStore.SecretKeyEntry((SecretKey)key);
			KeyStore.PasswordProtection passEntry = new KeyStore.PasswordProtection(password);
			ksa.setEntry("mac", keyEntry, passEntry);

			FileOutputStream fos = new FileOutputStream("../keystore/data/" + username + ".jce");
			ksa.store(fos, password);
			fos.close();
			this.macKey = key;
		}catch(Exception e){
			return;
		}
	}
}
