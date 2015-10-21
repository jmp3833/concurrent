class PM extends Thread {
  public long startTime;
  public long endTime;
  private ConferenceRoom cr;

  public PM(String name, ConferenceRoom c) {
	  super(name);
	  cr = c;
	  this.startTime = System.currentTimeMillis();
	  this.endTime = this.endTime + 5400;
  }
  
   
  public long getTime(){
      if(this.isAlive()) return(System.currentTimeMillis() - startTime);
      else return endTime;
  }
  public static void askPMQuestion(){
	  try {
		PM.sleep(100);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

}



