import java.util.Timer;
import java.util.TimerTask;

class PM extends Thread {
  public long startTime;
  public long endTime;
  private ConferenceRoom cr;
  private Timer firstTimer;
  private Timer secondTimer;
  private Timer leaveTimer;
  public PM(String name, ConferenceRoom c) {
	  super(name);
	  cr = c;
	  this.startTime = System.currentTimeMillis();
	  this.endTime = this.endTime + 5400;
	  firstTimer = new Timer();
	  secondTimer = new Timer();
      firstTimer.schedule(new firstTask(), 1200);
      secondTimer.schedule(new secondTask(), 3600);
      leaveTimer.schedule(new leaveTask(), 5400);
  }
  
  class firstTask extends TimerTask {
      public void run() {
    	  System.out.println("Project Manager is entering the morning Executive Meeting");
    	  try {
			PM.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          firstTimer.cancel(); //Terminate the timer thread
          System.out.println("Project Manager is leaving the morning Executive Meeting");
      }
  }
  class secondTask extends TimerTask {
      public void run() {
    	  System.out.println("Project Manager is entering the afternoon Executive Meeting");

    	  try {
			PM.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          secondTimer.cancel(); //Terminate the timer thread
    	  System.out.println("Project Manager is leaving the afternoon Executive Meeting");

      }
  }
  
  class leaveTask extends TimerTask {
      public void run() {
    	  System.out.println("Project Mangaer leaving for the day at 5:00pm!");
          leaveTimer.cancel(); //Terminate the timer thread
      }
  }
  
   
  public long getTime(){
      if(this.isAlive()) return(System.currentTimeMillis() - startTime);
      else return endTime;
  }
  public static void askPMQuestion(){
	  System.out.println("Asking the Project Manager a question");
	  try {
		  System.out.println("Project Manager is thinking...");
		PM.sleep(100);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  System.out.println("Project Manager answered question");
  }

}



