import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.Arrays;

import akka.actor.Actors.*;
import akka.actor.*;

class Main {
  public static void main(String[] args) {

    ActorRef scan = Actors.actorOf(ScanActor.class);
    scan.start();

    //Send a dummy ScanRequest
    scan.tell(new ScanRequest());
    
  }
}


