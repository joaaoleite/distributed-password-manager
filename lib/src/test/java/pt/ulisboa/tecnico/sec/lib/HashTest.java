package pt.ulisboa.tecnico.sec.lib;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import pt.ulisboa.tecnico.sec.lib.crypto.Hash;

public class HashTest extends TestCase {

  public void testHash() throws Exception{
    System.out.println("---TESTING HASH---");

    String plainText1 = "ThisIsAPlainTextReadyToBeHashedWithSHA";
    System.out.println("PlainText1: " + plainText1);
    String plainText2 = "ThisIsAPlainTextReadyToBeHashedWithSHA";
    System.out.println("PlainText2: " + plainText2);

    System.out.println("Hashing...");
    String hash1 = Hash.digest(plainText1);
    System.out.println("Hash1: " + hash1);

    System.out.println("Hashing...");
    String hash2 = Hash.digest(plainText2);
    System.out.println("Hash2: " + hash2);

    assertEquals(hash1, hash2);
		System.out.println("Success!");
  }
}
