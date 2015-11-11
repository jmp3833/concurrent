import static akka.actor.Actors.* ;
import static java.lang.Math.* ;

import akka.actor.* ;

/*
 * Class of messages sent to computing agents.
 * Four double precision numbers to be used as the calculator
 * sees fit.
 */
class Values {
    final double first ;
    final double second ;
    final double third ;
    final double fourth ;

    public Values(double f, double s, double t, double l) {
        first = f ;
        second = s ;
        third = t ;
        fourth = l ;
    }
}

/*
 * Abstract computational class.
 */
abstract class Computer extends UntypedActor {
    private final ActorRef reporter ;

    public Computer(final ActorRef rep) {
        reporter = rep ;
    }

    public void onReceive(Object message) {
        String result ;

        if ( message instanceof Values ) {
            Values v = (Values) message ;

            result = compute(v.first, v.second,
                             v.third, v.fourth) ;
            reporter.tell(result) ;
        } else {
            throw new IllegalArgumentException("Unknown message") ;
        }
    }

    abstract String compute(double one, double two,
            double three, double four ) ;
}

/*
 * Concrete computational class for quadratic equations.
 */

class Quadratic extends Computer {
    public Quadratic(ActorRef ref) {
        super(ref) ;
    }
    String compute(double a, double b, double c, double x) {
        return "Quadratic(" + a + ", " + b + ", " + c + ", " + x + "): " + (a * x * x + b * x + c) ;
    }
}

/*
 * Concrete computational class for distance between points.
 */

class Distance extends Computer {
    public Distance(ActorRef ref) {
        super(ref) ;
    }
    String compute(double x0, double y0, double x1, double y1) {
        double xdiff = x1 - x0 ;
        double ydiff = y1 - y0 ;

        return "Distance((" + x0 + ", " + y0 + ") to  (" + x1 + ", " + y1 + "): " +
                sqrt( xdiff * xdiff + ydiff * ydiff ) ;
    }
}

/*
 * Actor that reports the results of a computation.
 */

class Reporter extends UntypedActor {
    public void onReceive(Object message) {
        if ( message instanceof String ) {
            String result = (String) message ;

            System.out.println("Report") ;
            System.out.println("    " + result) ;
        } else {
            throw new IllegalArgumentException("Unknown message") ;
        }
    }
}

/*
 * The driver class.
 */

public class ActorDemo {
    static ActorRef quad ;  /* ref to quadratic computation actor */
    static ActorRef dist ;  /* ref to distance computation actor */
    static ActorRef rep ;   /* ref to reporter actor */

    /*
     * Just fire off some commands to the actors using tell.
     * (fire and forget).
     */

    static void doCommands() {
        quad.tell(new Values(2, 3, 4, 2)) ;
        quad.tell(new Values(1, 1, 2, 2)) ;
        dist.tell(new Values(2, 3, 4, 2)) ;
        dist.tell(new Values(1, 1, 2, 2)) ;
    }

    /*
     * Create references to three actors.
     * Run commands.
     * Shutdown actors with a poison pill.
     */

    public static void main(String[] args) {
        rep = actorOf(Reporter.class) ;

        quad = actorOf(
                new UntypedActorFactory() {
                    public UntypedActor create() {
                        return new Quadratic(rep) ;
                    }
                }
               ) ;

        dist = actorOf(
                new UntypedActorFactory() {
                    public UntypedActor create() {
                        return new Distance(rep) ;
                    }
                }
               ) ;

        rep.start() ; quad.start() ; dist.start() ;

        doCommands() ;

        quad.tell(poisonPill()) ;
        dist.tell(poisonPill()) ;

        try { Thread.sleep(1000) ; } catch( Exception e ) {} ;

        rep.tell(poisonPill()) ;
    }
}
