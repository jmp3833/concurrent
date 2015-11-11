import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.Arrays;

import akka.actor.*;
import akka.actor.Actors.*;

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
        
        ActorRef collectionActorRef = actorOf(new CollectionActor());
        CollectionActor collect = new CollectionActor();
        ScanActor scan = new ScanActor(new Configure("in.txt", collectionActorRef, reg));

    }
}

class Configure {
    
    String fileName;
    Pattern reg;
    ActorRef collection;  

    public Configure(String fileName, ActorRef collection, Pattern reg) {
        this.fileName = fileName;
        this.collection = collection;    
    }
}

class CollectionActor extends UntypedActor {
  public void onReceive(Object message) {
  
  }
}

