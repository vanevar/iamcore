package fr.epita.iam.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DaoInitializationException;

/**
 * @author vanessavargas
 * This class manages the Identity - DB connection and queries.
 */
public class IdentityJDBCDAO {
  private Connection conn;
  private final File configFile = new File("Configs/DBConfig.xml"); 
  private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  
  public IdentityJDBCDAO() {
    try{
      getConnection();
    }catch(SQLException e)
    {
      DaoInitializationException die = new DaoInitializationException("Unable to get a connetion to the Database.");
      die.initCause(e);
      throw die;
    }
  }

  /**
   * Make sure we have a working connection or create a new one.
   * @return Connection to DB
   * @throws SQLException
   */
  private Connection getConnection() throws SQLException {
    try{
      this.conn.getSchema();
    }catch(Exception e)
    {
      MyLogger.setup();
      LOGGER.log(Level.INFO, "Connection lost. Re-connecting.", e);
      List<String> configs = readFromFile();
      String connString = configs.get(0);
      this.conn =  DriverManager.getConnection(connString, configs.get(1), configs.get(2));
    }
    return this.conn;
  }
  
  /**
   * Helper method to get the DB configurations from the DB Configurations file.
   * @return a list of strings where the first is the location, user and password
   */
  private List<String> readFromFile(){
    List<String> configs = new ArrayList<>();
    try ( Scanner scanner = new Scanner(configFile)){
      while(scanner.hasNextLine()){
        scanner.nextLine();
        scanner.nextLine();
        configs.add(scanner.nextLine());
        scanner.nextLine();
        scanner.nextLine();
        configs.add(scanner.nextLine());
        scanner.nextLine();
        scanner.nextLine();
        configs.add(scanner.nextLine());
        break;
      }
    } catch (FileNotFoundException e) {
      DaoInitializationException die = new DaoInitializationException("Unable to read from file.");
      die.initCause(e);
      throw die;
    } 
    return configs;
  }
  
  /**
   * Release the connection resources
   */
  private void releaseResources() {
    try{
      this.conn.close();
    }catch(Exception e){
      DaoInitializationException die = new DaoInitializationException("Unable to close the connection to the database.");
      die.initCause(e);
      throw die;
    }
  }
  
  /**
   * Read all identities persisted on the DB
   * @return
   * @throws DaoInitializationException
   */
  public List<Identity> readAllIdentities() 
  {
    Connection connection;
    List<Identity> listId = new ArrayList<>();
    DateFormatManager dfm = new DateFormatManager();
    PreparedStatement statement = null;
    try {
     connection = getConnection();
    
     statement = connection.prepareStatement("SELECT * from Identities");
     ResultSet result = statement.executeQuery();
    
     while(result.next()){
       String uId = result.getString("Uid");
       String displayName = result.getString("DisplayName");
       String email = result.getString("Email");
       String birthdate = result.getString("Birthday");
       Identity id = new Identity(displayName, uId, email, dfm.dateFromString(birthdate));
       listId.add(id);
        }
      this.releaseResources();
    } catch (Exception e) {
      DaoInitializationException die = new DaoInitializationException("A problem was found during the read of all identities.");
      die.initCause(e);
      throw die;
    } finally {
      if (statement != null)
        try {
          statement.close();
        } catch (SQLException e) {
          MyLogger.setup();
          LOGGER.log(Level.SEVERE, "FAILED: readAllIdentities", e);
        }
    }
    return listId;
  }
  
  /**
   * Persist an identity into the DB
   * @param id
   * @throws DaoInitializationException
   */
  public void writeIdentity( Identity id ) 
  {
    PreparedStatement statement = null;
    if(alreadyExists(id)){
      throw new DaoInitializationException("This identity already exists.");
    }
    
    try{
      Connection connection = getConnection();
      DateFormatManager dfm = new DateFormatManager();
    
      String sql = "INSERT INTO Identities (DisplayName, Email, Birthday) VALUES ( ?, ?, ?)";
      statement = connection.prepareStatement(sql);
      statement.setString(1, id.getDisplayName());
      statement.setString(2, id.getEmail());
      statement.setString(3, dfm.stringFromDate(id.getBirthdate()));
  
      statement.execute();
      this.releaseResources();
    } catch (Exception e) {
      DaoInitializationException die = new DaoInitializationException("A problem was encountered when trying to save the identity on the database.");
      die.initCause(e);
      throw die;
    } finally {
      if (statement != null)
        try {
          statement.close();
        } catch (SQLException e) {
          MyLogger.setup();
          LOGGER.log(Level.SEVERE, "FAILED: writeIdentity Statement close.", e);
        }
    }
    
  }
  
  /**
   * Read one identity from the DB based on the uid for the identity
   * @param uid 
   * @throws SQLException
   */
  public Identity readIdentity(String uid) 
  {
    DateFormatManager dfm = new DateFormatManager();
    Identity id = new Identity();  
    PreparedStatement statement = null;
    
    try{
      Connection connection = getConnection();
    
      statement = connection.prepareStatement("SELECT * from Identities WHERE UId = ?");
      statement.setString(1, uid);
      ResultSet result = statement.executeQuery();
    
      while(result.next()){
        String uId = result.getString("Uid");
        String displayName = result.getString("DisplayName");
        String email = result.getString("Email");
        String birthdate = result.getString("Birthday");
        id = new Identity(displayName, uId, email, dfm.dateFromString(birthdate));
      }
      this.releaseResources();
    } catch (Exception e)
    {
      DaoInitializationException die = new DaoInitializationException("A problem was encountered when trying to read the identity to the database.");
      die.initCause(e);
      throw die;
    } finally {
      if (statement != null)
        try {
          statement.close();
        } catch (SQLException e) {
          MyLogger.setup();
          LOGGER.log(Level.SEVERE, "FAILED: readIdentity.", e);
        }
    }

    return id;
  }
  
  /**
   * Update the passed identity on the DB, the update is done based on the uid
   * @param id
   * @throws DaoInitializationException
   */
  public void updateIdentity( Identity id ) 
  {
    PreparedStatement statement = null;
    try{
      Connection connection = getConnection();
      DateFormatManager dfm = new DateFormatManager();
      String sql = "UPDATE Identities SET DisplayName = ?, Email = ?, Birthday = ? WHERE UId = ?";
      statement = connection.prepareStatement(sql);
      statement.setString(1, id.getDisplayName());
      statement.setString(2, id.getEmail());
      statement.setString(3, dfm.stringFromDate(id.getBirthdate()));
      statement.setString(4, id.getUid());
    
      statement.execute();
      this.releaseResources();
    } catch (Exception e) {
      DaoInitializationException die = new DaoInitializationException("A problem was encountered when trying to update the identity to the database.");
      die.initCause(e);
      throw die;
    } finally {
      if (statement != null)
        try {
          statement.close();
        } catch (SQLException e) {
          MyLogger.setup();
          LOGGER.log(Level.SEVERE, "FAILED: updateIdentity.", e);
        }
    }
  }
  
  /**
   * Delete the identity from the DB
   * @param id
   * @throws DaoInitializationException
   */
  public void deleteIdentity( Identity id ) 
  {
    PreparedStatement statement = null;
    try{
    Connection connection = getConnection();
      String sql = "DELETE FROM Identities WHERE UId = ?";
      statement = connection.prepareStatement(sql);
      statement.setString(1, id.getUid());
    
      statement.execute();
      this.releaseResources();
    }  catch (Exception e) {
      DaoInitializationException die = new DaoInitializationException("A problem was encountered when trying to remove the identity from the database");
      die.initCause(e);
      throw die;
    }finally {
      if (statement != null)
        try {
          statement.close();
        } catch (SQLException e) {
          MyLogger.setup();
          LOGGER.log(Level.SEVERE, "FAILED: deleteIdentity.", e);
        }
    }
  }

  private Boolean alreadyExists(Identity id){
    for(Identity id2 : readAllIdentities())
    {
      if (id.getDisplayName().equals(id2.getDisplayName()) &&
          id.getEmail().equals(id2.getEmail()) &&
          id.getBirthdate().equals(id2.getBirthdate()))
          return true;
    }
    return false;
  }
}
