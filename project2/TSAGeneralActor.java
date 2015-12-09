import akka.actor.*;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Random;

class TSAGeneralActor extends UntypedActor {
  
  //Is the entire TSA service running?
  private boolean isRunning = true;
  private Random rng = new Random();

  //Collection of lines that are available
  public Queue<ActorRef> lines;

  /*
   * Recieve a ScanRequest object with a passenger and its cooresponding
   * bag, or receive a message to shut down the system at the Scanner level.
   */
  public void onReceive(Object msg) {
  
    //Shut the system down
    if(msg instanceof ShutdownRequest) {
      System.out.println("TSA General actor is initiating a shutdown");

      //Propogate message to all lines
      while(lines.peek() != null) {
        ActorRef l = lines.remove(); 

        //Spread the word, we're shutting down! 
        l.tell(msg);

        //Stop the specific line 
        l.stop();
      }
    }

    if(msg instanceof PriorityQueue) {
      //Unchecked cast to set lines in TSAGeneral
      @SuppressWarnings("unchecked")
      PriorityQueue<ActorRef> lns = (PriorityQueue<ActorRef>) msg;

      this.lines = lns;
      System.out.println("General got his lines!"); 
    }
    
    //Turn away passengers with document problems with a probability of 20%
    if(msg instanceof DocumentCheckRequest) {

      DocumentCheckRequest d = (DocumentCheckRequest) msg;
      Passenger p = d.getPassenger();

      //1 in 5 chance of being True
      boolean problem = FailureChance.randomFailure();

      if(problem) {
        //Reject passenger
        System.out.println("Passenger " + p.name + " was rejected from the document check");
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

