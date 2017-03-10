package pt.ulisboa.tecnico.sec;

public class KeyStore{

  public static void main(String[] args) throws Exception{
    Box box = Generator.generateKeyCertBox();
    Generator.write(box);
  }
}
