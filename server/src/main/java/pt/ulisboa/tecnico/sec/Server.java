package pt.ulisboa.tecnico.sec;
import static spark.Spark.*;
import org.json.JSONObject;
import java.io.StringWriter;


public class Server
{
  public static void main( String[] args )
  {
    System.out.println( "Server address: http://localhost:" + args[0]);
    port(Integer.parseInt(args[0]));
    Routes routes = new Routes();

  }
}
