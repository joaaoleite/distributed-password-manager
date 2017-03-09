package pt.ulisboa.tecnico.sec;
import static spark.Spark.*;
import org.json.JSONObject;


public class Routes
{
  public Routes(){


    post("/register", (request, response) -> {
      JSONObject reqObj = new JSONObject(request.body().toString());
      System.out.println("HTTP POST /register");
      String pubKey =reqObj.get("publicKey").toString();

      JSONObject resObj= new JSONObject();
      resObj.put("status","success");
      return resObj;
    });
    put("/put", (request, response) -> {
      JSONObject reqObj = new JSONObject(request.body().toString());
      System.out.println("HTTP PUT /put");
      String pubKey =reqObj.get("publicKey").toString();;
      String domain =reqObj.get("domain").toString();;
      String username =reqObj.get("username").toString();;
      String password =reqObj.get("password").toString();;

      JSONObject resObj= new JSONObject();
      resObj.put("status","success");
      return resObj;
    });
    post("/get", (request, response) -> {
      JSONObject reqObj = new JSONObject(request.body().toString());
      System.out.println("HTTP POST /get");
      String pubKey =reqObj.get("publicKey").toString();;
      String domain =reqObj.get("domain").toString();;
      String username =reqObj.get("username").toString();;



      JSONObject resObj= new JSONObject();
      resObj.put("status","success");
      return resObj;
    });

  }
}
