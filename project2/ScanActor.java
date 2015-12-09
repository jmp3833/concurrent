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
      ScanBagRequest sb = (ScanBagRequest) msg;

      System.out.println("Scanner is checking bag for passenger " 
          + sb.b.getPassenger().name);

      secStationRef.tell(new BagScannedRequest(
              ((ScanBagRequest) msg).b, FailureChance.randomFailure())
      );
    }

    else if(msg instanceof ScanBodyRequest) {
      System.out.println("Scanner is checking passenger " 
          + ((ScanBodyRequest) msg).p.name);
      secStationRef.tell(new BodyScannedRequest(
              ((ScanBodyRequest) msg).p, FailureChance.randomFailure())
      );
    }

    //Shut the system down
    else if(msg instanceof ShutdownRequest) {
      System.out.println("A scanner has recieved a message to shut down");
      secStationRef.tell(msg);
    }
  }
} 

