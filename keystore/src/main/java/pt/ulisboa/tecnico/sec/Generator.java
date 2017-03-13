package pt.ulisboa.tecnico.sec;

import sun.security.x509.X509CertInfo;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Date;
import sun.security.x509.*;
import java.io.File;
import java.io.FileWriter;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.io.FileOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;

public class Generator {

  private static SecretKey generateSecretKey() throws Exception{
    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    keyGen.init(128, random);
    SecretKey key = keyGen.generateKey();
    return key;
  }

  private static KeyPair generateKeyPair() throws Exception{
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    keyGen.initialize(2048, random);
    KeyPair pair = keyGen.generateKeyPair();
    return pair;
  }

  private static X509Certificate[] generateCertificate(KeyPair pair) throws Exception {
    X509CertInfo info = new X509CertInfo();
    Date from = new Date();
    Date to = new Date(from.getTime() + 365 * 86400000l); //1 year
    CertificateValidity interval = new CertificateValidity(from, to);
    X500Name owner = new X500Name("C=PT, ST=SEC");
    info.set(X509CertInfo.VALIDITY, interval);
    info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(new BigInteger(64, new SecureRandom())));
    info.set(X509CertInfo.SUBJECT, owner);
    info.set(X509CertInfo.ISSUER,owner);
    info.set(X509CertInfo.KEY, new CertificateX509Key(pair.getPublic()));
    info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
    AlgorithmId algo = new AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);
    info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algo));
    X509CertImpl cert = new X509CertImpl(info);
    cert.sign(pair.getPrivate(), "SHA1withRSA");
    algo = (AlgorithmId)cert.get(X509CertImpl.SIG_ALG);
    info.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, algo);
    cert = new X509CertImpl(info);
    cert.sign(pair.getPrivate(), "SHA1withRSA");
    return new X509Certificate[]{cert};
  }

  public static void generateStore(String username, String pass) throws Exception{
    File dir = new File("data");
    dir.mkdirs();
    char[] password = pass.toCharArray();

    KeyPair pair = Generator.generateKeyPair();
    PrivateKey privateKey = pair.getPrivate();
    X509Certificate[] cert = Generator.generateCertificate(pair);
    SecretKey key = generateSecretKey();

    KeyStore ks = KeyStore.getInstance("JCEKS");
    ks.load(null, password);
    KeyStore.PrivateKeyEntry privEntry = new KeyStore.PrivateKeyEntry(privateKey, cert);
    KeyStore.SecretKeyEntry secEntry = new KeyStore.SecretKeyEntry(key);
    KeyStore.PasswordProtection passwordEntry = new KeyStore.PasswordProtection(password);
    ks.setEntry("privateKey", privEntry, passwordEntry);
    ks.setEntry("secretKey", secEntry, passwordEntry);
    FileOutputStream fos = new FileOutputStream("data/" + username + ".jce");
    ks.store(fos, password);
    fos.close();
  }
}
