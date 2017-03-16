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
import pt.ulisboa.tecnico.sec.lib.http.*;

public class Session {

	private static Session session;

	private PrivateKey privateKey;
	private PublicKey publicKey;
	private SecretKey secretKey;
	private X509Certificate certificate;
	private PublicKey serverPublicKey;
	private KeyStore ksa;
	private char[] password;
	private String username;

	private DigitalSignature signature;
	private SeqNumber seq;

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
	public PublicKey setServerPublicKey(PublicKey serverPublicKey) throws Exception{
		this.serverPublicKey = serverPublicKey;
		this.signature = new DigitalSignature(privateKey, serverPublicKey);
		return serverPublicKey;
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
	public RSANoPadding RSANoPadding(){
		try{
			return new RSANoPadding(publicKey, privateKey);
		}
		catch(Exception e){
			return null;
		}
	}
	public DigitalSignature DigitalSignature(){
		try{
			if(signature == null)
				return new DigitalSignature(privateKey);
			else
				return this.signature;
		}
		catch(Exception e){
			return null;
		}
	}
	public void DigitalSignature(String serverPublicKey) throws Exception{
		this.serverPublicKey = RSA.stringToPublicKey(serverPublicKey);
		this.signature = new DigitalSignature(privateKey,this.serverPublicKey);
	}
	public SeqNumber SeqNumber(){
		return seq;
	}
	public void SeqNumber(String seq) throws Exception{
		this.seq = new SeqNumber(Long.parseLong(seq));
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
	public static Session newInstance(){
		session = new Session();
		return session;
	}
	public static Session getInstance(){
		if(session == null)
			session = new Session();
		return session;
	}
}
