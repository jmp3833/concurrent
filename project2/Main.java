import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.Arrays;

import akka.actor.Actors.*;
import akka.actor.*;

class Main {
  public static void main(String[] args) {

    ActorRef scan = Actors.actorOf(ScanActor.class);
    ActorRef general = Actors.actorOf(TSAGeneralActor.class);

    //Start the general to begin program execution
    general.start();

    scan.start();

    //Send a dummy ScanRequest
    scan.tell(new ScanRequest());

    //Shut it all down when we're done
    general.tell(new ShutdownRequest()); 
    
  }
}
