import java.util.concurrent.*;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

class GrepTask implements Callable<List<String>> {
  
  protected String filename;
  protected Pattern regex;

  public GrepTask(String filename, Pattern regex) {
    this.filename = filename; 
    this.regex = regex;
  }

  public List<String> call() {
    List<String> results = new ArrayList<String>();

    //Append filename to the beginning of the list to tell them apart
    results.add(filename);

    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      String line;

      try {
        while((line = br.readLine()) != null) {
          //Build a pattern matcher on the regex 
          Matcher m = regex.matcher(line);
          if(m.matches()) {
            //Add line to the list if the pattern matches
            results.add(line);
          }
        }
      }
      //Swallow buffer exception for now
      catch(Exception e){}
    }
    catch(FileNotFoundException e) {
      System.out.println("The filename " + filename + " was not found"); 
    }
    return results;
  }
} 
