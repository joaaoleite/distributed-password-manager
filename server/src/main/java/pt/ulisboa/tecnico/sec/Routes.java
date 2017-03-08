package pt.ulisboa.tecnico.sec;
import static spark.Spark.*;
import org.json.JSONObject;


public class Routes
{
  public Routes(){
    System.out.println( "Routes Starting");
    JSONObject obj = new JSONObject();
    obj.put("message","hello");

    post("/test", (req, res) ->  {
      JSONObject test = new JSONObject(req.body().toString());

      System.out.println(test.get("cona"));
      return obj;
    } );
    post("/register", (request, response) -> {
        // Create something
        return obj;
    });
    put("/put", (request, response) -> {
        // Create something
        return obj;
    });
    post("/get", (request, response) -> {
        // Create something
        return obj;
    });

  }
}
