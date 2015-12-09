import akka.actor.*;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Random;

class TSAGeneralActor extends UntypedActor {
  
  //Is the entire TSA service running?
  private boolean isRunning = true;
  private Random rng = new Random();

  //Queue of passengers waiting to enter a line at the TSA
  Queue<Passenger> passengers = new PriorityQueue<Passenger>();

  //Collection of lines that are available
  Queue<ActorRef> lines = new PriorityQueue<ActorRef>();
  
  /*
   * Recieve a ScanRequest object with a passenger and its cooresponding
   * bag, or receive a message to shut down the system at the Scanner level.
   */
  public void onReceive(Object msg) {
  
    //Shut the system down
    if(msg instanceof ShutdownRequest) {
      //Propogate message to all lines
      while(lines.peek() != null) {
        ActorRef l = lines.remove(); 

        //Spread the word, we're shutting down! 
        l.tell(msg);
      }
    }
    
    //Turn away passengers with document problems with a probability of 20%
    if(msg instanceof DocumentCheckRequest) {

      //Dequeue passenger
      Passenger p = passengers.remove();

      //1 in 5 chance of being True
      boolean problem = rng.nextInt(5 - 1 + 1) + 1 == 2? true : false;

      if(problem) {
        //Reject passenger, send them to beginning of line
        passengers.add(p);
      }
      else {
        //Grab next line in order
        ActorRef l = lines.remove();
        l.tell(new AddToLineRequest(p));
        //Add line back to the queue
        lines.add(l);
      }
    }
  }
} 
