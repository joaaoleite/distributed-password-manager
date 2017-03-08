package pt.ulisboa.tecnico.sec;
import static spark.Spark.*;


public class Server
{
  public static void main( String[] args )
  {

    port(8080);

    get("/test", (req, res) ->  {
      res.type("application/json");
      return "{\"message\":\"hello\"}";
    } );
    System.out.println( "Server address: " + args[0]);
  }
}
