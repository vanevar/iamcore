/**
 * 
 */
package fr.epita.iam.tests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.DateFormatManager;
import fr.epita.iam.services.IdentityJDBCDAO;
import fr.epita.iam.services.UserMessageHandler;

/**
 * @author vanessavargas
 * Use this class to create 3 identities on your new DB.
 */
public class CreateIdentities {
  
  private CreateIdentities() {
    throw new IllegalAccessError("Data creation class");
  }
  
  /**
   * @param args
   * @throws SQLException
   */
  public static void main(String[] args) throws SQLException {
    DateFormatManager dfm = new DateFormatManager();
    List<Identity> toCreate = new ArrayList<>();
    toCreate.add(new Identity("Vanessa Vargas", "", "vvg@gmail.com", dfm.dateFromString("1990-12-14")));
    toCreate.add(new Identity("Juan Perez", "", "jpn@gmail.com", dfm.dateFromString("1991-07-21")));
    toCreate.add(new Identity("Pedro Dominguez", "", "pdm@gmail.com", dfm.dateFromString("1981-10-31")));
   
    Boolean success = toCreate.size() == create(toCreate).size();
    if(success)
    {
      UserMessageHandler.writeMessage("SUCCESS!!");
    }
    
  }
  
  private static List<Identity> create(List<Identity> toCreate)
  {
    IdentityJDBCDAO idJDBC = new IdentityJDBCDAO();
    for(Identity id : toCreate){
      idJDBC.writeIdentity(id);
    }
    return idJDBC.readAllIdentities(); 
  }
}
