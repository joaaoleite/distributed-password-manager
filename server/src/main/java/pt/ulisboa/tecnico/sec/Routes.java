package pt.ulisboa.tecnico.sec;
import static spark.Spark.*;
import org.json.JSONObject;


public class Routes
{
  public Routes(){
    Api api = new Api();


    post("/register", (request, response) -> {
      //falta o confirm
      //curl -X POST -d {"publicKey":"chave publica"} http://localhost:8080/register
      System.out.println(request.body().toString());

      JSONObject reqObj = new JSONObject(request.body().toString());

      JSONObject resObj= new JSONObject();
      System.out.println("HTTP POST /register");
      if (reqObj.get("publicKey")!=null){
        String pubKey =reqObj.get("publicKey").toString();
        api.register(pubKey); //call api function

        resObj.put("status","success");
        return resObj;
      }
      if(reqObj.get("confirm")!=null){

        resObj.put("status","confirm");
      }
      return resObj.put("status","success");
    });
    put("/put", (request, response) -> {
      //curl -X PUT -d "{'publicKey':'chave publica','domain':'facebook.com','username':'bytes','password':'1237493'}" http://localhost:8080/put
      JSONObject reqObj = new JSONObject(request.body().toString());
      System.out.println(reqObj.names());
      System.out.println("HTTP PUT /put");
      String pubKey =reqObj.get("publicKey").toString();;
      String domain =reqObj.get("domain").toString();;
      String username =reqObj.get("username").toString();;
      String password =reqObj.get("password").toString();;

      api.put(pubKey, domain,  username, password); //call api function

      JSONObject resObj= new JSONObject();
      resObj.put("status","success");
      return resObj;
    });
    post("/get", (request, response) -> {
      //curl -X POST -d "{'publicKey':'chave publica','domain':'facebook.com','username':'bytes'}" http://localhost:8080/get

      JSONObject reqObj = new JSONObject(request.body().toString());
      System.out.println("HTTP POST /get");
      String pubKey =reqObj.get("publicKey").toString();
      String domain =reqObj.get("domain").toString();
      String username =reqObj.get("username").toString();

      api.get(pubKey, domain,  username); //call api function

      JSONObject resObj= new JSONObject();
      resObj.put("status","success");
      return resObj;
    });

  }
}
