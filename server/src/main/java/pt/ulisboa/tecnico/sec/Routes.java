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
      System.ou.println(req.body().toString());
      return obj;
    } );
    post("/", (request, response) -> {
        // Create something
    });
  }
}
