class Troll {
  public int numCrossing = 0; 
  public int maxCrossing;

  public Troll(int maxCrossing) {
    this.maxCrossing = maxCrossing;
  }
  public static void main(String[] args) {
    Troll t =  new Troll(3);
    Bridge trollBridge = new Bridge(t);
    Woolie jack = new Woolie("Jack Bauer", 1, "Merctan", trollBridge);
    Woolie donald = new Woolie("Donald Trump", 1, "Sicstine", trollBridge);
    Woolie four = new Woolie("Four", 1, "Merctan", trollBridge);
    Woolie five = new Woolie("Five", 1, "Sicstine", trollBridge);
    Woolie six = new Woolie("Six", 1, "Merctan", trollBridge);

    jack.start();
    donald.start();
    four.start();
    five.start();
    six.start();

  }

  public synchronized void waitForApproval() {
    try {
      while(this.numCrossing >= this.maxCrossing){
        this.wait();
      }
      this.numCrossing++;
    } 
    catch(InterruptedException e) {
   
    }
  }

  public synchronized void endExecution() {
    this.numCrossing--; 
    this.notify();
  }
}
