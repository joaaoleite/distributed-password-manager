package pt.ulisboa.tecnico.sec.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;

public class RSA {

	private PublicKey publicKey;
	private PrivateKey privateKey;
	private Cipher cipher;

	public RSA(PublicKey publicKey, PrivateKey privateKey)throws Exception{
		this.publicKey = publicKey;
		this.privateKey = privateKey;
		this.cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	}

	public RSA(KeyPair keys){
		this.publicKey = keys.getPublic();
		this.privateKey = keys.getPrivate();
	}

	public String encrypt(String text) throws Exception{
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherBytes = cipher.doFinal(text.getBytes());
		return DatatypeConverter.printBase64Binary(cipherBytes);
	}

	public String decrypt(String text) throws Exception{
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] plainBytes = cipher.doFinal(DatatypeConverter.parseBase64Binary(text));
		return new String(plainBytes);
	}

	public static KeyPair generateKeys()throws Exception{
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    keyGen.initialize(2048, random);
    KeyPair pair = keyGen.generateKeyPair();
    return pair;
	}
}
