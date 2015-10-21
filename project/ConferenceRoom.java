class ConferenceRoom {

  public boolean isFree;
  public ConferenceRoom(boolean isFree) {
    this.isFree = isFree; 
  }

  public synchronized void holdMeeting() {
	  this.isFree = false;
  }

  public synchronized void endMeeting() {
	  this.isFree = true;
  }
}
