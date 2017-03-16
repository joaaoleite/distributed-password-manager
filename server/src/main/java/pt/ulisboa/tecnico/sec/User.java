package pt.ulisboa.tecnico.sec;
import pt.ulisboa.tecnico.sec.lib.http.*;
import java.util.HashMap;



public class User
{

  private DigitalSignature signature;
  private SeqNumber seqNumber;
  private HashMap<String,String> database=  new HashMap<String,String>();



  public  User(DigitalSignature signature) {
    this.signature=signature;
    this.seqNumber=new SeqNumber();
  }
  public SeqNumber getSeqNumber(){
    return this.seqNumber;
  }

  public DigitalSignature getDigitalSignature(){
    return this.signature;
  }
  public void put(String domain,  String username, String password) {
    database.put(domain+username,password);
  }
  public String get(String domain,  String username) {
    return database.get(domain+username);
  }

}
