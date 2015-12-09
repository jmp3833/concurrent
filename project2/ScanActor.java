import akka.actor.*;

class ScanActor extends UntypedActor {
  
  /*
   * Recieve a ScanRequest object with a passenger and its cooresponding
   * bag, or receive a message to shut down the system at the Scanner level.
   */
  public void onReceive(Object msg) {

    if(msg instanceof InitRequest){
      ActorRef jailRef = ((InitRequest) msg).jail;
    }
    //Perform a Scan operation on the given user or baggage
    if(msg instanceof BagScannedRequest) {
      if (FailureChance.randomFailure()){
        // passed
      } else {
        // failed the inspection
        Passenger criminal = ((BagScannedRequest) msg).b.getPassenger();
        jailRef.tell(new AddPrisonerRequest(criminal));
      }
    }

    else if(msg instanceof BodyScannedRequest) {
      if (FailureChance.randomFailure()){
        // passed
      } else {
        // failed the inspection
        Passenger criminal = ((BodyScannedRequest) msg).p;
        jailRef.tell(new AddPrisonerRequest(criminal));
      }
    }

    //Shut the system down
    else if(msg instanceof ShutdownRequest) {

    }
  }
} 

