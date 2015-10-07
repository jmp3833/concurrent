class Client extends Thread {

  /*
   * Final instance variables
   */
  private final String name;
  private final Banker banker;
  private final int nUnits;
  private final long minSleepMillis;
  private final long maxSleepMillis;
  
  /*
   * Mutable instance variables
   */  
  private int nRequests;

  public Client(String name, Banker banker, int nUnits,
  int nRequests, long minSleepMillis, long maxSleepMillis) {
    this.name = name;
    this.banker = banker;
    this.nUnits = nUnits;
    this.nRequests = nRequests;
    this.minSleepMillis = minSleepMillis;
    this.maxSleepMillis = maxSleepMillis; 
  }
  
  /*
   * Register a claim for up to nUnits of resource with the banker. 
   * Then create a loop that will be executed nRequests times; each iteration 
   * will either request or release resources by invoking methods in the banker.
   */
  public void run() {
  
  }

}
