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

public class KeyStoreTest extends TestCase
{
    private String username;
    private String password;

    protected void setUp() throws Exception{
      username = "user";
      password = "test123";
      Generator.generateStore(username, password);
    }

    private void cleanUp(){
      File store = new File("data/" + username + ".jce");
      store.delete();
    }

    public void test() throws Exception{
      FileInputStream fis = new FileInputStream("data/" + username + ".jce");
      KeyStore ksa = KeyStore.getInstance("JCEKS");
      ksa.load(fis, password.toCharArray());
      fis.close();
      PrivateKey k = (PrivateKey) ksa.getKey("privateKey", password.toCharArray());
      Certificate[] c = ksa.getCertificateChain("secretKey");
      System.out.println(k);
      System.out.println(c[0]);
      cleanUp();
    }
}
