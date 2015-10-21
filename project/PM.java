class PM extends Thread {
  public long startTime;
  public long endTime;
  public PM(String name) {
    super(name);}
  
 
  public void setTime(){
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



