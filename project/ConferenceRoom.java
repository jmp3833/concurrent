class ConferenceRoom {

  public boolean isFree;
  public ConferenceRoom() {
    this.isFree = true;
  }

  public synchronized void holdMeeting() {
    while(this.isFree == false) {
      try {
        wait();
      } catch (InterruptedException e) {
      }
    }
	this.isFree = false;
  }

  public synchronized void endMeeting() {
	this.isFree = true;
    notifyAll();
  }
}
