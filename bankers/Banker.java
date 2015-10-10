import java.util.HashMap;
import java.util.Set;

class Banker {
  //max units a banker can have available
  private final int maxUnits;
  
  //number of units left to lend out
  private int unitsRemaining;
  
  //Map storing each registered claim
  //Array is: [0] = nUnits, [1] = currently allocated
  private HashMap<String, int[]> claims;
  
  public Banker(int nUnits) {
    this.maxUnits = nUnits;
    this.unitsRemaining = nUnits;
    this.claims = new HashMap<String, int[]>();
  }

  /*
   * The current thread attempts to register a claim for n units of
   * the given resource
   */
  public synchronized void setClaim(int nUnits) {
    //Grab the current client trying to execute
    Client c = (Client)Thread.currentThread();
    
    /*
     * System.exit(1) if the val of nunits is not strictly positive or
     * the client has already registered a claim
     */
    if(nUnits > maxUnits || nUnits < 1 || claims.containsKey(c.getName())) {
      System.out.println(c.getName() + " has attempted to make an invalid claim");
      System.exit(1); 
    }
    
    //Register a claim on the current client thread.
    int[] arr = {nUnits, 0};
    claims.put(c.getName(), arr);
    System.out.println("Thread " + c.getName() + " sets a claim for " + nUnits + " units.");
  } 
  
  /*
   * The current thread requests nUnits more resources.
   */
  public synchronized boolean request(int nUnits) {
    //Grab the current client trying to execute
    Client c = (Client)Thread.currentThread();
    //And their claim data
    int[] cliDat = claims.get(c.getName());
    
    /*
     * System.exit(1) if (a) the current thread has no claim registered, 
     * (b) nUnits is not strictly positive or 
     * (c) nUnits exceeds the invoking thread's remaining claim.
     */
    if(!claims.containsKey(c.getName()) || nUnits < 1 || nUnits > cliDat[0] - cliDat[1]) {
      System.out.println("Thread " + c.getName() + " made an invalid request: " + nUnits);
      System.exit(1);
    }
    
    //If safe request, grant it, otherwise wait for it to be a safe request
    if(isSafeRequest(c.getName(), nUnits)) {
      unitsRemaining -= nUnits;
      claims.get(c.getName())[1] += nUnits;
      System.out.println("Thread " + c.getName() + "  has " + claims.get(c.getName())[1] + " units allocated.");
    } else {
      //Wait until it is safe to request this amount
      while(!isSafeRequest(c.getName(), nUnits)) {
        try {
          wait();
        } catch (InterruptedException e) {
          System.out.println("Thread " + c.getName() + " was interrupted while waiting.");
        }
      }
      //Finish the request
      unitsRemaining -= nUnits;
      claims.get(c.getName())[1] += nUnits;
      System.out.println("Thread " + c.getName() + "  has " + claims.get(c.getName())[1] + " units allocated.");
    }
    return true; 
  }
  
  /*
   * The current thread releases nUnits resources
   */
  public synchronized void release(int nUnits) {
    //Grab the current client trying to execute
    Client c = (Client)Thread.currentThread();
    //And their claim data
    int[] cliDat = claims.get(c.getName());
    
    /*
     * System.exit(1) if (a) the current thread has no claim registered,
     * (b) nUnits is not strictly positive or
     * (c) nUnits exceeds the number of units allocated to the current thread.
     */
    if(!claims.containsKey(c.getName()) || nUnits < 1 || nUnits > cliDat[1]) {
      System.out.println("Thread " + c.getName() + " tried to release " + nUnits + " units.");
      System.exit(1);
    }
    
    System.out.println("Thread " + c.getName() + " releases " + nUnits + " units.");
    unitsRemaining += nUnits;
    cliDat[1] -= nUnits;
    notifyAll();
  }
  
  /*
   * Returns number of units allocated to the current thread
   */
  public synchronized int allocated() {
    //Grab the current client trying to execute
    Client c = (Client)Thread.currentThread();
    //And their claim data
    int[] cliDat = claims.get(c.getName());
    return cliDat[1]; 
  }
  
  /*
   * Returns number of units remaining in the current thread's claim
   */
  public synchronized int remaining() {
    //Grab the current client trying to execute
    Client c = (Client)Thread.currentThread();
    //And their claim data
    int[] cliDat = claims.get(c.getName());
    return cliDat[0] - cliDat[1]; 
  }
  
  private boolean isSafeRequest(String name, int request) {
    Set<String> clients = claims.keySet();
    clients.remove(name);//remove the requesting client from our client set
    int newRemaining = unitsRemaining - request;
    
    //If the request is more than the banker has left, obviously we can't grant the request
    if(newRemaining < 0) {
      return false;
    }
    
    //If the client making the request reaches their max claim,
    //or enough remains for them to, then it's safe
    int[] cliDat = claims.get(name);
    if(cliDat[1] - cliDat[0] - request <= newRemaining) {
      return true;
    }
    
    //Looping through all of the clients, if anyone can finish, it's a safe request
    for(String i : clients) {
      cliDat = claims.get(i);
      if(cliDat[1] - cliDat[0] - request <= newRemaining) {
        return true;
      }
    }
    
    return false;
  }
}
