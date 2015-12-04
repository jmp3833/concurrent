import akka.actor.*;
import java.util.Queue;
import java.util.PriorityQueue;

class LineActor extends UntypedActor {
 
  //Queue of passengers waiting to enter a line at the TSA
  Queue<Passenger> passengers = new PriorityQueue<Passenger>();
  
  //Queue of baggage waiting to enter a line at the TSA
  Queue<Bag> baggage = new PriorityQueue<Bag>();

  SecurityStationActor secStation;
  ScanActor bagScanner;
  ScanActor bodyScanner;
 
  //Apply relevant actors to this line
  public LineActor(SecurityStationActor secStation, ScanActor bagScanner, ScanActor bodyScanner) {
    this.secStation = secStation;
    this.bagScanner = bagScanner;
    this.bodyScanner = bodyScanner;
  }
  
  public void onReceive(Object msg) {
    //Shut the system down
    if(msg instanceof ShutdownRequest) {
    
    }

    if(msg instanceof AddToLineRequest) {
    
    }
    if(msg instanceof PassToScannerRequest) {
    
    }
  }
} 

