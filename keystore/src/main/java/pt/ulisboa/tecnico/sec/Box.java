package pt.ulisboa.tecnico.sec;

import java.security.*;
import java.security.cert.X509Certificate;
import sun.security.x509.*;

public class Box implements java.io.Serializable{
  private KeyPair _keyPair;
  private X509Certificate[] _certificate;

  public Box(KeyPair keys, X509Certificate[] cert){
    this._keyPair = keys;
    this._certificate = cert;
  }

  public KeyPair getKeyPair(){
    return this._keyPair;
  }

  public X509Certificate[] getCertificate(){
    return this._certificate;
  }
}
