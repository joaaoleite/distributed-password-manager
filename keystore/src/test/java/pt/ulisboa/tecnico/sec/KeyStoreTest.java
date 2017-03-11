package pt.ulisboa.tecnico.sec;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Unit test for simple App.
 */
public class KeyStoreTest extends TestCase
{
    public KeyStoreTest(String testName)
    {
        super(testName);
    }

    public void testApp() throws Exception{
      Box box = Generator.generateKeyCertBox();
      Generator.write(box);

      FileInputStream fis = new FileInputStream("keys/test.dat");
      ObjectInputStream objectinputstream = new ObjectInputStream(fis);
      Box out = (Box) objectinputstream.readObject();

      fis.close();
      objectinputstream.close();
    }

    public void testApp2() throws Exception{
      Box box = Generator.generateKeyCertBox();
      Generator.write(box);

      FileInputStream fis = new FileInputStream("keys/test.dat");
      ObjectInputStream objectinputstream = new ObjectInputStream(fis);
      Box out = (Box) objectinputstream.readObject();

      fis.close();
      objectinputstream.close();
    }
}
