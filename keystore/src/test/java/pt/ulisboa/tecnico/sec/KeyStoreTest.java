package pt.ulisboa.tecnico.sec;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Scanner;
import java.security.KeyStore;

public class KeyStoreTest extends TestCase
{
    protected String username;
    protected String password;

    @BeforeClass
    protected void setUp(){
      Scanner reader = new Scanner(System.in);
      System.out.print("Enter username: ");
      this.username = reader.next();
      System.out.print("Enter password: ");
      this.password = reader.next();
      Generator.generateStore(username, password);
    }

    @AfterClass
    protected

    @Test
    public void test() throws Exception{
      FileInputStream fis = new FileInputStream("data/" + username + ".jce");
      KeyStore ksa = KeyStore.getInstance("JCEKS");
      ksa.load(fis, password);
      fis.close();
      PrivateKey k = (PrivateKey) ksa.getKey(username, password.toCharArray());
      Certificate c = ksa.getCertificate(username);
      PublicKey pk = c.getPublicKey();


    }
}
