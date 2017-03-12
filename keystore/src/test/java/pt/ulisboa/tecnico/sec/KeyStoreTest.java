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

      File dir = new File("test");
      dir.mkdirs();

      KeyPair pair = Generator.generateKeyPair();
      PrivateKey privateKey = pair.getPrivate();
      PublicKey publicKey = pair.getPublic();
      X509Certificate[] cert = Generator.generateCertificate(pair);

      String username = "user";
      char[] password = "pass".toCharArray();

      KeyStore ks = KeyStore.getInstance("JCEKS");
      ks.load(null, password);
      KeyStore.PrivateKeyEntry privEntry = new KeyStore.PrivateKeyEntry(privateKey, cert);
      KeyStore.PasswordProtection passwordEntry = new KeyStore.PasswordProtection(password);
      ks.setEntry(username, privEntry, passwordEntry);
      FileOutputStream fos = new FileOutputStream("test/" + username + ".jce");
      ks.store(fos, password);
      fos.close();

      FileInputStream fis = new FileInputStream("test/" + username + ".jce");
      KeyStore ksa = KeyStore.getInstance("JCEKS");
      ksa.load(fis, password);
      fis.close();
      PrivateKey k = (PrivateKey) ksa.getKey(username, password);
      Certificate c = ksa.getCertificate(username);
      PublicKey pk = c.getPublicKey();

      /*String priv = new String(Files.readAllBytes(Paths.get("test/private.key")));
      String pub = new String(Files.readAllBytes(Paths.get("test/public.key")));

      byte[] privateBytes = Base64.getDecoder().decode(priv);
      byte[] publicBytes = Base64.getDecoder().decode(pub);

      PKCS8EncodedKeySpec keySpecPriv = new PKCS8EncodedKeySpec(privateBytes);
      X509EncodedKeySpec keySpecPub = new X509EncodedKeySpec(publicBytes);

      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      PrivateKey privKey = keyFactory.generatePrivate(keySpecPriv);
      PublicKey pubKey = keyFactory.generatePublic(keySpecPub);*/

      assertEquals(privateKey, k);
      assertEquals(cert[0], c);
      assertEquals(publicKey, pk);
    }
}
