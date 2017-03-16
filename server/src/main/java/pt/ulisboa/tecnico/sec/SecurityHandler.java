package pt.ulisboa.tecnico.sec;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.ArrayList;
import java.security.SecureRandom;
import java.util.Base64;
import pt.ulisboa.tecnico.sec.lib.http.*;
import pt.ulisboa.tecnico.sec.lib.crypto.*;
import pt.ulisboa.tecnico.sec.lib.exceptions.*;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.io.FileInputStream;
import java.security.cert.X509Certificate;



public class SecurityHandler
{

  private PrivateKey privateKey;
  private PublicKey publicKey;
  private HashMap<String,User> users = new HashMap<String,User>();


  public  SecurityHandler()  {
      try{
			FileInputStream fis = new FileInputStream("../keystore/data/server.jce");
			KeyStore ksa = KeyStore.getInstance("JCEKS");

			ksa.load(fis, "server".toCharArray());
			fis.close();
			this.privateKey = (PrivateKey) ksa.getKey("privateKey", "server".toCharArray());
			X509Certificate certificate = (X509Certificate) ksa.getCertificate("privateKey");
			this.publicKey = certificate.getPublicKey();
    }catch(Exception e){

    }
  }


  public HttpResponse register(String token, JSONObject reqObj) throws Exception {

    String clientPubKey =reqObj.get("publicKey").toString();
    JSONObject resObj= new JSONObject();
    DigitalSignature signature = new DigitalSignature(this.privateKey,clientPubKey);
    if(signature.verify(token,resObj)){
      User user=users.get(clientPubKey);
      if(user==null){
        user=new User(signature);
        this.users.put(clientPubKey,user);
      }
        resObj=user.getSeqNumber().state();
        resObj.put("publicKey",this.publicKey);
        resObj.put("status","200 OK");

      }else{
        resObj.put("status","403 Server failed to authenticate the request");
      }
    token = signature.sign(resObj);


    return new HttpResponse(token,resObj);
  }


  public HttpResponse init(String token, JSONObject reqObj) throws Exception {

    String clientPubKey =reqObj.get("publicKey").toString();
    JSONObject resObj= new JSONObject();
    DigitalSignature signature = new DigitalSignature(this.privateKey,clientPubKey);
    if(signature.verify(token,resObj)){
      User user=users.get(clientPubKey);
      if(user!=null){
        resObj=user.getSeqNumber().state();
        resObj.put("publicKey",this.publicKey);
        resObj.put("status","OK");
      }
      else{
        resObj.put("status","User Does not exist");
      }


      }else{
        resObj.put("status","Server failed to authenticate the request");
      }
    token = signature.sign(resObj);


    return new HttpResponse(token,resObj);
  }

  public HttpResponse put(String token, JSONObject reqObj) throws Exception {
    String clientPubKey =reqObj.get("publicKey").toString();
    String domain =reqObj.get("domain").toString();
    String username =reqObj.get("username").toString();
    String password =reqObj.get("password").toString();
    JSONObject resObj= new JSONObject();
    DigitalSignature signature = new DigitalSignature(this.privateKey,clientPubKey);
    if(signature.verify(token,resObj)){
      User user=users.get(clientPubKey);
      if(user!=null){
        if(user.getSeqNumber().verify(reqObj)){
          user.put(domain,username,password);
          resObj=user.getSeqNumber().request(resObj);
          resObj.put("status","OK");
        }else{
          resObj.put("status","Invalid sequencial number");
        }
      }else{
        resObj.put("status","User does not exist");
      }
    }else{
      resObj.put("status","Failed to verify signature");
    }
    token = signature.sign(resObj);

    return new HttpResponse(token,resObj);
  }

  public HttpResponse get(String token, JSONObject reqObj) throws Exception {
    String clientPubKey =reqObj.get("publicKey").toString();
    String domain =reqObj.get("domain").toString();
    String username =reqObj.get("username").toString();
    JSONObject resObj= new JSONObject();
    DigitalSignature signature = new DigitalSignature(this.privateKey,clientPubKey);
    if(signature.verify(token,resObj)){
      User user=users.get(clientPubKey);
      if(user!=null){
        if(user.getSeqNumber().verify(reqObj)){
          String pass=user.get(domain,username);
          if(pass!=null){
            resObj=user.getSeqNumber().request(resObj);
            resObj.put("status","OK");
            resObj.put("password",pass);
          }else{
            resObj.put("status","Domain or user does not exist");
          }
        }else{
          resObj.put("status","Invalid sequencial number");
        }
      }else{
        resObj.put("status","User does not exist");
      }
    }else{
      resObj.put("status","Failed to verify signature");
    }
    token = signature.sign(resObj);


    return new HttpResponse(token,resObj);
  }






}
