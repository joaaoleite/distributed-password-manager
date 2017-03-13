package pt.ulisboa.tecnico.sec;
import static spark.Spark.*;
import org.json.JSONObject;
import java.io.StringWriter;
import io.jsonwebtoken.*;

import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import io.jsonwebtoken.impl.TextCodec;
import javax.crypto.KeyGenerator;


public class Server
{


  public static void main( String[] args )
  {

    System.out.println( "Server address: http://localhost:" + args[0]);
    port(Integer.parseInt(args[0]));
    Routes routes = new Routes();


  



  }



}
