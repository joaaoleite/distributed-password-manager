package pt.ulisboa.tecnico.sec;
import static spark.Spark.*;
import org.json.JSONObject;


public class Routes
{
  public Routes(){
    System.out.println( "Routes Starting");
    JSONObject obj = new JSONObject();
    obj.put("message","hello");

    get("/test", (req, res) ->  {
      res.type("application/json");
      //chamar as outra funções
      return obj;
    } );

  }
}
