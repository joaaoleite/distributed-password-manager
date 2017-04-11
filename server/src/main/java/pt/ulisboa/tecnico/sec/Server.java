package pt.ulisboa.tecnico.sec;
import static spark.Spark.*;
import org.json.JSONObject;
import java.io.StringWriter;
import io.jsonwebtoken.*;

import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import io.jsonwebtoken.impl.TextCodec;
import javax.crypto.KeyGenerator;
import spark.Service;
import java.util.ArrayList;



public class Server
{


  public static void main( String[] args ){

    for (Integer i = 0; i < args.length; i++) {
      System.out.println( "Server address: http://localhost:" + args[i]);
      new Routes(Service.ignite().port(Integer.parseInt(args[i])));
    }

  }



}
