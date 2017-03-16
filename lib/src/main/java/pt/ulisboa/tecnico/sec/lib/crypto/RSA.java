package pt.ulisboa.tecnico.sec.lib.crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import java.security.SecureRandom;
import java.util.Base64;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

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

	public RSA(String pubKeyString) throws Exception {
		this.publicKey = stringToPublicKey(pubKeyString);
	}

	public String encrypt(String plainText) throws Exception{
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] textInBytes = plainText.getBytes("UTF-8");
		byte[] cipheredBytes = cipher.doFinal(textInBytes);
		return Base64.getEncoder().encodeToString(cipheredBytes);
	}

	public String decrypt(String cipheredText) throws Exception{
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] cipheredBytes = Base64.getDecoder().decode(cipheredText);
		byte[] textInBytes = cipher.doFinal(cipheredBytes);
		return new String(textInBytes, "UTF-8");
	}

	public static KeyPair generateKeys() throws Exception{
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		keyGen.initialize(2048, random);
		KeyPair pair = keyGen.generateKeyPair();
		return pair;
	}
	public static PublicKey stringToPublicKey(String publicKey) throws Exception{
		byte[] publicBytes = Base64.getDecoder().decode(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		return pubKey;
	}
	public static String publicKeyToString(PublicKey publicKey) throws Exception{
		return Base64.getEncoder().encodeToString(publicKey.getEncoded());
	}

	public PublicKey getPublicKey(){
		return publicKey;
	}
	public PrivateKey getPrivateKey(){
		return privateKey;
	}
}
