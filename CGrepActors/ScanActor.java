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

  public List<String> call() throws Exception {
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
      catch(IOException e){
        throw new Exception("An error occurred when attempting to parse" + 
            "the file " + filename, e); 
      }
    }
    catch(FileNotFoundException e) {
      throw new Exception("The filename " + filename + " was not found", e);
    }
    return results;
  }
} 
