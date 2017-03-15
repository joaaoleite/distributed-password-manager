package pt.ulisboa.tecnico.sec.lib.http;

import org.json.JSONObject;

public class SeqNumber {

  private long seq = 0;

  public JSONObject request(JSONObject obj){
    this.seq++;
    return obj.put("seq", this.seq);
  }

  public boolean verifyOnClient(JSONObject obj){
    return obj.getLong("seq") == this.seq;
  }

  public boolean verifyOnServer(JSONObject obj){
    return (obj.getLong("seq") - 1) == this.seq;
  }
}
