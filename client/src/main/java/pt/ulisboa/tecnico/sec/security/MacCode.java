package pt.ulisboa.tecnico.sec;

//provides helper methods to print byte[]
import static javax.xml.bind.DatatypeConverter.printHexBinary;

import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

/** Generate a Message Authentication Code */
public class MacCode {
  private SecretKey key;

  public SecretKey getKey(){
      return this.key;
  }


  /** auxiliary method to generate SecretKey */
  public SecretKey generate() throws Exception {
    // generate a DES secret key
    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    keyGen.init(256); // for example
    key = keyGen.generateKey();

    return key;
  }

  /** auxiliary method to make the MAC */
  public String makeMAC(String text, SecretKey key) throws Exception {

    Mac cipher = Mac.getInstance("HmacMD5");
    cipher.init(key);
    byte[] cipherDigest = cipher.doFinal(text.getBytes());

    System.out.println("CipherDigest:");
    System.out.println(printHexBinary(cipherDigest));

    return DatatypeConverter.printBase64Binary(cipherDigest);
  }

  /** auxiliary method to calculate new digest from text and compare it to the
  to deciphered digest */
  public boolean verifyMAC(byte[] cipherDigest,
  String text,
  SecretKey key) throws Exception {

    Mac cipher = Mac.getInstance("HmacMD5");
    cipher.init(key);
    byte[] cipheredBytes = cipher.doFinal(text.getBytes());
    return Arrays.equals(cipherDigest, cipheredBytes);
  }

}
