package fr.epita.iam.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.epita.iam.datamodel.User;
import fr.epita.iam.exceptions.DaoInitializationException;

/**
 * @author vanessavargas
 * This class manages the read and write to the users file.
 */
public class FileUserDAO {
  private File db; 
  private PrintWriter printWriter = null;
  private FileWriter fileWriter = null;
  Scanner scanner = null; 
  private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  
  public FileUserDAO(String fileName) {
    db = new File(fileName);
  }
  
  private void initWriter(){
    if(!db.exists()){
      //If the file NOT exists
      try {
        db.getParentFile().mkdirs();
        if(!db.createNewFile())
        {
          MyLogger.setup();
          LOGGER.log(Level.SEVERE, "FAILED: to create the DB file.");
        }
      } catch (IOException e) {
        DaoInitializationException die = new DaoInitializationException("Unable to create a new database file.");
        die.initCause(e);
        throw die;
      } 
    }
    

    try{
      fileWriter = new FileWriter(db, true);
      printWriter = new PrintWriter(fileWriter);
      } catch (IOException e) {
        DaoInitializationException die = new DaoInitializationException("Unable to initialize the writer for the database file.");
        die.initCause(e);
        throw die;
    } 
  }
  
  private void closeWriter(){
    printWriter.close();
    try {
      fileWriter.close();
    } catch (IOException e) {
      DaoInitializationException die = new DaoInitializationException("Unable to close file writer.");
      die.initCause(e);
      throw die;
    }
  }
  
  public void writeUser(User user){
    // Init the writer
    initWriter();
    //Write an identity in the file
    printWriter.println("<User>");
    printWriter.println("<Name>");
    printWriter.println(user.getUserName());
    printWriter.println("</Name>");
    printWriter.println("<Password>");
    printWriter.println(user.getUserPassword());
    printWriter.println("</Password>");
    printWriter.println("</User>");
    printWriter.flush();
    //Close the writer
    closeWriter();
  }
  
  public List<User> readAllUsers(){
    List<User> all = new ArrayList<>();
    try {
      scanner = new Scanner(db);
      while(scanner.hasNextLine()){
        scanner.nextLine();
        scanner.nextLine();
        String name = scanner.nextLine();
        scanner.nextLine();
        scanner.nextLine();
        String password  = scanner.nextLine();
        scanner.nextLine();
        scanner.nextLine();
        
        User user = new User(name, password);
        all.add(user);
        continue;
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      DaoInitializationException die = new DaoInitializationException("Unable to read from file.");
      die.initCause(e);
      throw die;
    }
    
    return all;
  }

}
