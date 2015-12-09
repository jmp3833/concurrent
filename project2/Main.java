import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.Random;
import java.util.Queue;
import java.util.PriorityQueue;

import akka.actor.Actors.*;
import akka.actor.*;

class Main {
  public static void main(String[] args) {
    
    //Configurable parameters of app
    final int NUM_PASSENGERS = 5;
    final int MAX_BAGS = 5;
    final int MIN_BAGS = 1;
    final int NUM_LINES = 2;

    final Random rng = new Random();
    Queue<ActorRef> lines = new PriorityQueue<ActorRef>();

    ActorRef general = Actors.actorOf(TSAGeneralActor.class);

    //Start the general to begin program execution
    general.start();

    for(int line = 0; line < NUM_LINES; line++) {

      //Setup security station and scanners per line
      ActorRef sec = Actors.actorOf(SecurityStationActor.class);
      ActorRef bagScan = Actors.actorOf(ScanActor.class);
      ActorRef bodyScan = Actors.actorOf(ScanActor.class);

      ActorRef ln = Actors.actorOf(LineActor.class);
      ln.start();

      ln.tell(new InitLineRequest(sec, bagScan, bodyScan));
      lines.add(ln);
    }
    
    //Send the general it's collection of lines 
    general.tell(lines);

    for(int i = 0; i < NUM_PASSENGERS; i++) {
      String name = "Passenger " + i;
      int numBags = rng.nextInt(MAX_BAGS - MIN_BAGS + 1) + MIN_BAGS;

      Passenger p = new Passenger(name, numBags);

      System.out.println("Passenger " + p.name + " headed to the document check with " +  numBags + " bags!");
      general.tell(new DocumentCheckRequest(p)); 
    }

    //Shut it all down when we're done
    System.out.println("Shutting down the system...");
    general.tell(new ShutdownRequest()); 
  }
}
