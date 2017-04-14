package pt.ulisboa.tecnico.sec;
import pt.ulisboa.tecnico.sec.lib.http.*;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;



public class User
{

  private HMAC hmac;
  private SeqNumber seqNumber;
  private ConcurrentHashMap<String,Password> database=  new ConcurrentHashMap<String,Password>();



  public  User() throws Exception {
    this.hmac=new HMAC();
    this.seqNumber=new SeqNumber();
  }
  public SeqNumber getSeqNumber(){
    return this.seqNumber;
  }

  public HMAC getHMAC(){
    return this.hmac;
  }
  public void put(String domain,  String username, String password,String signature,long timestamp) {
    if(database.containsKey(domain+username)){
      database.get(domain+username).put( password, signature, timestamp);

    }else{
      Password pass=new Password();
      pass.put( password, signature, timestamp);
      database.put(domain+username,pass);
    }

  }
  public JSONObject get(String domain,  String username) throws Exception {
    return database.get(domain+username).get();
  }

}
