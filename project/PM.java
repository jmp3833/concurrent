import java.sql.Time;
import java.util.Calendar;
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
  }
  
  class meetingTask extends TimerTask {
	 Timer timer;
	 public meetingTask(Timer timer){
		 this.timer = timer;
	 }
	 @Override
	  public void run() {
		 //Before the sleep is called message
		 if(this.timer.equals(firstTimer)){
	    	  System.out.println("Project Manager is entering the morning Executive Meeting at " + getClockTime());
		 }
		 else if(this.timer.equals(secondTimer)){
	    	  System.out.println("Project Manager is entering the afternoon Executive Meeting at " + getClockTime());
		 }
		 else if(this.timer.equals(lunchTimer)){
	    	  System.out.println("Project Manager is heading to lunch at " + getClockTime());

		 }
		 else{
			 System.out.println("Project Mangaer leaving for the day at 5:00pm!");
			 this.timer.cancel();
		 }
		 
		 //Sleep for 1 hour
		 try {
				PM.sleep(600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		 //After the sleep is called
		 if(this.timer.equals(firstTimer)){
	          System.out.println("Project Manager is leaving the morning Executive Meeting at " + getClockTime());
		 }
		 else if(this.timer.equals(secondTimer)){
	    	  System.out.println("Project Manager is leaving the afternoon Executive Meeting at " + getClockTime());
		 }
		 else if(this.timer.equals(lunchTimer)){
	          System.out.println("Project Manager is finished with lunch at " + getClockTime());
		 }
		 this.timer.cancel();
		 
	  }
  }
   
  //Returns current system time - start time giving time in ms since 8am.
  public long getTime(){
      return(System.currentTimeMillis() - startTime);
     
  }
  
  //Sleeps pm for 10 minutes to answer question.
  public synchronized void askPMQuestion(){
	  System.out.println("Asking the Project Manager a question at " + getClockTime());
	  try {
		  System.out.println("Project Manager is thinking...");
		PM.sleep(100);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  System.out.println("Project Manager answered question at " + getClockTime());
  }
  
  public void run(){
	  this.startTime = System.currentTimeMillis();
	  this.endTime = this.startTime + 5400; 
	  System.out.println("Project Manager enters the office at " + getClockTime() );
	  firstTimer = new Timer();
	  secondTimer = new Timer();
	  lunchTimer = new Timer();
	  leaveTimer = new Timer();
	  //First meeting at 10
      firstTimer.schedule(new meetingTask(firstTimer), 1200);
      //Lunch at 12
      lunchTimer.schedule(new meetingTask(lunchTimer), 2400);
      //Second meeting at 2
      secondTimer.schedule(new meetingTask(secondTimer), 3600);
      //
      leaveTimer.schedule(new meetingTask(leaveTimer), 5400);
  }
    //give a string of the current time
    public String getClockTime() {
        //Time since 8:00AM in milliseconds (10ms = 1min)
        long milliseconds = getTime();
        long minutes = milliseconds/10;
        long printMin = minutes%60;
        long hours = (minutes/60) + 7;

        String ret = String.format("%1$s:%2$02d", (hours%12) + 1, printMin);

        return ret;
    }

}



