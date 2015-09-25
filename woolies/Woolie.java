class Woolie extends Thread {
  String name;
  Integer speed;
  String city;
  Bridge bridge;

  public Woolie(String name, Integer speed, String city, Bridge bridge) {
    this.name = name;
    this.speed = speed;
    this.city = city; 
    this.bridge = bridge;
  }

  public void run() {
    System.out.println(name + " arrives at the bridge");
    bridge.enterBridge(); 

    Integer counter = 0;
    System.out.println(name + " is starting to cross.");

    while(counter <= speed) {
      try {
        Thread.sleep(1000);
        System.out.println("\t " + name + " " + counter + " seconds.");
        counter++;
      }
      catch(InterruptedException e) {
      
      }
    }    

    System.out.println(name + " leaves at " + city);
    bridge.leaveBridge();
  }
}
