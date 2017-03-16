package pt.ulisboa.tecnico.sec.lib;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import pt.ulisboa.tecnico.sec.lib.crypto.*;
import pt.ulisboa.tecnico.sec.lib.http.*;
import pt.ulisboa.tecnico.sec.lib.exceptions.*;

import java.util.Base64;
import javax.crypto.SecretKey;

public class RSANoPaddingTest extends TestCase {

	public void testRSAPublicEncryption() throws Exception{
		System.out.println("---TESTING RSA-PUBLIC ENCRYPTION---");

		System.out.println("Creating RSA class with keys generated...");
		RSANoPadding rsa = new RSANoPadding(RSA.generateKeys());

		String plainText = "ThisIsAPlainTextReadyToBeEncryptedInRSAWithPublicKey";
		System.out.println("PlainText: " + plainText);

		System.out.println("Encrypting...");
		String encrypted1 = rsa.encrypt(plainText);
		System.out.println("Encrypted1: " + encrypted1);

		String encrypted2 = rsa.encrypt(plainText);
		System.out.println("Encrypted2: " + encrypted2);

		System.out.println("Decrypting...");
		String decrypted = rsa.decrypt(encrypted1);
		System.out.println("Decrypted: " + decrypted);

		System.out.println("Success!");
	}
}
