package pt.ulisboa.tecnico.sec;

public class User
{
  String publicKey;
  String domain;
  String username;
  String password;
  public User(String publickey){
    this.publicKey=publicKey;
  }

  public static void set(String publicKey, String domain,  String username, String password) {
    this.publicKey=publicKey;
    this.domain=domain;
    this.username=username;
    this.password=password;
  }
  public String getKey(String publicKey) {
    return publicKey;
  }
  public String getDomain(String domain) {
    return domain;
  }
  public String getUsername(String username) {
    return username;
  }
  public String getPassword(String password) {
    return password;
  }



}
