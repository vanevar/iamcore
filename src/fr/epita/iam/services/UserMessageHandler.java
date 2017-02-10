/**
 * 
 */
package fr.epita.iam.services;

/**
 * @author vanessavargas
 * Helper method to print in the command and isolate the Sonar error
 */
public class UserMessageHandler {

  private UserMessageHandler() {
    throw new IllegalAccessError("Service class");
  }
  
  public static void writeMessage(String message){
    System.out.println(message);
  }
}
