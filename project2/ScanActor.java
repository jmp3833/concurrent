import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import akka.actor.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class ScanActor extends UntypedActor {
  
  /*
   * Recieve a ScanRequest object with a passenger and its cooresponding
   * bag, or receive a message to shut down the system at the Scanner level.
   */
  public void onReceive(Object msg) {

    //Perform a Scan operation on the given user or baggage
    if(msg instanceof ScanRequest) {
    
    }

    //Shut the system down
    else if(msg instanceof ShutdowmRequest) {
    
    }
    configure.collection.tell(itemsFound);
  }
} 

