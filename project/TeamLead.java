import java.util.Random;

class TeamLead extends Developer {
	private PM pm;
    public TeamLead(String name, Team t, PM thePm) {
        super(name, t);
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
}
