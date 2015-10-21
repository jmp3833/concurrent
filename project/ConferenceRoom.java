class ConferenceRoom {

    //Is this conference room currently in use
    public boolean isFree;

    public ConferenceRoom() {
        this.isFree = true;
    }

    //Indicate that someone wants to hold a meeting, waiting until no one else is using it
    public synchronized void holdMeeting() {
        while(this.isFree == false) {
            try {
                wait();
            } catch (InterruptedException e) {
        }
    }
    this.isFree = false;
    }

    //End the current meeting, and let everyone else know it is empty
    public synchronized void endMeeting() {
        this.isFree = true;
        notifyAll();
    }
}
