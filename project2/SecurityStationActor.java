import akka.actor.*;
import akka.actor.UntypedActor;

import java.util.ArrayList;
import java.util.List;

class SecurityStationActor extends UntypedActor {
    List<Passenger> waiting = new ArrayList<>();
    int shutdownsReceived = 0;

    /*
     * Recieve a BodyScanned and BagScanned object with the respective object
     * and pass/fail or receive a message to shut down the system at the station.
     */
    public void onReceive(Object msg) {

        // Body Scan logic. Message should have the body and pass/ fail.
        if(msg instanceof BodyScannedRequest) {
            BodyScannedRequest bodySR = (BodyScannedRequest) msg;
            if (bodySR.passed) {
                for(Bag b : bodySR.p.bags) {
                    
                }
            } else {
                //Body scan failed
                //TODO tell jail
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
            this.shutdownsReceived++;
            if(this.shutdownsReceived == 2) {
                //TODO send shutdown to jail
            }
        }
    }
}

