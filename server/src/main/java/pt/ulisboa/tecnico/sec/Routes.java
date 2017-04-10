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

      //curl -X POST -s -D - -d '{"nounce":"12345","publicKey":"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlqs1p0CgN0AxoY2s/99yaohsDbQViDVXbujBbTPeS0rOYo0hBlHPKEvcdlY8ztuX8KFnQRt4HzHXhsSEBZ86DJTNt/MMoSM9FWM5tCRTG9YOH8LYjC4RU4dlvI8uqMNAWOTCzyx+b84TSZDNLoBWA0GgsefRpMFBMmNR2PmXe7OZQHroJd1toPfJ/rnmArKRhQbUUA36qIVgr1rB31kM4igl0Vuy5urxmqVcQ1fEQb4sYh3ssFgeayD4vBNW48RqWMWyC1+ZxLTMditGUsqzhh6keSoB+AZiDnMKl/lT6J9jWkUL5bjp1fDSGM4gml0l1HkCM+wXm+azNtC4+1OV+QIDAQAB"}' http://localhost:8080/register
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

      //curl -X POST -s -D - -d '{"nounce":"12345","publicKey":"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlqs1p0CgN0AxoY2s/99yaohsDbQViDVXbujBbTPeS0rOYo0hBlHPKEvcdlY8ztuX8KFnQRt4HzHXhsSEBZ86DJTNt/MMoSM9FWM5tCRTG9YOH8LYjC4RU4dlvI8uqMNAWOTCzyx+b84TSZDNLoBWA0GgsefRpMFBMmNR2PmXe7OZQHroJd1toPfJ/rnmArKRhQbUUA36qIVgr1rB31kM4igl0Vuy5urxmqVcQ1fEQb4sYh3ssFgeayD4vBNW48RqWMWyC1+ZxLTMditGUsqzhh6keSoB+AZiDnMKl/lT6J9jWkUL5bjp1fDSGM4gml0l1HkCM+wXm+azNtC4+1OV+QIDAQAB"}' http://localhost:8080/register
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
      //curl -X PUT -s -D - -d "{"nounce":"12346",'publicKey':'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlqs1p0CgN0AxoY2s/99yaohsDbQViDVXbujBbTPeS0rOYo0hBlHPKEvcdlY8ztuX8KFnQRt4HzHXhsSEBZ86DJTNt/MMoSM9FWM5tCRTG9YOH8LYjC4RU4dlvI8uqMNAWOTCzyx+b84TSZDNLoBWA0GgsefRpMFBMmNR2PmXe7OZQHroJd1toPfJ/rnmArKRhQbUUA36qIVgr1rB31kM4igl0Vuy5urxmqVcQ1fEQb4sYh3ssFgeayD4vBNW48RqWMWyC1+ZxLTMditGUsqzhh6keSoB+AZiDnMKl/lT6J9jWkUL5bjp1fDSGM4gml0l1HkCM+wXm+azNtC4+1OV+QIDAQAB','domain':'facebook.com','username':'bytes','password':'1237493'}" http://localhost:8080/put
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
      //curl -X POST -s -D - -d "{"nounce":"12347",'publicKey':'chave publica','domain':'facebook.com','username':'bytes'}" http://localhost:8080/get
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
