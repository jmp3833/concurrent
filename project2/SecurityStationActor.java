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
            BodyScannedRequest bodySR = (BodyScannedRequest) msg;
            if (bodySR.passed) {

            }
        }

        // Bag Scan logic. Message should have the bag and pass/ fail.
        if(msg instanceof BagScannedRequest) {
            BagScannedRequest bagSR = (BagScannedRequest) msg;
            if (bagSR.passed) {
                
            }
        }

        //Shut the system down
        else if(msg instanceof ShutdownRequest) {

        }
    }
}

