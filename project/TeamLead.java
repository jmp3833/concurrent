import java.util.Random;

class TeamLead extends Thread {

    public TeamLead(String name) {
        super(name);
    }

    public synchronized Boolean askQuestion() {
        Random rand = new Random();
	    Boolean answer = rand.nextInt(100) < 50;
	    return answer;
    }
}
