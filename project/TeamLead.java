import java.util.Random;

class TeamLead extends Developer {

    public TeamLead(String name, Team t) {
        super(name, t);

    }

    public synchronized Boolean askQuestion() {
    	System.out.println("Asking Team Lead a question");
        Random rand = new Random();
	    Boolean answer = rand.nextInt(100) < 50;
	    if(answer){
	    	System.out.println("Team Lead was able to answer the question!");
	    }
	    else{
	    	System.out.println("Team Lead was not able to answer the question.");
	    }
	    
	    return answer;
    }

    public void run() {

    }
}
