import akka.stm.Ref;
import akka.stm.Atomic;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Customer {
  private final long MAXCANDYLEVEL = 6;
  private final long MAXCOOKIELEVEL = 6;

  final Ref<Long> candyLevel = new Ref<Long>(MAXCANDYLEVEL);
  final Ref<Long> cookieLevel = new Ref<Long>(MAXCOOKIELEVEL);

  final Ref<Long> cookieUsageCount = new Ref<Long>(0L);
  final Ref<Long> candyUsageCount = new Ref<Long>(0L);

  final Ref<Boolean> keepRunning = new Ref<Boolean>(true);

  private static final ScheduledExecutorService replenishTimer =
    Executors.newScheduledThreadPool(10);

  private Customer() {}
  
  private void init() {   
    replenishTimer.schedule(new Runnable() {
      public void run() { 
        replenish();
        if (keepRunning.get()) replenishTimer.schedule(
          this, 3, TimeUnit.SECONDS);
      }
    }, 3, TimeUnit.SECONDS);
  }
  

  public static Customer create() {
    final Customer c = new Customer();
    c.init();
    return c;
  }

  public void stopCustomer() { keepRunning.swap(false); }

  public long getCookiesAvailable() { return cookieLevel.get(); }
  
  public long getCandyAvailable() { return candyLevel.get(); }

  public long getCookieUsageCount() { return cookieUsageCount.get(); }
  
  public long getCandyUsageCount() { return candyUsageCount.get(); }

  public boolean getCookie(final long units) {
    return  new Atomic<Boolean>() {
      public Boolean atomically() {
        long currentLevel = cookieLevel.get();
        if(units > 0 && currentLevel >= units) {
          cookieLevel.swap(currentLevel - units);
          cookieUsageCount.swap(cookieUsageCount.get() + 1);
          return true;          
        } else {
          return false;
        }
      }  
    }.execute();
  }
  
  public boolean getCandy(final long units) {
    return  new Atomic<Boolean>() {
      public Boolean atomically() {
        long currentLevel = candyLevel.get();
        if(units > 0 && currentLevel >= units) {
          candyLevel.swap(currentLevel - units);
          candyUsageCount.swap(candyUsageCount.get() + 1);
          return true;          
        } else {
          return false;
        }
      }  
    }.execute();
  }

  public void replenish() {
    new Atomic<Object>() {
      public Object atomically() {
        long currentCookieLevel = cookieLevel.get();
        long currentCandyLevel = candyLevel.get();

        if (currentCookieLevel < MAXCOOKIELEVEL) cookieLevel.swap(currentCookieLevel + 1);
        if (currentCandyLevel < MAXCANDYLEVEL) candyLevel.swap(currentCandyLevel + 1);

        return null;
      }
    }.execute();
  }
}
