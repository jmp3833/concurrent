import java.util.concurrent.*;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import akka.actor.*;

class ScanActor extends UntypedActor {
  
  protected String filename;
  protected Pattern regex;

  public ScanActor(String filename, Pattern regex) {
    this.filename = filename; 
    this.regex = regex;
  }

  public void onReceive(Object msg) {
  
  }
} 

/*
 * Object message to display results found by the collection actor
 */
class Found {
  
  //Everything we need to find the results of a grep 
  private String fileName;
  private String resultsFound;

  public Found (String fileName, String resultsFound) {
    this.fileName = fileName;  
    this.resultsFound = resultsFound;
  }
}
