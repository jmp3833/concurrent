import akka.actor.UntypedActor;

import java.util.ArrayList;
import java.util.List;

public class JailActor extends UntypedActor {
    private List<Passenger> detainees = new ArrayList<>();

    public void onReceive(Object msg) {
        //Add prisoner to the jail
        if(msg instanceof AddPrisonerRequest) {
            detainees.add(((AddPrisonerRequest) msg).p);
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
