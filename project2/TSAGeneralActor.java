import akka.actor.*;
import com.sun.deploy.association.utility.AppAssociationWriter;

import java.util.Queue;
import java.util.PriorityQueue;
import java.util.ArrayList;

class TSAGeneralActor extends UntypedActor {

    //Is the entire TSA service running?
    private boolean isRunning = true;

    //Queue of passengers waiting to enter a line at the TSA
    Queue<Passenger> passengers = new PriorityQueue<Passenger>();

    //Collection of lines that are available
    ArrayList<ActorRef> lines = new ArrayList<ActorRef>();
    int currentLine = 0;//Line to put next passenger into

    /*
     * Recieve a ScanRequest object with a passenger and its cooresponding
     * bag, or receive a message to shut down the system at the Scanner level.
     */
    public void onReceive(Object msg) {
        //Shut the system down
        if (msg instanceof ShutdownRequest) {

        }

        if (msg instanceof DocumentCheckRequest) {
            Passenger p = ((DocumentCheckRequest) msg).p;
            //20% failure chance
            boolean pass = FailureChance.randomFailure();
            if (pass) {
                //Send to a line
                lines.get(currentLine).tell(new NewPassengerRequest(p));
                System.out.println(p.name + " passed the document check.");
                //Increment line index
                currentLine++;
                currentLine = currentLine % lines.size();
            } else {
                System.out.println(p.name + " failed the document check.");
            }
        }

        if (msg instanceof SendToLineRequest) {

        }
    }
} 

