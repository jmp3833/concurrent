import java.util.Random;
class TeamLead extends Thread {

  public String name;

  public TeamLead(String name) {
    this.name = name;
  }
  
  public synchronized Boolean askQuestion() {
	  Random rand = new Random();
	  Boolean answer = rand.nextInt(100) < 50;
	  return answer;
  }

}
