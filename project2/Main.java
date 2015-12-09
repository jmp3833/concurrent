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
    ActorRef jail = Actors.actorOf(JailActor.class);

    //start jail
    jail.start();

    //Start the general to begin program execution
    general.start();

    for(int line = 0; line < NUM_LINES; line++) {
      InitRequest jailRequest = new InitRequest(null, null, null, jail);

      //Setup security station and scanners per line
      ActorRef sec = Actors.actorOf(SecurityStationActor.class);
      sec.start();
      sec.tell(jailRequest);

      InitRequest secRequest = new InitRequest(sec, null, null, jail);

      ActorRef bagScan = Actors.actorOf(ScanActor.class);
      bagScan.start();
      bagScan.tell(secRequest);

      ActorRef bodyScan = Actors.actorOf(ScanActor.class);
      bodyScan.start();
      bodyScan.tell(secRequest);

      ActorRef ln = Actors.actorOf(LineActor.class);
      ln.start();

      ln.tell(new InitRequest(sec, bagScan, bodyScan, jail));
      lines.add(ln);
    }
    
    //Send the general it's collection of lines 
    general.tell(lines);

    for(int i = 0; i < NUM_PASSENGERS; i++) {
      String name = "Passenger " + i;
      int numBags = rng.nextInt(MAX_BAGS - MIN_BAGS + 1) + MIN_BAGS;

      Passenger p = new Passenger(name, numBags);

      System.out.println("Passenger " + p.name + " headed to the document check with "
          +  numBags + " bags!");
      general.tell(new DocumentCheckRequest(p)); 
    }

    //Shut it all down when we're done
    System.out.println("System is initiating the shutdown process...");
    general.tell(new ShutdownRequest()); 
    general.stop();
  }
}
