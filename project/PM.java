class PM extends Thread {

  public PM(String name) {
    super(name);  }
 
  public static void askPMQuestion(){
	  try {
		PM.sleep(100);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

}



