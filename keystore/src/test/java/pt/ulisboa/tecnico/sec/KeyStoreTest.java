package pt.ulisboa.tecnico.sec;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Scanner;
import java.security.KeyStore;
import java.io.FileInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.io.File;
import javax.crypto.SecretKey;

public class KeyStoreTest extends TestCase
{
    public void test() throws Exception{
      String username = "user";
      String password = "test123";
      System.out.println("Generating keys...");
      Generator.generateStore(username, password);

      System.out.println("Retrieving KeyStoreFile...");
      File store = new File("data/" + username + ".jce");
      FileInputStream fis = new FileInputStream(store);
      KeyStore ksa = KeyStore.getInstance("JCEKS");

      System.out.println("Loading KeyStore...");
      char[] pass = password.toCharArray();
      ksa.load(fis, pass);
      fis.close();

      System.out.println("Retrieving PrivateKey...");

      PrivateKey k = (PrivateKey) ksa.getKey("privateKey", pass);
      System.out.println("Retrieving PublicKey...");
      Certificate c = ksa.getCertificate("privateKey");
      PublicKey p = c.getPublicKey();
      System.out.println("Success! Deleting test KeyStoreFile...");
      store.delete();
    }
}
