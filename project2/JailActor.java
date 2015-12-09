import akka.actor.UntypedActor;

import java.util.ArrayList;
import java.util.List;

public class JailActor extends UntypedActor {
    private List<Passenger> detainees = new ArrayList<>();

    public void onReceive(Object msg) {
        //Add prisoner to the jail
        if(msg instanceof AddPrisonerRequest) {
            // if they are already in the jail, don't send them again
            Passenger passenger = ((AddPrisonerRequest) msg).p;
            if (detainees.indexOf(passenger) == -1){
                detainees.add(passenger);
            }
        }

        //Take all the prisoners to real jail
        if(msg instanceof ShutdownRequest) {
            System.out.println("Moving prisoners to real jail:");
            for(Passenger p : detainees) {
                System.out.println("  " + p.name);
            }
        }
    }
}
