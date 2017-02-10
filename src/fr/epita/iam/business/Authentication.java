/**
 * 
 */
package fr.epita.iam.business;

import java.util.List;

import fr.epita.iam.datamodel.User;
import fr.epita.iam.services.FileUserDAO;

/**
 * @author vanessavargas
 * This class is in charge of perform the authentication of a user
 */
public class Authentication {
  
  private Authentication() {
    throw new IllegalAccessError("Services class");
  }

  
  /**
   * Check file to authenticate the User.
   * @param file that contains the users
   * @param name given by the user to the app
   * @param pass given by the user to the app
   * @return
   */
  public static Boolean authenticate(String file, String name, String pass){
    FileUserDAO fileUserDAO = new FileUserDAO(file);
    
    List<User> allUsers = fileUserDAO.readAllUsers();
    
    for(User u : allUsers)
    {
      if(u.getUserName().equals(name) && u.getUserPassword().equals(pass))
        return true;
    }
    return false;
  }
}
