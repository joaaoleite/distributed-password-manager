package pt.ulisboa.tecnico.sec;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import pt.ulisboa.tecnico.sec.Client;
import pt.ulisboa.tecnico.sec.client.*;

public class ClientTest extends TestCase {

  private String address;
  private int port;
  private Session session;

  protected void setUp(){
    address = "localhost";
    port = 8080;
    session = Session.newInstance();
  }

  public void test(){

    session.login("username","password");

    Client client = new Client(session, address, port);

    client.register();

    String domain = "gmail.com";
    String username = "example@gmail.com";
    String password = "123456789";

    client.savePassword(domain,username,password);

    String passwordFromServer = client.retrievePassword(domain,username);

    assertEquals(password, passwordFromServer);
  }
}
