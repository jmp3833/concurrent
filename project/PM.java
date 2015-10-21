import java.util.Timer;
import java.util.TimerTask;

class PM extends Thread {
  public long startTime;
  public long endTime;
  private ConferenceRoom cr;
  private Timer firstTimer;
  private Timer secondTimer;
  private Timer lunchTimer;
  private Timer leaveTimer;
  public PM(String name, ConferenceRoom c) {
	  super(name);
	  cr = c;
	  this.startTime = System.currentTimeMillis();
	  this.endTime = this.endTime + 5400;
	  firstTimer = new Timer();
	  secondTimer = new Timer();
      firstTimer.schedule(new meetingTask(firstTimer), 1200);
      lunchTimer.schedule(new meetingTask(lunchTimer), 2400);
      secondTimer.schedule(new meetingTask(secondTimer), 3600);
      leaveTimer.schedule(new meetingTask(leaveTimer), 5400);
  }
  
  class meetingTask extends TimerTask {
	 Timer timer;
	 public meetingTask(Timer timer){
		 this.timer = timer;
	 }
	 @Override
	  public void run() {
		 if(this.timer.equals(firstTimer)){
	    	  System.out.println("Project Manager is entering the morning Executive Meeting");
		 }
		 else if(this.timer.equals(secondTimer)){
	    	  System.out.println("Project Manager is entering the afternoon Executive Meeting");
		 }
		 else if(this.timer.equals(lunchTimer)){
	    	  System.out.println("Project Manager is heading to lunch");

		 }
		 else{
			 System.out.println("Project Mangaer leaving for the day at 5:00pm!");
			 this.timer.cancel();
		 }
		 
		 try {
				PM.sleep(600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		 if(this.timer.equals(firstTimer)){
	          System.out.println("Project Manager is leaving the morning Executive Meeting");
		 }
		 else if(this.timer.equals(secondTimer)){
	    	  System.out.println("Project Manager is leaving the afternoon Executive Meeting");
		 }
		 else if(this.timer.equals(lunchTimer)){
	          System.out.println("Project Manager is finished with lunch");
		 }
		 this.timer.cancel();
		 
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



