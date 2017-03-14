package pt.ulisboa.tecnico.sec;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.ArrayList;
import java.security.SecureRandom;
import java.util.Base64;
import pt.ulisboa.tecnico.sec.lib.http.*;
import pt.ulisboa.tecnico.sec.lib.crypto.*;
import pt.ulisboa.tecnico.sec.lib.exceptions.*;



public class SecurityHandler
{
  private Api api = new Api();
  private HashMap<String,DigitalSignature> users = new HashMap<String,DigitalSignature>();
  private HashMap<String,DigitalSignature> unconfirm = new HashMap<String,DigitalSignature>();
  private ArrayList<String> nounces=new ArrayList<String>();

  public HttpResponse register(JSONObject reqObj) throws Exception {
    String nounce =reqObj.get("nounce").toString();
    String pubKey =reqObj.get("publicKey").toString();
    JSONObject resObj= new JSONObject();

    if(!nounces.contains(nounce)){

      DigitalSignature mac =new DigitalSignature();
      String id= generateRandom();

      unconfirm.put(id,mac);
      nounces.add(nounce);
      Nounces nounceCLASS = new Nounces(pubKey);
      String  nounceJSON= nounceCLASS.generate();
      String key = mac.getKey();
      RSA rsa =new RSA(pubKey);
      String keyEncryt= rsa.encrypt(key);
      resObj.put("nounce",nounceJSON);
      resObj.put("key",keyEncryt);
      resObj.put("id",id);
      String token=mac.sign(resObj);
      HttpResponse result= new HttpResponse(token,resObj);
      return result;
    }
    resObj.put("nounce","number");
    resObj.put("status","error");
    HttpResponse result= new HttpResponse("",resObj);

    return result;
  }

  public HttpResponse confirm(String token,JSONObject reqObj) throws Exception {
    String nounce =reqObj.get("nounce").toString();
    String pubKey =reqObj.get("publicKey").toString();
    String id =reqObj.get("id").toString();
    JSONObject resObj= new JSONObject();
    Nounces nounceCLASS = new Nounces(pubKey);
    System.out.println("nounce");
    String  nounceJSON= nounceCLASS.generate();

    if(!nounces.contains(nounce)){
      DigitalSignature mac = unconfirm.get(id);
      if(mac!=null){

        if(mac.verify(token,reqObj)){
          users.put(pubKey,mac);
          mac= users.get(pubKey);
          unconfirm.remove(id);
          nounces.add(nounce);

          resObj.put("nounce",nounceJSON);
          resObj.put("status","ok");

          token=mac.sign(resObj);
          HttpResponse result= new HttpResponse(token,resObj);
          return result;
        }
      }
    }
    resObj.put("nounce",nounceJSON);
    resObj.put("status","error");
    HttpResponse result= new HttpResponse("",resObj);

    return result;
  }

  public HttpResponse put(String token,JSONObject reqObj) throws Exception{
    String nounce =reqObj.get("nounce").toString();
    String pubKey =reqObj.get("publicKey").toString();
    String domain =reqObj.get("domain").toString();
    String username =reqObj.get("username").toString();
    String password =reqObj.get("password").toString();

    JSONObject resObj= new JSONObject();
    Nounces nounceCLASS = new Nounces(pubKey);
    System.out.println("nounce");
    String  nounceJSON= nounceCLASS.generate();
    System.out.println(nounceJSON);

    if(users.get(pubKey)!=null){

      DigitalSignature mac = users.get(pubKey);
      if(!nounces.contains(nounce)){

        if(mac!=null){

          if(mac.verify(token,reqObj)){
            nounces.add(nounce);
            String status=api.put(pubKey,domain,username,password);

            if(status.equals("OK")){
              resObj.put("nounce",nounceJSON);
              resObj.put("status","ok");
              token=mac.sign(resObj);
              HttpResponse result= new HttpResponse(token,resObj);
              return result;
            }
          }
        }
      }
    }
    resObj.put("nounce",nounceJSON);
    resObj.put("status","error");
    HttpResponse result= new HttpResponse("",resObj);
    return result;




  }
  public HttpResponse get(String token,JSONObject reqObj) throws Exception{
    String nounce =reqObj.get("nounce").toString();
    String pubKey =reqObj.get("publicKey").toString();
    String domain =reqObj.get("domain").toString();
    String username =reqObj.get("username").toString();
    JSONObject resObj= new JSONObject();
    Nounces nounceCLASS = new Nounces(pubKey);
    System.out.println("nounce");
    String  nounceJSON= nounceCLASS.generate();

    if(users.get(pubKey)!=null){
      DigitalSignature mac = users.get(pubKey);
      if(!nounces.contains(nounce)){
        if(mac!=null){
          if(mac.verify(token,reqObj)){
            nounces.add(nounce);

            String status=api.get(pubKey,domain,username);
            if(!status.equals("Error")){
              resObj.put("nounce",nounceJSON);
              resObj.put("status","ok");
              token=mac.sign(resObj);
              HttpResponse result= new HttpResponse(token,resObj);
              return result;
            }
          }
        }
      }

    }
    resObj.put("nounce",nounceJSON);
    resObj.put("status","error");
    HttpResponse result= new HttpResponse("",resObj);
    return result;
  }


  private String generateRandom(){
    SecureRandom ranGen = new SecureRandom();
    byte[] arr = new byte[16];
    ranGen.nextBytes(arr);
    return Base64.getEncoder().encodeToString(arr);
  }




}
