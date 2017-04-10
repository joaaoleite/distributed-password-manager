package pt.ulisboa.tecnico.sec;
import static spark.Spark.*;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;


public class Routes
{
  public Routes(){
    SecurityHandler security = new SecurityHandler();
    ArrayList<String> log = new ArrayList<String>();

    before((request, response) -> {
      String logString="----------Log------------\n"+"Session id: "+request.session().id()+"\nClient ip: "+request.ip()+"\nProtocol: " +request.protocol()+"\nMethod: /"+request.requestMethod()+"\nUrl: "+request.url()+"\nRequest UserAgent: "+request.userAgent()+"\nArguments:\n";
      JSONObject reqObj = new JSONObject(request.body().toString());
      Iterator<?> keys = reqObj.keys();


      while( keys.hasNext() ) {
        String key = (String)keys.next();
        logString+="  -"+key+":"+reqObj.get(key)+"\n";

      }
      logString+="------------------------";
      System.out.println(logString);
      log.add(logString);
    });

    post("/init", (request, response) -> {

      try {
        response.type("application/json");
        JSONObject reqObj = new JSONObject(request.body().toString());
        String token = request.headers("Authorization").split("Bearer ")[1];
        HttpResponse resp =security.init(token,reqObj); //call security function
        response.header("Authorization", "Bearer "+resp.getToken());
        return resp.getJSON();
      }
      catch(Exception e){
        System.out.println(e);
        JSONObject resObj= new JSONObject();
        resObj.put("status","500 Internal Server Error");
        return resObj;
      }

    });


    post("/register", (request, response) -> {

      try {
        response.type("application/json");
        JSONObject reqObj = new JSONObject(request.body().toString());
        String token = request.headers("Authorization").split("Bearer ")[1];
        HttpResponse resp =security.register(token,reqObj); //call security function
        response.header("Authorization", "Bearer "+resp.getToken());
        return resp.getJSON();
      }
      catch(Exception e){
        System.out.println(e);
        JSONObject resObj= new JSONObject();
        resObj.put("status","500 Internal Server Error");
        return resObj;
      }

    });

    post("/put", (request, response) -> {

      try {
        response.type("application/json");
        JSONObject reqObj = new JSONObject(request.body().toString());
        String token = request.headers("Authorization").split("Bearer ")[1];
        HttpResponse resp =security.put(token,reqObj); //call security function
        response.header("Authorization", "Bearer "+resp.getToken());
        return resp.getJSON().toString();
      }
      catch(Exception e){
        System.out.println(e);
        JSONObject resObj= new JSONObject();
        resObj.put("status","500 Internal Server Error");
        return resObj;
      }
    });
    post("/get", (request, response) -> {

      try {
        response.type("application/json");
        JSONObject reqObj = new JSONObject(request.body().toString());
        String token = request.headers("Authorization").split("Bearer ")[1];
        HttpResponse resp=security.get(token,reqObj); //call security function
        response.header("Authorization", "Bearer "+resp.getToken());
        return resp.getJSON().toString();
      }
      catch(Exception e){
        JSONObject resObj= new JSONObject();
        resObj.put("status","500 Internal Server Error");
        return resObj;
      }
    });
  }
}
