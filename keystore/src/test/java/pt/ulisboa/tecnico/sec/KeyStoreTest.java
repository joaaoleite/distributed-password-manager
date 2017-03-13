package pt.ulisboa.tecnico.sec;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyStore;
import java.io.File;
import java.security.cert.X509Certificate;
import java.security.cert.Certificate;
/**
 * Unit test for simple App.
 */
public class KeyStoreTest extends TestCase
{
    public void test() throws Exception{

      File dir = new File("data");
      dir.mkdirs();

      KeyPair pair = Generator.generateKeyPair();
      PrivateKey privateKey = pair.getPrivate();
      PublicKey publicKey = pair.getPublic();
      X509Certificate[] cert = Generator.generateCertificate(pair);

      String username = "user";
      char[] password = "pass".toCharArray();

      Generator.saveStore(username, password, privateKey, cert);

      FileInputStream fis = new FileInputStream("data/" + username + ".jce");
      KeyStore ksa = KeyStore.getInstance("JCEKS");
      ksa.load(fis, password);
      fis.close();
      PrivateKey k = (PrivateKey) ksa.getKey(username, password);
      Certificate c = ksa.getCertificate(username);
      PublicKey pk = c.getPublicKey();

      assertEquals(privateKey, k);
      assertEquals(cert[0], c);
      assertEquals(publicKey, pk);
    }
}
