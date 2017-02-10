/**
 * 
 */
package fr.epita.iam.services;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import fr.epita.iam.exceptions.DaoInitializationException;

/**
 * @author vanessavargas
 * Logger class
 */
public class MyLogger {

  private MyLogger() {
    throw new IllegalAccessError("Logger class");
  }
  
  /**
   * Initialization of the global logger.
   */
  public static void setup() {

    FileHandler fileTxt;
    SimpleFormatter formatterTxt;
    // get the global logger to configure it
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    logger.setUseParentHandlers(false);
    logger.setLevel(Level.INFO);
    try {
      fileTxt = new FileHandler("Logs/Logging.txt");
      // create a TXT formatter
      formatterTxt = new SimpleFormatter();
      fileTxt.setFormatter(formatterTxt);
      logger.addHandler(fileTxt);
    } catch (SecurityException | IOException e) {
      DaoInitializationException die = new DaoInitializationException("Unable to read from file.");
      die.initCause(e);
      throw die;
    }
  }
}
