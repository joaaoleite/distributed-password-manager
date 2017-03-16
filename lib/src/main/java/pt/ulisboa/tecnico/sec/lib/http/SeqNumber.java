package pt.ulisboa.tecnico.sec.lib.http;

import org.json.JSONObject;

public class SeqNumber {
  private long seq;
  private int isServer;

  public SeqNumber(JSONObject json){
    this.isServer = 0;
    this.seq = json.getLong("seq");
  }
  public SeqNumber(){
    this.isServer = 1;
    this.seq = 0;
  }

  public JSONObject request(JSONObject obj){
    return obj.put("seq", ++this.seq);
  }
  public boolean verify(JSONObject obj){
    return (obj.getLong("seq") - isServer) == this.seq;
  }
  public JSONObject state(){
    JSONObject json = new JSONObject();
    json.put("seq",this.seq);
    return json;
  }
}
