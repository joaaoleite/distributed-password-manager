package pt.ulisboa.tecnico.sec.lib.crypto;

import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.spec.IvParameterSpec;

public class AES {

	private SecretKey secretKey;
	private Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

	public AES(SecretKey secretKey) throws Exception{
		this.secretKey = secretKey;
	}

	public AES(String secretKeyString) throws Exception{
		byte[] decodedKey = Base64.getDecoder().decode(secretKeyString);
		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		this.secretKey = originalKey;
	}

	public String encrypt(String plainText) throws Exception{
		IvParameterSpec ivParameterSpec = new IvParameterSpec(secretKey.getEncoded());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
		byte[] textInBytes = plainText.getBytes("UTF-8");
		byte[] cipheredBytes = cipher.doFinal(textInBytes);
		return Base64.getEncoder().encodeToString(cipheredBytes);
	}

	public String decrypt(String cipheredText) throws Exception{
		IvParameterSpec ivParameterSpec = new IvParameterSpec(secretKey.getEncoded());
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
		byte[] cipheredBytes = Base64.getDecoder().decode(cipheredText);
		byte[] textInBytes = cipher.doFinal(cipheredBytes);
		return new String(textInBytes, "UTF-8");
	}

	public static SecretKey generateKey() throws Exception{
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		keyGen.init(128, random);
		SecretKey key = keyGen.generateKey();
		return key;
	}
}
