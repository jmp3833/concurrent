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

    }

    if(msg instanceof AddToLineRequest) {

    }
    if(msg instanceof PassToScannerRequest) {

    }

    if(msg instanceof InitLineRequest) {
      //Setup ref to all relevant actors on the chain    
      InitLineRequest ilr = (InitLineRequest) msg;
      this.secStation = ilr.secStation; 
      this.bagScanner = ilr.bagScanner; 
      this.bodyScanner = ilr.bodyScanner; 
    }
  }
} 

