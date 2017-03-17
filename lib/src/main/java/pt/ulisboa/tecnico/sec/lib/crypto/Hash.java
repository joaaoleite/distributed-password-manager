package pt.ulisboa.tecnico.sec.lib.crypto;

import java.security.MessageDigest;
import java.util.Base64;

public class Hash{

  public static String digest(String text) throws Exception{
    byte[] textInBytes = text.getBytes("UTF-8");
    MessageDigest md = MessageDigest.getInstance("SHA-512");
    md.update(textInBytes);
    byte[] digest = md.digest();
    return Base64.getEncoder().encodeToString(digest);
  }
}
