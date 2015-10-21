class PM extends Thread {
	private ConferenceRoom cr;

  public PM(String name, ConferenceRoom c) {
	  super(name);
	cr = c;
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



