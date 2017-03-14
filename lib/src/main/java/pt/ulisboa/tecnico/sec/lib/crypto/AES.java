package pt.ulisboa.tecnico.sec.lib.crypto;

import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;
import java.security.SecureRandom;
import java.util.Base64;

public class AES {

	private SecretKey secretKey;
	private Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

	public AES(SecretKey secretKey) throws Exception{
		this.secretKey = secretKey;
	}

	public String encrypt(String plainText) throws Exception{
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] textInBytes = Base64.getDecoder().decode(plainText);
		byte[] cipheredBytes = cipher.doFinal(textInBytes);
		String cipheredText = Base64.getEncoder().encodeToString(cipheredBytes);
		return cipheredText;
	}

	public String decrypt(String cipheredText) throws Exception{
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] cipheredBytes = Base64.getDecoder().decode(cipheredText);
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
