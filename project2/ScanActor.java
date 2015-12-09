import akka.actor.*;

class ScanActor extends UntypedActor {

    /*
     * Recieve a ScanRequest object with a passenger and its cooresponding
     * bag, or receive a message to shut down the system at the Scanner level.
     */
    public void onReceive(Object msg) {

        //Perform a Scan operation on the given user or baggage
        if (msg instanceof ScanPassengerRequest) {

        }

        if (msg instanceof ScanBagRequest) {
        }

        //Shut the system down
        else if (msg instanceof ShutdownRequest) {

        }
    }
} 

