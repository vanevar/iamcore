package fr.epita.iam.launcher;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.epita.iam.business.Authentication;
import fr.epita.iam.business.CreateActivity;
import fr.epita.iam.business.DeleteActivity;
import fr.epita.iam.business.ModifyActivity;
import fr.epita.iam.business.ShowAllActivity;
import fr.epita.iam.exceptions.DaoInitializationException;
import fr.epita.iam.services.MyLogger;
import fr.epita.iam.services.UserMessageHandler;

/**
 * @author vanessavargas
 * This class runs the whole program.
 */
public class ConsoleLauncher {

  private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  private static final String USERSFILE = "Users/user-list.xml";
  
  private ConsoleLauncher() {
    throw new IllegalAccessError("Launcher class");
  }
  
  public static void main(String[] args){
    UserMessageHandler.writeMessage("Welcome to the IAM software");
    Scanner scanner = new Scanner(System.in);
    
    if( !authenticate(scanner) )
    {
      end(scanner);
      return;
    }
    
    //Show the menu for ever
    do
    {
      //Menu
      UserMessageHandler.writeMessage("PLease selet and action:");    
      UserMessageHandler.writeMessage("1. Create an identity");    
      UserMessageHandler.writeMessage("2. Modify an identity");    
      UserMessageHandler.writeMessage("3. Delete an identity");    
      UserMessageHandler.writeMessage("4. Show all identities");
      UserMessageHandler.writeMessage("5. Quit");
      String choice = scanner.nextLine();
    
      switch(choice){
      case "1":
        //Create
        handleCreate(scanner);
        break;
    
      case "2":
        //Modify
        handleUpdate(scanner);
        break;
      
      case "3":
        //Delete
        handleDelete(scanner);
        break;
      
      case "4":
        //Show all
        handleShowAll();
        break;
        
      case "5":
        //Quit
        end(scanner);
        return;
      
      default:
        UserMessageHandler.writeMessage("Your choice is not recognized...");
        break;
      
      }
    }while (true);
  }

  /**
   * Say goodbye and close Scanner.
   * @param scanner
   */
  private static void end(Scanner scanner) {
    UserMessageHandler.writeMessage("Thanks for using this application, good bye!");
    scanner.close();
  }

  /**
   * Ask the user for authentication
   * @param scanner
   * @return
   */
  private static boolean authenticate(Scanner scanner) {
    UserMessageHandler.writeMessage("Please type your login: ");
    String login = scanner.nextLine();
    
    UserMessageHandler.writeMessage("Please type your password: ");
    String password = scanner.nextLine();
    
    if(Authentication.authenticate(USERSFILE, login, password))
    {
      UserMessageHandler.writeMessage("Authentication was successful.");
      return true;
    }
    else
    {
      UserMessageHandler.writeMessage("Authentication failed.");
      return false;
    }
  }


  private static void handleCreate(Scanner scanner)
  {
    try {
      CreateActivity.execute(scanner);
    } catch (DaoInitializationException e){
      UserMessageHandler.writeMessage(e.getMessage());
      MyLogger.setup();
      LOGGER.log(Level.INFO, "Error when creating new Identity.", e);
    }
  }
  
  private static void handleUpdate(Scanner scanner){
    try {
      ModifyActivity.execute(scanner);
    } catch(DaoInitializationException e){
      UserMessageHandler.writeMessage(e.getMessage());
      MyLogger.setup();
      LOGGER.log(Level.INFO, "Error when updating Identity.", e);
    }
  }

  private static void handleDelete(Scanner scanner){
    try {
      DeleteActivity.execute(scanner);
      } catch (DaoInitializationException e)
      {
        UserMessageHandler.writeMessage(e.getMessage());
        MyLogger.setup();
        LOGGER.log(Level.INFO, "Error when deleting Identity.", e);
      }
  }
  
  private static void handleShowAll(){
    try {
      ShowAllActivity.execute();
      } catch (DaoInitializationException e)
      {
        UserMessageHandler.writeMessage(e.getMessage());
        MyLogger.setup();
        LOGGER.log(Level.INFO, "Error when deleting Identity.", e);
      }
  }
}
