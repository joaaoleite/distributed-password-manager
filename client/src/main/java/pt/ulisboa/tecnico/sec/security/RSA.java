package pt.ulisboa.tecnico.sec.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

public class RSA {

	private PublicKey publicKey;
	private PrivateKey privateKey;
	private Cipher cipher;

	public RSA(PublicKey publicKey, PrivateKey privateKey){
		this.publicKey = publicKey;
		this.privateKey = privateKey;
		this.cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	}

	public RSA(KeyPair keys){
		this.publicKey = keys.getPublic();
		this.privateKey = keys.getPrivate();
	}

	public String encrypt(String text){
        cipher.init(Cipher.ENCRYPT_MODE, keys.getPublic());
        byte[] cipherBytes = cipher.doFinal(plainText.getBytes());
		return DatatypeConverter.printBase64Binary(cipherBytes);
	}

	public String decrypt(String text){
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] plainBytes = cipher.doFinal(DatatypeConverter.parseBase64Binary(text));
		return new String(plainBytes);
	}

	public static KeyPair generateKeys(){
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        return keyGen.generateKeyPair();
	}
}
