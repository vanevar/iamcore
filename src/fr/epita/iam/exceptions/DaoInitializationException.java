package fr.epita.iam.exceptions;

/**
 * @author vanessavargas
 * This class was created to handle the Exceptions in a customized way. 
 * With the objective of having only one type of exception to handle.
 */
public class DaoInitializationException extends RuntimeException{

    /**
   * Added auto generated
   */
  private static final long serialVersionUID = 2974147217574149198L;

    public DaoInitializationException()
    {
      //Exists to complete implementation
    }
    
    /**
     * To set custom exception message
     */
    public DaoInitializationException(String message)
    {
      super(message);
    }
}
/*
 Focus on layer separation!!
 Also abstraction.
 #5 not to be considered.
 We can mail him with doubts. Do not hesitate to insist if no reply. 
  
 
//Give quality to what we need to complete.
//Code is going to go thru a tool: sonarqube, that checks code quality. So, make sure you have comments and everything.
//So part of the mark comes from the stats of the tool.

// A way to check ur self in eclipe is called: sonarlint (client side of sonarcube)
 * Go on HElp -> Eclipse market pale -> type sonar on the search tab, and should be in the first 5 results.
 ->>>>> ***He will like no bug (!) and no vulnerability.
Use the sonarLint Issues View


we will submit a git repository to him. 
He will stract the code from the github @ 11:59 pm the 14th.
Project well documented so he can test. Instructions to use. 

50% is quality, good documentation, good instructions to use the project,
Realization 
Industrialization - Professional approach Configurable? How easy is it to use?
He will have to have Derby installed. So he only needs to start his DB and launch the app.

1. Provide the project itself on Git. (do this tries with proper time)
2. Provide installation instructions in the repository (like a README.txt)
3. Provide technical documentation #3 on the web page of the project http://thomas-broussard.fr/work/java/courses/project/fundamental.xhtml
(It is not necessary to cover all if you don;t find what to put) Put it also on the Git.

We can take exactly the project that there is on the Git and work from there. CAREFUL with QUALITY ISSUES. 
*
*

*/