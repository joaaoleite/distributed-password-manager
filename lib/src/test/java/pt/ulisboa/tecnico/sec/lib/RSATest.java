package pt.ulisboa.tecnico.sec.lib;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import pt.ulisboa.tecnico.sec.lib.crypto.*;
import pt.ulisboa.tecnico.sec.lib.http.*;
import pt.ulisboa.tecnico.sec.lib.exceptions.*;

import java.util.Base64;
import javax.crypto.SecretKey;

public class RSATest extends TestCase {

	public void testRSAPublicEncryption() throws Exception{
		System.out.println("---TESTING RSA-PUBLIC ENCRYPTION---");

		System.out.println("Creating RSA class with keys generated...");
		RSA rsa = new RSA(RSA.generateKeys());

		String plainText = "ThisIsAPlainTextReadyToBeEncryptedInRSAWithPublicKey";
		System.out.println("PlainText: " + plainText);

		System.out.println("Encrypting...");
		String encrypted = rsa.encrypt(plainText);
		System.out.println("Encrypted: " + encrypted);

		System.out.println("Decrypting...");
		String decrypted = rsa.decrypt(encrypted);
		System.out.println("Decrypted: " + decrypted);

		assertEquals(plainText, decrypted);
		System.out.println("Success!");
	}

	public void testRSAPrivateEncryption() throws Exception{
		System.out.println("---TESTING RSA-PRIVATE ENCRYPTION---");

		System.out.println("Creating RSA class with keys generated...");
		RSA rsa = new RSA(RSA.generateKeys());

		String plainText = "ThisIsAPlainTextReadyToBeEncryptedInRSAWithPrivateKey";
		System.out.println("PlainText: " + plainText);

		System.out.println("Encrypting...");
		String encrypted = rsa.encryptWithPrivateKey(plainText);
		System.out.println("Encrypted: " + encrypted);

		System.out.println("Decrypting...");
		String decrypted = rsa.decryptWithPublicKey(encrypted);
		System.out.println("Decrypted: " + decrypted);

		assertEquals(plainText, decrypted);
		System.out.println("Success!");
	}
}
