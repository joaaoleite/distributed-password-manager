package pt.ulisboa.tecnico.sec;
import pt.ulisboa.tecnico.sec.lib.http.*;
import java.util.HashMap;



public class User
{

  private HMAC hmac;
  private SeqNumber seqNumber;
  private HashMap<String,String> database=  new HashMap<String,String>();



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
  public void put(String domain,  String username, String password) {
    database.put(domain+username,password);
  }
  public String get(String domain,  String username) {
    return database.get(domain+username);
  }

}
