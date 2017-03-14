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
      Generator.generateStore(username, password);
      File store = new File("data/" + username + ".jce");
      FileInputStream fis = new FileInputStream(store);
      KeyStore ksa = KeyStore.getInstance("JCEKS");
      ksa.load(fis, password.toCharArray());
      fis.close();
      PrivateKey k = (PrivateKey) ksa.getKey("privateKey", password.toCharArray());
      Certificate c = ksa.getCertificate("privateKey");
      PublicKey p = c.getPublicKey();
      SecretKey s = (SecretKey) ksa.getKey("secretKey", password.toCharArray());
      store.delete();
    }
}
