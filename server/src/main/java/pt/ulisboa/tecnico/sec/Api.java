package pt.ulisboa.tecnico.sec;
import java.util.HashMap;


public class Api
{
  //[publicKey,[domain,[username,password]]]
  private HashMap<String,HashMap<String,HashMap<String,String>>> passwords = new HashMap<String,HashMap<String,HashMap<String,String>>>();

  public String put(String publicKey, String domain,  String username, String password) {

      if(passwords.get(publicKey)!=null){
        if(passwords.get(publicKey).get(domain)!=null){
          System.out.println("ja existia o dominio");
          passwords.get(publicKey).get(domain).put(username,password);
          return "OK";

        }else{
          System.out.println("nao existia o dominio");
          passwords.get(publicKey).put(domain, new HashMap<String, String>());
          passwords.get(publicKey).get(domain).put(username, password);
          return "OK";
        }
      }else{
        //nao havia entrada no hash
        System.out.println("nao havia entrada no hash");
        passwords.put(publicKey, new HashMap<String, HashMap<String, String>>());
        passwords.get(publicKey).put(domain, new HashMap<String, String>());
        passwords.get(publicKey).get(domain).put(username, password);
        System.out.println(passwords.get(publicKey).get(domain).get(username));
        return "OK";
      }



  }
  public String get(String publicKey, String domain,  String username) {

      if(passwords.get(publicKey)!=null){
        if(passwords.get(publicKey).get(domain)!=null){
          //existe o dominio"
          if(passwords.get(publicKey).get(domain).get(username)!=null){
            //existe o user"
            System.out.println(passwords.get(publicKey).get(domain).get(username));
            return passwords.get(publicKey).get(domain).get(username);
          }else{
            //nao existe o username
            System.out.println("nao existe username");
            return "Error";
          }
        }else{
          //nao existia o dominio
          System.out.println("nao existe dominio");
          return "Error";

        }
      }else{
        System.out.println("key");
        return "Error";

      }



  }

}
