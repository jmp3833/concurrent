import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import akka.actor.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class ScanActor extends UntypedActor {

  /*
   * Construct a Found object to feed back to the 
   * collection actor. The message object is of type Configure
   * which contains the filename (or null for stdin), reference to
   * the collection actor to notify on completion, and the regular
   * expression object to match against.
   */
  public void onReceive(Object msg) {

    Configure configure = (Configure) msg;
    msg = (Configure) msg;
    List<String> results = new ArrayList<String>();

    //Append filename to the beginning of the list to tell them apart
    results.add(configure.fileName);

    try {
      BufferedReader br = new BufferedReader(new FileReader(configure.fileName));
      String line;

      try {
        while((line = br.readLine()) != null) {
          //Build a pattern matcher on the regex 
          Matcher m = configure.reg.matcher(line);
          if(m.matches()) {
            //Add line to the list if the pattern matches
            results.add(line);
          }
        }
      }
      //Swallow buffer exception for now
      catch(IOException e){
      }
    }
    catch(FileNotFoundException e) {
    }
    //Construct the message to the Configure actor
    Found itemsFound = new Found(configure.fileName, results);
    configure.collection.tell(itemsFound);
  }
} 

