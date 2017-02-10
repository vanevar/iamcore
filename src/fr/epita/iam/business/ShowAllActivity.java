/**
 * 
 */
package fr.epita.iam.business;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.IdentityJDBCDAO;
import fr.epita.iam.services.UserMessageHandler;

/**
 * @author vanessavargas
 * This class shows all the Identities that are in the DB.
 */
public class ShowAllActivity {
  private ShowAllActivity() {
    throw new IllegalAccessError("Business class");
  }
  
  public static void execute() {
    IdentityJDBCDAO idJDBC = new IdentityJDBCDAO();
      
    UserMessageHandler.writeMessage("Available identities");
    for(Identity i : idJDBC.readAllIdentities()){
      UserMessageHandler.writeMessage(i.toString());
    }
    return;
  }

}
