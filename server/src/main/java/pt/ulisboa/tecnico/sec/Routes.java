package pt.ulisboa.tecnico.sec;
import static spark.Spark.*;
import org.json.JSONObject;


public class Routes
{
  public Routes(){
    SecurityHandler security = new SecurityHandler();


    post("/register", (request, response) -> {

      //curl -X POST -s -D - -d '{"nounce":"12345","publicKey":"chave publica"}' http://localhost:8080/register
      try {
        response.type("application/json");
        System.out.println(request.body().toString());
        JSONObject reqObj = new JSONObject(request.body().toString());
        System.out.println("HTTP POST /register");
        HttpResponse resp =security.register(reqObj); //call security function
        if (resp.getToken().equals(""))
        return resp.getJSON();
        response.header("Authorization", "Bearer "+resp.getToken());
        return resp.getJSON().toString();
      }
      catch(Exception e){
        JSONObject resObj= new JSONObject();
        resObj.put("status","error");
        return resObj;
      }

    });
    post("/confirm", (request, response) -> {

      //curl -X POST -s -D - -d '{"nounce":"12345","publicKey":"chave publica"}' http://localhost:8080/register
      try {
        response.type("application/json");
        System.out.println(request.body().toString());
        JSONObject reqObj = new JSONObject(request.body().toString());
        System.out.println("HTTP POST /register");
        HttpResponse resp =security.register(reqObj); //call security function
        if (resp.getToken().equals(""))
        return resp.getJSON();
        response.header("Authorization", "Bearer "+resp.getToken());
        return resp.getJSON().toString();
      }
      catch(Exception e){
        JSONObject resObj= new JSONObject();
        resObj.put("status","error");
        return resObj;
      }

    });
    put("/put", (request, response) -> {
      //curl -X PUT -s -D - -d "{"nounce":"12346",'publicKey':'chave publica','domain':'facebook.com','username':'bytes','password':'1237493'}" http://localhost:8080/put
      try {
        response.type("application/json");
        JSONObject reqObj = new JSONObject(request.body().toString());
        System.out.println("HTTP PUT /put");
        HttpResponse resp =security.put("token",reqObj); //call security function
        if (resp.getToken().equals("")){
          return resp.getJSON();
        }
        response.header("Authorization", "Bearer "+resp.getToken());
        return resp.getJSON().toString();
      }
      catch(Exception e){
        JSONObject resObj= new JSONObject();
        resObj.put("status","error");
        return resObj;
      }
    });
    post("/get", (request, response) -> {
      //curl -X POST -s -D - -d "{"nounce":"12347",'publicKey':'chave publica','domain':'facebook.com','username':'bytes'}" http://localhost:8080/get
      try {
        response.type("application/json");
        JSONObject reqObj = new JSONObject(request.body().toString());
        System.out.println("HTTP POST /get");
        HttpResponse resp=security.get("token",reqObj); //call security function
        if (resp.getToken().equals("")){
          return resp.getJSON();
        }
        response.header("Authorization", "Bearer "+resp.getToken());
        return resp.getJSON().toString();
      }
      catch(Exception e){
        JSONObject resObj= new JSONObject();
        resObj.put("status","error");
        return resObj;
      }
    });
  }
}
