package pt.ulisboa.tecnico.sec;
import org.json.JSONObject;

public class HttpResponse{
  private JSONObject json;
  private String token;
  public HttpResponse(String token, JSONObject json){
    this.json = json;
    this.token = token;
  }
  public JSONObject getJSON(){
    return json;
  }
  public String getToken(){
    return token;

  }

}
