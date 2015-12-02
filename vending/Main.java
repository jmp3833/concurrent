import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

  private static final Customer fatAlbert = Customer.create();
  private static final Customer cookieMonster = Customer.create();
  private static final Customer willieWonka = Customer.create();

  public static void main(final String[] args) 
    throws InterruptedException, ExecutionException {

    List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();    

    final ExecutorService service = Executors.newFixedThreadPool(10);
    service.invokeAll(tasks);

    TimerTask refillVendingMachine = new TimerTask() {
      public void run() {
        //Refill the vending machine

      }
    };
    
    TimerTask fetchCookie = new TimerTask() {
      public void run() {
        //Get a cookie from the machine

      }
    };
    
    TimerTask getCandyBar = new TimerTask() {
      public void run() {
        //Get a candy bar from the machine

      }
    };
    
    TimerTask getFatAlbertSweg = new TimerTask() {
      public void run() {
        //Fetch the goods for fat albert

      }
    };
    
    TimerTask endThePeriod = new TimerTask() {
      public void run() {
        //Stop execution of all customers
        fatAlbert.stopCustomer();
        cookieMonster.stopCustomer();
        willieWonka.stopCustomer();
        service.shutdown();
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
