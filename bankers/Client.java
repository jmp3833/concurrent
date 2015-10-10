import java.util.Random;

class Client extends Thread {

  /*
   * Final instance variables
   */
  private final Banker banker;
  private final int nRequests;
  private final int nUnits;
  private final long minSleepMillis;
  private final long maxSleepMillis;
  
  //random number generator for sleep/requests
  private Random rng;

  public Client(String name, Banker banker, int nUnits,
  int nRequests, long minSleepMillis, long maxSleepMillis) {
    super(name);
    this.banker = banker;
    this.nUnits = nUnits;
    this.nRequests = nRequests;
    this.minSleepMillis = minSleepMillis;
    this.maxSleepMillis = maxSleepMillis;
    rng = new Random();
  }
  
  /*
   * Register a claim for up to nUnits of resource with the banker. 
   * Then create a loop that will be executed nRequests times; each iteration 
   * will either request or release resources by invoking methods in the banker.
   */
  public void run() {
    banker.setClaim(nUnits);
    for(int i = 0; i < nRequests; i++) {
      int rem = banker.remaining();
      //if we already borrowed our max
      if(rem == 0) {
        banker.release(nUnits);
      } else {
        int req;
        if(rem == 1) {
          req = 1;
        } else {
          //If else needed because nextInt does not work with an argument of 0 (i.e. when rem = 1)
          req = rng.nextInt(rem - 1) + 1;
        }
        banker.request(req);
      }
      //wait a while before requesting/releasing anything
      long sleepTime = rng.nextInt((int) (maxSleepMillis - minSleepMillis - 1)) + minSleepMillis;
      try {
        Thread.sleep(sleepTime);
      } catch (InterruptedException e) {
        System.out.println("Thread " + getName() + " was interrupted while sleeping.");
      }
    }
    //Release everything, cause we're done borrowing
    banker.release(banker.remaining());
  }
}
