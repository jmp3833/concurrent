import java.util.Random;

class Developer extends Thread {

  private Random rng;

  public Developer(String name) {
    super(name);
    rng = new Random();
  }
  
  /*
   * Conduct a standard day for the developer 
   */
  public void run() {

    //Enter the office anywhere between 8 and 8:30 AM
    try {
      int arrivalMillis = rng.nextInt(290);
      Thread.sleep(arrivalMillis);

      String arrivalTime = (arrivalMillis / 10 < 10) ? 
        ("0" + Integer.toString(arrivalMillis / 10)) : Integer.toString(arrivalMillis / 10);

      System.out.println("Developer " + super.getName() + "enters the office at 8:"
          + arrivalTime);
    }

    catch(InterruptedException e) {
      e.printStackTrace(); 
    }
  }

}
