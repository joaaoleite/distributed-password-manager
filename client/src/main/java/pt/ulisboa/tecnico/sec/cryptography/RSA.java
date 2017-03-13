package pt.ulisboa.tecnico.sec.cryptography;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import java.security.SecureRandom;
import java.util.Base64;

public class RSA {

	private PublicKey publicKey;
	private PrivateKey privateKey;
	private Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

	public RSA(PublicKey publicKey, PrivateKey privateKey) throws Exception{
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public RSA(PublicKey publicKey) throws Exception{
		this.publicKey = publicKey;
	}

	public RSA(KeyPair keys) throws Exception {
		this.publicKey = keys.getPublic();
		this.privateKey = keys.getPrivate();
	}

	public String encrypt(String plainText) throws Exception{
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] textInBytes = Base64.getDecoder().decode(plainText);
		byte[] cipheredBytes = cipher.doFinal(textInBytes);
		String cipheredText = Base64.getEncoder().encodeToString(cipheredBytes);
		return cipheredText;
	}

	public String decrypt(String cipheredText) throws Exception{
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] cipheredBytes = Base64.getDecoder().decode(cipheredText);
		byte[] textInBytes = cipher.doFinal(cipheredBytes);
		String plainText = Base64.getEncoder().encodeToString(textInBytes);
		return plainText;
	}

	public static KeyPair generateKeys() throws Exception{
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		keyGen.initialize(2048, random);
		KeyPair pair = keyGen.generateKeyPair();
		return pair;
	}
}
