package fr.epita.iam.tests;

import java.sql.SQLException;
import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.DateFormatManager;
import fr.epita.iam.services.IdentityJDBCDAO;
import fr.epita.iam.services.UserMessageHandler;

public class TestJDBC {

  private TestJDBC() {
    throw new IllegalAccessError("Test class");
  }
  
  public static void main(String[] args) throws SQLException {
    UserMessageHandler.writeMessage("Read All identities: "+testReadAllIdentities());
    UserMessageHandler.writeMessage("Read identity:" + testReadIdentity());
    UserMessageHandler.writeMessage("Write Identity: " + testWriteIdentity());
    UserMessageHandler.writeMessage("Update Identity: " + testUpdateIdentity());    
  }

  private static Boolean testReadAllIdentities(){
    IdentityJDBCDAO idJDBC = new IdentityJDBCDAO();
    List<Identity> all = idJDBC.readAllIdentities();
    return !all.isEmpty();
  }
  
  private static Boolean testReadIdentity(){
    IdentityJDBCDAO idJDBC = new IdentityJDBCDAO();
    Identity id = idJDBC.readAllIdentities().get(0);
  
    Identity id2 = idJDBC.readIdentity(id.getUid());
    
    if( id.getDisplayName().equals(id2.getDisplayName()) &&
        id.getEmail().equals(id2.getEmail()) &&
        id.getBirthdate().equals(id2.getBirthdate()) )
    {
      return true;
    }
    return false;
  }
  
  private static Boolean testWriteIdentity(){
    DateFormatManager dfm = new DateFormatManager();
    Identity id = new Identity("Carlos Diez", "2", "cdm@gmailcom", dfm.dateFromString("1980-12-24"));
    IdentityJDBCDAO idJDBC = new IdentityJDBCDAO();
    idJDBC.writeIdentity(id);
    
    for(Identity i : idJDBC.readAllIdentities()){
      if( id.getDisplayName().equals(i.getDisplayName()) &&
          id.getEmail().equals(i.getEmail()) &&
          id.getBirthdate().equals(i.getBirthdate()) )
      {
        return true;
      }
    }
    
    return false;
    
  }
  
  private static Boolean testUpdateIdentity(){
    IdentityJDBCDAO idJDBC = new IdentityJDBCDAO();
    Identity id = idJDBC.readAllIdentities().get(0);
    id.setDisplayName("Test");
  
    idJDBC.updateIdentity(id);
    
    Identity up = idJDBC.readIdentity(id.getUid());
    if( id.getDisplayName().equals(up.getDisplayName()) )
      {
        return true;
      }
    return false;
  }
  
}
