import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

import java.util.Timer;
import java.util.TimerTask;

import java.util.Random;

public class Main {

  private static final Customer fatAlbert = Customer.create();
  private static final Customer cookieMonster = Customer.create();
  private static final Customer willieWonka = Customer.create();

  public static void main(final String[] args) 
    throws InterruptedException, ExecutionException {

    //Random num between 1 and 10 when Wonka gets his candy
    Random random = new Random();
    final int randomSeed = random.nextInt(9 - 1 + 1) + 1;
    System.out.println("Random seed is: " + randomSeed);

    List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();    

    final ExecutorService service = Executors.newFixedThreadPool(10);
    service.invokeAll(tasks);

    TimerTask refillVendingMachine = new TimerTask() {
      public void run() {

        //Refill the vending machine
        System.out.println("refill vending machine");
        int count = 0;
        while (count <= 6) {
          fatAlbert.replenish();
          count++;
        } 

        count = 0;
      }
    };

    TimerTask fetchCookie = new TimerTask() {
      public void run() {
        //Get a cookie from the machine
        if(cookieMonster.getCookie(1)) {
          System.out.println("  Me love cookies");
        }
        else {
          System.out.println("  Me hungry");
        }
      }
    };

    TimerTask getCandyBar = new MyTimerTask() {

      @Override
      public void run() {

        if(this.numTasks == 9) {
          this.numTasks = 0;
        }

        if(this.numTasks == randomSeed) {
          //Get a candy bar from the machine
          if(willieWonka.getCandy(1)) {
            System.out.println("    The Candy Man Can");
          }
          else {
            System.out.println("    Violet - you're turning violet");
          }
        }

        this.numTasks++;
      }
    };

    TimerTask getFatAlbertSweg = new TimerTask() {
      public void run() {
        //Fetch the goods for fat albert
        boolean gotCookie = fatAlbert.getCookie(1);
        boolean gotCandy = fatAlbert.getCandy(1);

        if(gotCookie && gotCandy) {
          System.out.println("      Hey, hey hey!");
        }
        else if (gotCookie && !gotCandy) {
          System.out.println("      At least I got Cookie");
        }
        else if (!gotCookie && gotCandy) {
          System.out.println("      At least I got Candy");
        }
        else {
          System.out.println("      No food for me today");
        }
      }
    };

    TimerTask endThePeriod = new TimerTask() {
      public void run() {
        //Stop execution of all customers
        fatAlbert.stopCustomer();
        cookieMonster.stopCustomer();
        willieWonka.stopCustomer();
        service.shutdown();
        System.out.println("Fin.");
        System.exit(0);
      }
    };

    //Timer to schedule tasks of all customers visiting the vending machine
    Timer applicationSequencer = new Timer();

    // scheduling the refill task at fixed rate delay
    applicationSequencer.scheduleAtFixedRate(refillVendingMachine ,3000 ,3000);   

    //CookieMonster execution 
    applicationSequencer.scheduleAtFixedRate(fetchCookie, 500, 500);   

    //Willie wonka happening at one point during the day. this is handled at the Customer level 
    //Happens every 1/10th of a day with random success once 
    applicationSequencer.scheduleAtFixedRate(getCandyBar, 100, 100);   

    //Schedule Fat Albert's intervals 4 times a day
    applicationSequencer.scheduleAtFixedRate(getFatAlbertSweg, 250, 250);   

    //End after 15 days of simulation 
    applicationSequencer.schedule(endThePeriod, 15000);   

  }
}

class MyTimerTask extends TimerTask  {
  int numTasks;

  @Override
  public void run() {

  }
}
