import java.util.Random;

class TeamLead extends Developer {
	private PM pm;
    private ConferenceRoom conferenceRoom;

    public TeamLead(String name, Team t, PM thePm, ConferenceRoom cr) {
        super(name, t);
        this.conferenceRoom = cr;
        this.pm = thePm;
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


            //Go to final meeting somewhere between 4:00 and 4:15


        } catch (Exception e) {
        }

    }
}
