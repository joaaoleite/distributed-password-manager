package pt.ulisboa.tecnico.sec.lib;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import pt.ulisboa.tecnico.sec.lib.crypto.*;
import pt.ulisboa.tecnico.sec.lib.http.*;
import pt.ulisboa.tecnico.sec.lib.exceptions.*;

import java.util.Base64;
import javax.crypto.SecretKey;

public class AESTest extends TestCase {

	public void testAESKey() throws Exception{
		System.out.println("---TESTING AES KEY CREATION FROM STRING---");

		System.out.println("Generating key...");
		SecretKey key = AES.generateKey();

		System.out.println("Encoding Key...");
		String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
		System.out.println("Encoded Key: " + encodedKey);

		System.out.println("Creating AES class with key...");
		AES aes1 = new AES(key);

		System.out.println("Creating AES class with encodedKey...");
		AES aes2 = new AES(encodedKey);

		String plainText = "ThisIsAPlainTextReadyToBeEncryptedWithAES";
		System.out.println("PlainText: " + plainText);

		System.out.println("Encrypting with aes1...");
		String encrypted1 = aes1.encrypt(plainText);
		System.out.println("Encrypted1: " + encrypted1);

		System.out.println("Encrypting with aes2...");
		String encrypted2 = aes2.encrypt(plainText);
		System.out.println("Encrypted2: " + encrypted2);

		System.out.println("Decrypting with aes1...");
		String decrypted1 = aes1.decrypt(encrypted1);
		System.out.println("Decrypted1: " + decrypted1);

		System.out.println("Decrypting with aes2...");
		String decrypted2 = aes2.decrypt(encrypted2);
		System.out.println("Decrypted2: " + decrypted1);

		assertEquals(decrypted1, decrypted1);
		System.out.println("Success!");
	}

	public void testAESEncryption() throws Exception{
		System.out.println("---TESTING AES---");

		System.out.println("Creating AES class with key generated...");
		AES aes = new AES(AES.generateKey());

		String plainText = "ThisIsAPlainTextReadyToBeEncryptedWithAES";
		System.out.println("PlainText: " + plainText);

		System.out.println("Encrypting...");
		String encrypted = aes.encrypt(plainText);
		System.out.println("Encrypted: " + encrypted);

		System.out.println("Decrypting...");
		String decrypted = aes.decrypt(encrypted);
		System.out.println("Decrypted: " + decrypted);

		assertEquals(plainText, decrypted);
		System.out.println("Success!");
	}
}
