package pt.ulisboa.tecnico.sec;

public class User
{
  private String publicKey;
  private String domain;
  private String username;
  private String password;
  public User(String publicKey){
    this.publicKey=publicKey;
  }

  public void set(String publicKey, String domain,  String username, String password) {
    this.publicKey=publicKey;
    this.domain=domain;
    this.username=username;
    this.password=password;
  }
  public String getKey() {
    return this.publicKey;
  }
  public String getDomain() {
    System.out.println("cona");
    return this.domain;
  }
  public String getUsername() {
    return this.username;
  }
  public String getPassword() {
    return this.password;
  }

}
