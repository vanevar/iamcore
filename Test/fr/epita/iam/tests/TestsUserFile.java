package fr.epita.iam.tests;

import java.util.List;

import fr.epita.iam.datamodel.User;
import fr.epita.iam.services.FileUserDAO;
import fr.epita.iam.services.UserMessageHandler;

public class TestsUserFile {
  
  private TestsUserFile() {
    throw new IllegalAccessError("Test class");
  }
  
  public static void main(String[] args){
    
    
    FileUserDAO fileUserDAO = new FileUserDAO("Users/user-list.xml");
    
    User user1 = new User("managment", "mana");
    fileUserDAO.writeUser(user1);
    
    List<User> list = fileUserDAO.readAllUsers();
    for( User tempUser : list )
    {
      UserMessageHandler.writeMessage(tempUser.getUserName());
    }
      
  }
}
