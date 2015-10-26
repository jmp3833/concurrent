import java.util.Random;

class TeamLead extends Developer {
	private PM pm;
    private ConferenceRoom conferenceRoom;

    public TeamLead(String name, Team t, int max, PM thePm, ConferenceRoom cr) {
        super(name, t, max);
        this.conferenceRoom = cr;
        this.pm = thePm;
    }

    public PM getPM() {
      return this.pm; 
    }

    public synchronized void askQuestion() {
    	System.out.println("Asking Team Lead a question");
        Random rand = new Random();
	    Boolean answer = rand.nextInt(100) < 50;
	    if(answer){
	    	System.out.println("Team Lead was able to answer the question!");
	    }
	    else{
	    	System.out.println("Team Lead was not able to answer the question.");
	    	this.pm.askPMQuestion();
	    }
	    
    }

    public void run() {
        try {
            //arrive for the day
            super.arrive();

            //go to the TeamLead standup meeting
            pm.getStandupBarrier().await();

            //Hold team standup meeting
            conferenceRoom.holdMeeting();
            team.getStandupBarrier().await();
            conferenceRoom.endMeeting();

            //Do work (asking questions along the way
            int preLunchInterval = rng.nextInt((2550 - 2400) + 1) + 2400;
            super.working(preLunchInterval, true);
            
            System.out.println("Team Lead " + this.getName() + 
                "is heading to lunch at " + pm.getClockTime());

            
            int lunchDuration = rng.nextInt((600 - 300) + 1) + 300;
            Thread.sleep(lunchDuration);
            
            System.out.println("Team Lead " + this.getName() + 
                "is leaving lunch at " + pm.getClockTime());

            int workInterval = rng.nextInt((4950 - 4800) + 1) + 4800;
            working(workInterval, true);
            
            //Go to final meeting somewhere between 4:00 and 4:15
            System.out.println("Team Lead " + this.getName() + 
                " is heading to the afternoon meeting at " + pm.getClockTime());
            pm.getAfternoonMeetingBarrier().await();
            
            //After the meeting, get ready to go home and end the day
            int dillyDallyBeforeHome = rng.nextInt((150 - 10) + 1) + 10;
            Thread.sleep(dillyDallyBeforeHome);

            //Leave after afternoon meeting is complete
            System.out.println("Team Lead " + this.getName() + 
                " is heading home at " + pm.getClockTime());

        } catch (Exception e) {
        }

    }
}
