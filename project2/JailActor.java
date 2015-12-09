import akka.actor.UntypedActor;

import java.util.ArrayList;
import java.util.List;

public class JailActor extends UntypedActor {
    private List<Passenger> detainees = new ArrayList<>();
    private int stationsShutdown = 0;
    private int numLines;

    public void onReceive(Object msg) {
        //Init message
        if(msg instanceof InitRequest) {
            System.out.println("Jail is initalized... ");
            numLines = ((InitRequest) msg).numLines;
        }

        //Add prisoner to the jail
        if(msg instanceof AddPrisonerRequest) {
            // if they are already in the jail, don't send them again
            Passenger passenger = ((AddPrisonerRequest) msg).p;
            if (!detainees.contains(passenger)){
                detainees.add(passenger);
                System.out.println("Passenger " + passenger.name + " is being detained");
            }
        }

        //Take all the prisoners to real jail
        if(msg instanceof ShutdownRequest) {
            System.out.println("Jail has been notified to shut down ");
            stationsShutdown++;
            if(stationsShutdown == numLines) {
                System.out.println("Moving prisoners to real jail:");
                for(Passenger p : detainees) {
                    System.out.println("  " + p.name);
                }
            }
        }
    }
}
