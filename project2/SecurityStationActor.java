import akka.actor.*;
import akka.actor.UntypedActor;

import java.util.ArrayList;
import java.util.List;

class SecurityStationActor extends UntypedActor {
    private List<Passenger> waiting = new ArrayList<>();
    int shutdownsReceived = 0;
    ActorRef jailRef;

    /*
     * Recieve a BodyScanned and BagScanned object with the respective object
     * and pass/fail or receive a message to shut down the system at the station.
     */
    public void onReceive(Object msg) {
        if(msg instanceof InitRequest){
            jailRef = ((InitRequest) msg).jail;
        }

        // Body Scan logic. Message should have the body and pass/ fail.
        if(msg instanceof BodyScannedRequest) {
            BodyScannedRequest bodySR = (BodyScannedRequest) msg;
            if (bodySR.passed) {
                int passed = 0;
                for(Bag b : bodySR.p.bags) {
                    if(b.getScanCompleted() && !b.getScanPassed()) {
                        //A bag failed a check
                        jailRef.tell(new AddPrisonerRequest(bodySR.p));
                        break;
                    }
                    if(b.getScanCompleted() && b.getScanPassed()) {
                        passed++;
                    }
                }
                if(passed == bodySR.p.getNumBags()) {
                    //Passenger passed all checks
                    System.out.println("Passenger " + bodySR.p.name + " finishes passing through security");
                } else {
                    waiting.add(bodySR.p);
                }
            } else {
                // failed the inspection
                System.out.println("Sending passenger " + ((BodyScannedRequest) msg).p.name +
                    " from sec station to jail");
                Passenger criminal = ((BodyScannedRequest) msg).p;
                jailRef.tell(new AddPrisonerRequest(criminal));
            }
        }

        // Bag Scan logic. Message should have the bag and pass/ fail.
        if(msg instanceof BagScannedRequest) {
            BagScannedRequest bagSR = (BagScannedRequest) msg;
            if (!bagSR.passed) {
                // failed the inspection
                Passenger criminal = ((BagScannedRequest) msg).b.getPassenger();
                jailRef.tell(new AddPrisonerRequest(criminal));
            }

            //Check all of the waiting passengers to see if their bags have passed
            for(int i = 0; i < waiting.size(); i++) {
                int passed = 0;
                Passenger p = waiting.get(i);

                for(int j = 0; j < p.bags.size(); j++) {
                    Bag b = p.bags.get(j);
                    if(b.getScanCompleted() && !b.getScanPassed()) {
                        //A bag failed a check
                        jailRef.tell(new AddPrisonerRequest(p));
                        break;
                    }
                    if(b.getScanCompleted() && b.getScanPassed()) {
                        passed++;
                    }
                }
                if(passed == p.getNumBags()) {
                    //Passenger passed all checks
                    System.out.println("Passenger " + p.name + " finishes passing through security");
                } else {
                    waiting.add(p);
                }
            }
        }

        //Shut the system down
        else if(msg instanceof ShutdownRequest) {
            this.shutdownsReceived++;
            if(this.shutdownsReceived == 2) {
                jailRef.tell(new ShutdownRequest());
            }
        }
    }
}

