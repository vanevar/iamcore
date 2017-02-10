package fr.epita.iam.business;

import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.DateFormatManager;
import fr.epita.iam.services.IdentityJDBCDAO;
import fr.epita.iam.services.UserMessageHandler;

/**
 * @author vanessavargas
 * This class manages the creation of a new Identity in the database
 */
public class CreateActivity {
  
  private CreateActivity() {
    throw new IllegalAccessError("Utility class");
  }
  
  /**
   * This function asks the user for input to create a new identity and then post it
   * to the DB.
   * @param scanner
   */
  public static void execute(Scanner scanner) {
    UserMessageHandler.writeMessage("Identity Creation");
    UserMessageHandler.writeMessage("Please insert the Display Name:");
    String displayName = scanner.nextLine();
    UserMessageHandler.writeMessage("Please input the e-mail address: ");
    String emailAddress = scanner.nextLine();
    UserMessageHandler.writeMessage("Please input the birthdate (yyyy-MM-dd): ");
    String birthdate = scanner.nextLine();
    
    DateFormatManager dfm = new DateFormatManager();
    
    Identity identity = new Identity (displayName, "", emailAddress, dfm.dateFromString(birthdate) );
    
    //Persists to DB
    IdentityJDBCDAO idJDBC = new IdentityJDBCDAO();
    idJDBC.writeIdentity(identity);
    
    UserMessageHandler.writeMessage("Creation Successful");
    UserMessageHandler.writeMessage("This it the identity you created:");
    UserMessageHandler.writeMessage("Display Name: " + identity.getDisplayName() + 
        " , the email address: "+ identity.getEmail());
    
    return;
  }

}
