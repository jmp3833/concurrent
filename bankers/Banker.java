import java.util.HashMap;

class Banker {
  private final int nUnits;
  
  //Map storing each registered claim
  //Array is: [0] = nUnits, [1] = currently allocated
  private HashMap<String, int[]> claims;
  
  public Banker(int nUnits) {
    this.nUnits = nUnits; 
    this.claims = new HashMap<String, int[]>();
  }

  /*
   * The current thread attempts to register a claim for n units of
   * the given resource
   */
  public synchronized void setClaim(int nUnits) {
    //Grab the cirrent client trying to execute
    Client c = (Client)Thread.currentThread();
    
    /*
     * System.exit(1) if the val of nunits is not strictly positive or
     * the client has already registered a claim
     */
    if(nUnits > this.nUnits || nUnits < 1 || c.claimRegistered == true) {
      System.out.println(c.getName() + " has attempted to make an invalid claim");
      System.exit(1); 
    }
    
    //Register a claim on the current client thread.   
    System.out.println("Client " + c.getName() + " requests " + nUnits + "units.");
    c.claimRegistered = true;
    int[] arr = {nUnits, 0};
    claims.put(c.getName(), arr);
  } 
  
  /*
   * The current thread requests nUnits more resources.
   */
  public synchronized boolean request(int nUnits) {
    return true; 
  }
  
  /*
   * The current thread releases nUnits resources
   */
  public synchronized void release(int nUnits2) {
    
  }
  
  /*
   * Returns number of units allocated to the current thread
   */
  public synchronized int allocated() {
    return 0; 
  }
  
  /*
   * Returns number of units remaining in the current thread's claim
   */
  public synchronized int remaining() {
    return 0; 
  }
}
