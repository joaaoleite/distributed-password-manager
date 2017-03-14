package pt.ulisboa.tecnico.sec;

import pt.ulisboa.tecnico.sec.lib.crypto.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Base64;
import java.math.BigInteger;
import java.security.KeyPair;
import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyStore;
import java.security.SecureRandom;
import sun.security.x509.X509CertInfo;
import java.security.cert.X509Certificate;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateValidity;
import sun.security.x509.X500Name;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.X509CertImpl;

public class Generator {

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

    KeyPair pair = RSA.generateKeys();
    PrivateKey privateKey = pair.getPrivate();
    X509Certificate[] cert = generateCertificate(pair);
    SecretKey key = AES.generateKey();

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
