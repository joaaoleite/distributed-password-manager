package pt.ulisboa.tecnico.sec.lib.http;

import org.json.JSONObject;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.security.SecureRandom;
import java.security.PublicKey;
import java.math.BigInteger;
import org.json.JSONObject;

import pt.ulisboa.tecnico.sec.lib.crypto.*;

public class Nounces {

  private static final int MINUTES = 10;
  private HashMap<String,Date> nounces;
  private RSA rsa;
  private String last;

  public Nounces(RSA rsa){
    this.rsa = rsa;
    this.init();
  }
  public Nounces(String publicKey) throws Exception{
    this.rsa = new RSA(publicKey);
    this.init();
  }
  public Nounces(PublicKey publicKey) throws Exception{
    this.rsa = new RSA(publicKey);
    this.init();
  }

  private void init(){

    this.nounces = new HashMap<String,Date>();
    this.last = "";

    new Thread(){ public void run(){
      nounces.forEach((String nounce, Date date)->{
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, - MINUTES);
        if(date.before(cal.getTime()))
          nounces.remove(nounce);
      });
    }}.start();
  }

  public JSONObject generate() throws Exception{
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    String nounce = new BigInteger(130, random).toString(32);
    nounces.put(nounce, new Date());
    JSONObject json = new JSONObject();
    json.put("nounce", rsa.encrypt(nounce));
    return json;
  }

  public JSONObject request(JSONObject nounceReq, JSONObject json) throws Exception{
    String encrypted = nounceReq.getString("nounce");
    String nounce = rsa.decrypt(encrypted);
    nounces.put(nounce, new Date());
    json.put("nounce", nounce);
    return json;
  }

  public JSONObject response(JSONObject json){
    String nounce = last;
    this.last = "";
    json.put("nounce",nounce);
    return json;
  }

  public boolean verify(JSONObject obj){
    String nounce = obj.getString("nounce");
    if(nounces.remove(nounce) != null){
      last = nounce;
      return true;
    }
    else return false;
  }
}
