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
    for (int i = 0; i < numClients; i++) {
      //TODO: Instantiate some clients
    }
  }
}
