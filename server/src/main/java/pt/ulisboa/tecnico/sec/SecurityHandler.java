package pt.ulisboa.tecnico.sec;
import pt.ulisboa.tecnico.sec.security.*;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.ArrayList;


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

      unconfirm.put("id",mac);
      nounces.add(nounce);
      resObj.put("nounce","success");
      resObj.put("key","success");
      resObj.put("id","lmdmds");
      String token=mac.assign(resObj);
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

    if(!nounces.contains(nounce)){
      DigitalSignature mac = unconfirm.get(id);
      if(mac!=null){

        if(mac.verify(token,reqObj)){
          users.put(pubKey,mac);
          mac= users.get(pubKey);
          unconfirm.remove(id);
          nounces.add(nounce);
          resObj.put("nounce","create nounce");
          resObj.put("status","ok");

          token=mac.assign(resObj);
          HttpResponse result= new HttpResponse(token,resObj);
          return result;
        }
      }
    }
    resObj.put("nounce","number");
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
    if(users.get(pubKey)!=null){

      DigitalSignature mac = users.get(pubKey);
      if(!nounces.contains(nounce)){

        if(mac!=null){

          if(mac.verify(token,reqObj)){
            nounces.add(nounce);
            String status=api.put(pubKey,domain,username,password);

            if(status.equals("OK")){
              resObj.put("nounce","create nounce");
              resObj.put("status","ok");
              token=mac.assign(resObj);
              HttpResponse result= new HttpResponse(token,resObj);
              return result;
            }
          }
        }
      }
    }
    resObj.put("nounce","number");
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
    if(users.get(pubKey)!=null){
      DigitalSignature mac = users.get(pubKey);
      if(!nounces.contains(nounce)){
        if(mac!=null){
          if(mac.verify(token,reqObj)){
            nounces.add(nounce);

            String status=api.get(pubKey,domain,username);
            if(!status.equals("Error")){
              resObj.put("nounce","create nounce");
              resObj.put("status","ok");
              token=mac.assign(resObj);
              HttpResponse result= new HttpResponse(token,resObj);
              return result;
            }
          }
        }
      }

    }
    resObj.put("nounce","number");
    resObj.put("status","error");
    HttpResponse result= new HttpResponse("",resObj);
    return result;
  }




}
