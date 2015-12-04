import akka.actor.*;
import java.util.Queue;
import java.util.PriorityQueue;

class LineActor extends UntypedActor {
  
  public void onReceive(Object msg) {
    //Shut the system down
    if(msg instanceof ShutdownRequest) {
    
    }
  }
} 

