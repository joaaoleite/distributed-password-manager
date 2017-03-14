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

		String text = "gmail.com";
		System.out.println("TEXT: "+text);

		String encrypted = aes.encrypt(text);
		System.out.println("ENCRYPTED: "+encrypted);

		String decrypted = aes.decrypt(encrypted);
		System.out.println("DECRYPTED: "+decrypted);
	}
}
