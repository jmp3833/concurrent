import akka.actor.*;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.ArrayList;

class TSAGeneralActor extends UntypedActor {
  
  //Is the entire TSA service running?
  private boolean isRunning = true;

  //Queue of passengers waiting to enter a line at the TSA
  Queue<Passenger> passengers = new PriorityQueue<Passenger>();

  //Collection of lines that are available
  ArrayList<LineActor> lines = new ArrayList<LineActor>();
  
  /*
   * Recieve a ScanRequest object with a passenger and its cooresponding
   * bag, or receive a message to shut down the system at the Scanner level.
   */
  public void onReceive(Object msg) {
    //Shut the system down
    if(msg instanceof ShutdownRequest) {
    
    }

    if(msg instanceof DocumentCheckRequest) {
    
    }

    if(msg instanceof SendToLineRequest) {
    
    }
  }
} 

