/**
 * 
 */
package fr.epita.iam.datamodel;

/**
 * @author vanessavargas
 * This is the Datamodel class for a user entity.
 */
public class User {
//Properties
 private String userName;
 private String userPassword; 

 public User (){
   //Mandatory constructor
 }
 
 public User( String userName, String password){
   this.setUserName(userName);
   this.setUserPassword(password);
 }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
 
  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

}
