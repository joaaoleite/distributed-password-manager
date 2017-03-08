package pt.ulisboa.tecnico.sec;
import static spark.Spark.*;
import org.json.JSONObject;
import java.io.StringWriter;


public class Server
{
  public static void main( String[] args )
  {
    JSONObject obj = new JSONObject();
    obj.put("message","hello");
    port(8080);

    get("/test", (req, res) ->  {
      res.type("application/json");
      return obj;
    } );
    System.out.println( "Server address: " + args[0]);
  }
}
