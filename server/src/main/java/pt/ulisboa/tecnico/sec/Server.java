package pt.ulisboa.tecnico.sec;
import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class Server
{
    public static void main( String[] args )
    {
        get("/hello", (req, res) -> "hello");
        System.out.println( "Server: " + args[0]);
    }
}
