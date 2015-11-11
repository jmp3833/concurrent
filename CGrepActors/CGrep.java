import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.Arrays;

import akka.actor.Actors.*;
import akka.actor.*;

class CGrep {
  public static void main(String[] args) {
    if(args.length < 2) {
      System.err.println("Not enough arguments");
      System.exit(1);
    }
    String pattern = args[0];
    Pattern reg = Pattern.compile(pattern);

    String[] filenames = new String[args.length - 1];
    for(int i = 1; i < args.length; i++) {
      filenames[i - 1] = args [i]; 
    }

    ActorRef collectionRef = Actors.actorOf(CollectionActor.class);
    
    //Fire off a scan actor for each file
    for(String s : filenames) {
      Configure conf = new Configure(s, collectionRef, reg);
      ActorRef sc = Actors.actorOf(ScanActor.class);  
      //Let the messages fly!
      sc.tell(conf);
    }         
  }
}

