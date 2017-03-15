package pt.ulisboa.tecnico.sec.lib;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import pt.ulisboa.tecnico.sec.lib.crypto.*;
import pt.ulisboa.tecnico.sec.lib.http.*;
import pt.ulisboa.tecnico.sec.lib.exceptions.*;

public class LibTest extends TestCase {

	public void test() throws Exception {

		AES aes = new AES(AES.generateKey());
		System.out.println("AES...");

		String text1 = "gmail.com";
		System.out.println("TEXT1: "+text1);
		System.out.println("SIZE1: "+text1.length());

		String encrypted1 = aes.encrypt(text1);
		String hash1 = Hash.digest(encrypted1);
		System.out.println("ENCRYPTED1: "+encrypted1);
		System.out.println("SIZE1: "+encrypted1.length());
		System.out.println("HASH1: "+hash1);
		System.out.println("SIZE1: "+hash1.length());

		String decrypted1 = aes.decrypt(encrypted1);
		System.out.println("DECRYPTED1: "+decrypted1);




		String text2 = "gmail.com";
		System.out.println("TEXT2: "+text2);
		System.out.println("SIZE2: "+text2.length());

		String encrypted2 = aes.encrypt(text2);
		String hash2 = Hash.digest(encrypted2);
		System.out.println("ENCRYPTED2: "+encrypted2);
		System.out.println("SIZE2: "+encrypted2.length());
		System.out.println("HASH2: "+hash2);
		System.out.println("SIZE2: "+hash2.length());

		String decrypted2 = aes.decrypt(encrypted2);
		System.out.println("DECRYPTED2: "+decrypted2);
	}
}
