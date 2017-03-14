package pt.ulisboa.tecnico.sec.lib.crypto;

import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AES {

	private SecretKey secretKey;
	private Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

	public AES(SecretKey secretKey) throws Exception{
		this.secretKey = secretKey;
	}

	public AES(String secretKeyString) throws Exception{
		byte[] decodedKey = Base64.getDecoder().decode(secretKeyString);
		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		this.secretKey = originalKey;
	}

	public String encrypt(String plainText) throws Exception{
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] textInBytes = Base64.getDecoder().decode(plainText.getBytes("UTF8"));
		byte[] cipheredBytes = cipher.doFinal(textInBytes);
		String cipheredText = Base64.getEncoder().encodeToString(cipheredBytes);
		return cipheredText;
	}

	public String decrypt(String cipheredText) throws Exception{
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] cipheredBytes = Base64.getDecoder().decode(cipheredText.getBytes("UTF8"));
		byte[] textInBytes = cipher.doFinal(cipheredBytes);
		String plainText = Base64.getEncoder().encodeToString(textInBytes);
		return plainText;
	}

	public static SecretKey generateKey() throws Exception{
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		keyGen.init(128, random);
		SecretKey key = keyGen.generateKey();
		return key;
	}
}
