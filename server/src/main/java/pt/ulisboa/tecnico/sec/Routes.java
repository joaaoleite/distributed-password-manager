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
      System.out.println(req.body().toString());
      return obj;
    } );
    post("/register", (request, response) -> {
        // Create something
    });
    put("/put", (request, response) -> {
        // Create something
    });
    post("/get", (request, response) -> {
        // Create something
    });

  }
}
