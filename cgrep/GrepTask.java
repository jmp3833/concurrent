import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.io.*;

class GrepTask implements Callable<String> {
  
  protected String filename;
  protected Pattern regex;

  public GrepTask(String filename, Pattern regex) {
    this.filename = filename; 
    this.regex = regex;
  }

  public String call() {
    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      String line;
      try {
        while((line = br.readLine()) != null) {
          System.out.println(line); 
        }
      }
      //Swallow buffer exception for now
      catch(Exception e){}
    }
    catch(FileNotFoundException e) {
      System.out.println("The filename " + filename + " was not found"); 
    }
    return "";
  }
} 
