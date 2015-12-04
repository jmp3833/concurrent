import akka.actor.*;
import akka.actor.UntypedActor;

class SecurityStationActor extends UntypedActor {

    /*
     * Recieve a BodyScanned and BagScanned object with the respective object
     * and pass/fail or receive a message to shut down the system at the station.
     */
    public void onReceive(Object msg) {

        // Body Scan logic. Message should have the body and pass/ fail.
        if(msg instanceof BodyScannedRequest) {

        }

        // Bag Scan logic. Message should have the bag and pass/ fail.
        if(msg instanceof BagScannedRequest) {

        }

        //Shut the system down
        else if(msg instanceof ShutdownRequest) {

        }
    }
}

