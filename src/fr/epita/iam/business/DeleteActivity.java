package fr.epita.iam.business;

import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.IdentityJDBCDAO;
import fr.epita.iam.services.UserMessageHandler;

/**
 * @author vanessavargas
 * This class handles an Identity deletition
 */
public class DeleteActivity {
  
  private DeleteActivity() {
    throw new IllegalAccessError("Utility class");
  }
  
  /**
   * This method Lists the available Identities, then allows the user to select one
   * and removes it from the DB.
   * @param scanner
   */
  public static void execute(Scanner scanner) {
    IdentityJDBCDAO idJDBC = new IdentityJDBCDAO();
    List<Identity> identities = idJDBC.readAllIdentities();
    if(identities.isEmpty())
    {
      UserMessageHandler.writeMessage("There are no identities on the Database. Aborting delete Process!");
      return;
    }
    UserMessageHandler.writeMessage("Identity Deletetion");
    UserMessageHandler.writeMessage("Available identities");
    for(Identity i : identities){
      UserMessageHandler.writeMessage(i.toString());
    }
    UserMessageHandler.writeMessage("Please insert the UId of the identity to remove:");
    String uId = scanner.nextLine();
    
    Identity identity = idJDBC.readIdentity(uId);
    
    if(identity.getUid() == null){
      UserMessageHandler.writeMessage("Wrong UId selected. Aborting process");
      return;
    }
    UserMessageHandler.writeMessage("Identity to Delete: ");
    UserMessageHandler.writeMessage(identity.toString());
    
    //Update Fields:
    UserMessageHandler.writeMessage("Are you sure you want to delete this identity? (Y/N)");
    String response = scanner.nextLine();
    if("Y".equalsIgnoreCase(response))
    {
      //Persists to DB
      idJDBC.deleteIdentity(identity);
    }
    
    UserMessageHandler.writeMessage("Process Completed");
    return;
  }
}
