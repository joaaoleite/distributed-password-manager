package pt.ulisboa.tecnico.sec;

import java.util.Scanner;

public class Main{

  public static void main(String[] args) throws Exception{
    Scanner reader = new Scanner(System.in);
    System.out.print("Enter username: ");
    String username = reader.next();
    System.out.print("Enter password: ");
    String password = reader.next();

    Generator.generateStore(username, password);
  }
}
