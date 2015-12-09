import akka.actor.*;
import java.util.Queue;
import java.util.PriorityQueue;

class LineActor extends UntypedActor {

  //Queue of passengers waiting to enter a line at the TSA
  Queue<Passenger> passengers = new PriorityQueue<Passenger>();

  //Queue of baggage waiting to enter a line at the TSA
  Queue<Bag> baggage = new PriorityQueue<Bag>();

  ActorRef secStation;
  ActorRef bagScanner;
  ActorRef bodyScanner;

  public void onReceive(Object msg) {
    //Shut the system down
    if(msg instanceof ShutdownRequest) {
      //Tell all my pals that I'm shutting down, then shut down 
      secStation.tell(msg);
      bagScanner.tell(msg);
      bodyScanner.tell(msg);
    }

    if(msg instanceof AddToLineRequest) {
      //Pass baggage and passengers to appropriate scanners
    }

    if(msg instanceof InitRequest) {
      //Setup ref to all relevant actors on the chain    
      InitRequest ilr = (InitRequest) msg;
      this.secStation = ilr.secStation; 
      this.bagScanner = ilr.bagScanner; 
      this.bodyScanner = ilr.bodyScanner; 
    }
  }
} 

