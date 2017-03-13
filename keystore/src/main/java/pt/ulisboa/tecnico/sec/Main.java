package pt.ulisboa.tecnico.sec;

import java.io.File;
import java.util.Scanner;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class Main{

  public static void main(String[] args) throws Exception{
    Scanner reader = new Scanner(System.in);
    System.out.print("Enter username: ");
    String username = reader.next();
    System.out.print("Enter password: ");
    char[] password = reader.next().toCharArray();

    File dir = new File("data");
    dir.mkdirs();

    KeyPair pair = Generator.generateKeyPair();
    PrivateKey privateKey = pair.getPrivate();
    X509Certificate[] cert = Generator.generateCertificate(pair);

    Generator.saveStore(username, password, privateKey, cert);
  }
}
