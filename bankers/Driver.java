class Driver {
  
  //Number of resources that are allocated to the banker
  private final static int numResources = 0;

  //Number of clients to instantiate
  private final static int numClients = 5;
  
  //Max number of units that can be requested by a given client
  private final static int nClientUnits = 3;
  
  //Max number of units that can be given out by the banker 
  private final static int nBankerUnits = 15;

  //Number of requests made by each client
  private final static int nRequests = 5;

  //Minimum time (in ms) a client can sleep between requests
  private final static int minSleepMillis = 1000;

  //Maximum time (in ms) a client can sleep between requests
  private final static int maxSleepMillis = 6000;
  
  //The amount of time each client thread is given to execute
  private final static int threadDuration = 1000;
 

  /*
   * (a) creates a Banker object,
   * (b) creates several Client objects, 
   * (c) starts all of the Clients, and 
   * (d) waits for all the clients to complete via the instance method join( )
   */
  public static void main(String[] args) {
    System.out.println("Instantiating Objects...");

    Banker banker = new Banker(nBankerUnits);
    Client[] clients = new Client[numClients];

    //Name and instantiate all the clients
    //Then run them one by one
    for (int i = 0; i < numClients; i++) {
      clients[i] = new Client("client" + i, banker, nClientUnits, nRequests, minSleepMillis, maxSleepMillis); 
      clients[i].start();
    }

    //Call join() on each client to make sure
    //each thread has completed before completing the main thread.
    for (int i = 0; i < numClients; i++) {
      try {
        clients[i].join(threadDuration);
        System.out.println("Client " + clients[i].name + " is done for the day!");
      }
      catch(InterruptedException e) {
        System.out.println("Issue joining thread " +  clients[i].name);
      }
    }

    System.out.println("Clients have completed execution. Let's call it a day!");

    //Program execution has completed
    System.exit(1);
  }
}
