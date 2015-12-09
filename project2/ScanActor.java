import akka.actor.*;

class ScanActor extends UntypedActor {
  ActorRef secStationRef;


  /*
   * Recieve a ScanRequest object with a passenger and its cooresponding
   * bag, or receive a message to shut down the system at the Scanner level.
   */
  public void onReceive(Object msg) {

    if(msg instanceof InitRequest) {
      secStationRef = ((InitRequest) msg).secStation;
    }
    //Perform a Scan operation on the given user or baggage
    if(msg instanceof ScanBagRequest) {
      secStationRef.tell(new BagScannedRequest(
              ((ScanBagRequest) msg).b, FailureChance.randomFailure())
      );
    }

    else if(msg instanceof ScanBodyRequest) {
      secStationRef.tell(new BodyScannedRequest(
              ((ScanBodyRequest) msg).p, FailureChance.randomFailure())
      );
    }

    //Shut the system down
    else if(msg instanceof ShutdownRequest) {
      secStationRef.tell(msg);
    }
  }
} 

