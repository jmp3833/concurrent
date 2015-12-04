import akka.actor.*;

class TSAGeneralActor extends UntypedActor {
  
  /*
   * Recieve a ScanRequest object with a passenger and its cooresponding
   * bag, or receive a message to shut down the system at the Scanner level.
   */
  public void onReceive(Object msg) {
    //Shut the system down
    if(msg instanceof ShutdownRequest) {
    
    }
  }
} 

