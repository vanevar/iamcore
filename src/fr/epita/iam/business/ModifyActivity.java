package fr.epita.iam.business;

import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.DateFormatManager;
import fr.epita.iam.services.IdentityJDBCDAO;
import fr.epita.iam.services.UserMessageHandler;

/**
 * @author vanessavargas
 * This class handles the Update of an Identity 
 */
public class ModifyActivity {
  
  private ModifyActivity() {
    throw new IllegalAccessError("Utility class");
  }
  
  /**
   * This function handles the update of an existing Identity by asking the user to
   * input the Identities values and then saving them in the DB.
   * @param scanner
   */
  public static void execute(Scanner scanner) {
    IdentityJDBCDAO idJDBC = new IdentityJDBCDAO();
    DateFormatManager dfm = new DateFormatManager();
    
    List<Identity> identities = idJDBC.readAllIdentities();
    if(identities.isEmpty())
    {
      UserMessageHandler.writeMessage("There are no identities on the Database. Aborting update Process!");
      return;
    }
    
    UserMessageHandler.writeMessage("Identity Update");
    for(Identity id : identities)
    {
      UserMessageHandler.writeMessage(id.toString());
    }
    
    UserMessageHandler.writeMessage("Please insert the UId of the identity to modify:");
    String uId = scanner.nextLine();
    
    Identity identity = idJDBC.readIdentity(uId);
    
    if(identity.getUid() == null){
      UserMessageHandler.writeMessage("Wrong UId selected. Aborting process");
      return;
    }
    
    UserMessageHandler.writeMessage("Identity to Modify: ");
    UserMessageHandler.writeMessage(identity.toString());
    
    //Update Fields:
    UserMessageHandler.writeMessage("Please insert the Display Name:");
    String displayName = scanner.nextLine();
    UserMessageHandler.writeMessage("Please input the e-mail address: ");
    String emailAddress = scanner.nextLine();
    UserMessageHandler.writeMessage("Please input the birthdate: ");
    String birthdate = scanner.nextLine();
    
    //Assign new values
    identity.setDisplayName(displayName);
    identity.setEmail(emailAddress);
    identity.setBirthdate(dfm.dateFromString(birthdate));
    
    //Persists to DB
    idJDBC.updateIdentity(identity);
    
    UserMessageHandler.writeMessage("Update Successful");
    return;
  }
  
}
