import akka.actor.*;
import java.util.Queue;
import java.util.PriorityQueue;

class LineActor extends UntypedActor {

  //Queue of passengers waiting to enter a line at the TSA
  Queue<Passenger> passengers = new PriorityQueue<Passenger>();

  ActorRef secStation;
  ActorRef bagScanner;
  ActorRef bodyScanner;

  public void onReceive(Object msg) {
    //Shut the system down
    if(msg instanceof ShutdownRequest) {
      //Tell all my pals that I'm shutting down, then shut down 
      System.out.println("A line has been notified to shut down");

      bagScanner.tell(msg);
      bodyScanner.tell(msg);

    }

    if(msg instanceof AddToLineRequest) {
      //Pass baggage and passengers to appropriate scanners
      AddToLineRequest atl = (AddToLineRequest) msg; 
      Passenger p = atl.getPassenger(); 
      System.out.println("Adding passenger " + p.name + " to line with it's baggage");
      
      //Send the baggage through 
      for(int i = 0; i < p.getNumBags(); i++) {
        bagScanner.tell(new ScanBagRequest(p.getBagAtIndex(i)));
      }

      //Send the passenger through
      bodyScanner.tell(new ScanBodyRequest(p));
    }

    if(msg instanceof InitRequest) {
      //Setup ref to all relevant actors on the chain    
      System.out.println("Setting up a line for the day!");
      InitRequest ilr = (InitRequest) msg;
      this.secStation = ilr.secStation; 
      this.bagScanner = ilr.bagScanner; 
      this.bodyScanner = ilr.bodyScanner; 
    }
  }
} 

