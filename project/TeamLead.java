import java.util.Random;

class TeamLead extends Developer {

    public TeamLead(String name, Team t) {
        super(name, t);

    }

    public synchronized Boolean askQuestion() {
        Random rand = new Random();
	    Boolean answer = rand.nextInt(100) < 50;
	    return answer;
    }

    public void run() {

    }
}
