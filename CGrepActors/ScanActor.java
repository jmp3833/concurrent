import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import akka.actor.*;

class ScanActor extends UntypedActor {

  private Configure configure;
  public ScanActor(Configure configure) {
  
  }
  
  /*
   * Construct a Found object to feed back to the 
   * collection actor. The message object is of type Configure
   * which contains the filename (or null for stdin), reference to
   * the collection actor to notify on completion, and the regular
   * expression object to match against.
   */
  public void onReceive(Object msg) {
     
  }
} 

/*
 * Object message to display results found by the collection actor
 */
class Found {
  
  //Everything we need to find the results of a grep 
  private String fileName;
  private List<String> resultsFound;

  public Found (String fileName, List<String> resultsFound) {
    this.fileName = fileName;  
    this.resultsFound = resultsFound;
  }
}
