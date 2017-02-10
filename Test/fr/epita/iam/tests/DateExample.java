package fr.epita.iam.tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.epita.iam.services.UserMessageHandler;

public class DateExample {
  
  private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  
  private DateExample() {
    throw new IllegalAccessError("Test class");
  }
  
  public static void main(String[] args){
    //Formating a Date
    Date date = new Date();
    
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss.SSSS");
    
    String result = simpleDateFormat.format(date);
    
    UserMessageHandler.writeMessage(result);
    
    //Creating a Date from a String
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyy/MM/dd - HH:mm:ss.SSSS");
    String stringInput = "2014/05/17 - 15:00:45.2356";
    Date date2 = new Date();
    try{
      date2 = simpleDateFormat2.parse(stringInput);
    }
    catch(ParseException e)
    {
      LOGGER.log(Level.WARNING, "Error in testing the Date conversion!", e);
    }
    UserMessageHandler.writeMessage(date2.toString());
  }

}
