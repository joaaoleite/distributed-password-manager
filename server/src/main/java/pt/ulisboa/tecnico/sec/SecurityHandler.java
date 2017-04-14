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


  private HashMap<String,User> users = new HashMap<String,User>();


  public HttpResponse register(String token, JSONObject reqObj) throws Exception {

    String clientPubKey =reqObj.get("publicKey").toString();
    JSONObject resObj= new JSONObject();
    DigitalSignature signature = new DigitalSignature();
    signature.setPublicKey(clientPubKey);
    if(signature.verify(token,reqObj)){
      User user=users.get(clientPubKey);
      if(user==null){
        user=new User();
        this.users.put(clientPubKey,user);
      }
        resObj=user.getSeqNumber().state();
        resObj.put("key",new RSA(clientPubKey).encrypt(user.getHMAC().getKey()));
        resObj.put("status","ok");
        token = user.getHMAC().sign(resObj);

      }else{
        resObj.put("status","403 Server failed to authenticate the request");
        token="";
      }

    return new HttpResponse(token,resObj);
  }


  public HttpResponse init(String token, JSONObject reqObj) throws Exception {

    String clientPubKey =reqObj.get("publicKey").toString();
    JSONObject resObj= new JSONObject();
    DigitalSignature signature = new DigitalSignature();
    signature.setPublicKey(clientPubKey);
    if(signature.verify(token,reqObj)){
      User user=users.get(clientPubKey);
      if(user!=null){
        resObj=user.getSeqNumber().state();
        resObj.put("key",new RSA(clientPubKey).encrypt(user.getHMAC().getKey()));
        resObj.put("status","ok");
        token = user.getHMAC().sign(resObj);

      }
      else{
        token="";
        resObj.put("status","User Does not exist");
      }
      }else{
        token="";
        resObj.put("status","Server failed to authenticate the request");

      }



    return new HttpResponse(token,resObj);
  }

  public  HttpResponse put(String token, JSONObject reqObj) throws Exception {
    String clientPubKey =reqObj.get("publicKey").toString();
    String domain =reqObj.get("domain").toString();
    String username =reqObj.get("username").toString();
    String password =reqObj.get("password").toString();
    String signaturePass =reqObj.get("signature").toString();
    long timestamp =reqObj.getLong("timestamp");
    JSONObject resObj= new JSONObject();
    DigitalSignature signature = new DigitalSignature();
    signature.setPublicKey(clientPubKey);
    if(signature.verify(token,reqObj)){
      User user=users.get(clientPubKey);
      if(user!=null){
        if(user.getSeqNumber().verify(reqObj)){
          user.put(domain,username,password, signaturePass, timestamp);
          resObj=user.getSeqNumber().request(resObj);
          resObj.put("status","ok");
        }else{
          resObj.put("status","Invalid sequencial number");
        }
      }else{
        resObj.put("status","User does not exist");
      }
    }else{
      resObj.put("status","Failed to verify signature");
    }
    token = "";

    return new HttpResponse(token,resObj);
  }

  public  HttpResponse get(String token, JSONObject reqObj) throws Exception {
    String clientPubKey =reqObj.get("publicKey").toString();
    String domain =reqObj.get("domain").toString();
    String username =reqObj.get("username").toString();
    JSONObject resObj= new JSONObject();
    DigitalSignature signature = new DigitalSignature();
    signature.setPublicKey(clientPubKey);
    if(signature.verify(token,reqObj)){
      User user=users.get(clientPubKey);
      if(user!=null){
        if(user.getSeqNumber().verify(reqObj)){
          JSONObject pass=user.get(domain,username);
          if(pass!=null){
            resObj=user.getSeqNumber().request(resObj);
            resObj.put("status","ok");
            resObj.put("password",pass.get("password"));
            resObj.put("signature",pass.get("signature"));
            resObj.put("timestamp",pass.get("timestamp"));
            token = user.getHMAC().sign(resObj);
          }else{
            resObj.put("status","Domain or user does not exist");
            token="";
          }
        }else{
          resObj.put("status","Invalid sequencial number");
          token="";
        }
      }else{
        resObj.put("status","User does not exist");
        token="";
      }
    }else{
      resObj.put("status","Failed to verify signature");
      token="";
    }

    return new HttpResponse(token,resObj);
  }

}
