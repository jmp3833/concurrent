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

    System.out.println("Initalize with " + NUM_PASSENGERS + " and " + NUM_LINES + " lines.");

    final Random rng = new Random();
    Queue<ActorRef> lines = new PriorityQueue<ActorRef>();

    ActorRef general = Actors.actorOf(TSAGeneralActor.class);
    ActorRef jail = Actors.actorOf(JailActor.class);

    //start jail
    jail.start();

    //Start the general to begin program execution
    general.start();

    for(int line = 0; line < NUM_LINES; line++) {
      InitRequest jailRequest = new InitRequest(null, null, null, jail, NUM_LINES);

      System.out.println("Initalize the jail from main");
      jail.tell(jailRequest);

      //Setup security station and scanners per line
      ActorRef sec = Actors.actorOf(SecurityStationActor.class);
      sec.start();
      
      System.out.println("Initalize the security station from main");
      sec.tell(jailRequest);

      InitRequest secRequest = new InitRequest(sec, null, null, jail, NUM_LINES);

      ActorRef bagScan = Actors.actorOf(ScanActor.class);
      bagScan.start();

      System.out.println("Initalize bag scanner from main");
      bagScan.tell(secRequest);

      ActorRef bodyScan = Actors.actorOf(ScanActor.class);
      bodyScan.start();
      System.out.println("Initalize body scanner from main");
      bodyScan.tell(secRequest);

      ActorRef ln = Actors.actorOf(LineActor.class);
      ln.start();

      ln.tell(new InitRequest(sec, bagScan, bodyScan, jail, NUM_LINES));
      System.out.println("Initalize line from main");

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
    try {
      Thread.sleep(500);
    } catch(Exception e) {
      e.printStackTrace(); 
    }

    System.out.println("System is initiating the shutdown process...");
    general.tell(new ShutdownRequest()); 
  }
}
