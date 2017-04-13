package pt.ulisboa.tecnico.sec;
import java.util.HashMap;

import org.json.JSONObject;


public class Password{
  private String password;
  private String signature;
  private long timestamp;
  public  Password(){
    this.timestamp=0;
  }
  public void put(String password,String signature,long timestamp){
    this.password=password;
    this.signature=signature;
    if(timestamp>this.timestamp){
      this.timestamp=timestamp;
    }
  }
  public JSONObject get(){
    JSONObject resObj= new JSONObject();
    resObj.put("password",password);
    resObj.put("signature",signature);
    resObj.put("timestamp",timestamp);
    return resObj;

  }



}
