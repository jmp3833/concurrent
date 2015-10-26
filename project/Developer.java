import java.util.Random;

class Developer extends Thread implements Runnable {

    protected Random rng;
    protected Team team;
    private int max;

    public Developer(String name, Team t, int max) {
        super(name);
        this.team = t;
        this.max = max;
        rng = new Random();
    }

    /*
     * Conduct a standard day for the developer
     */
    public void run() {
        try {
            arrive();

            //Wait for the daily developer standup
            team.getStandupBarrier().await();

            working(2400);
            
            System.out.println("Developer" + this.getName() + 
                "Goes to lunch at " + team.getLead().getPM().getClockTime());

            Thread.sleep(600);
            
            //Go to final meeting somewhere between 4:00 and 4:15
            int workInterval = rng.nextInt((4950 - 4800) + 1) + 4800;
            working(workInterval);

            System.out.println("Developer" + this.getName() + 
                "Goes to afternoon meeting at " + team.getLead().getPM().getClockTime());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void working(int endTime) throws InterruptedException {
      int min = 1;
      while(team.getLead().getPM().getTime() < endTime) {
        //Do work, asking questions when necessary until lunch
        int randomQuestionChance = rng.nextInt((max - min) + 1) + min;
        if(randomQuestionChance == 1) {
          System.out.println("Developer " + this.getName() + " has a question!");
          team.getLead().askQuestion();
        }
      }
    }

    protected void arrive() throws InterruptedException {
        //Enter the office anywhere between 8 and 8:30 AM
        int arrivalMillis = rng.nextInt(290);
        Thread.sleep(arrivalMillis);

        String arrivalTime = (arrivalMillis / 10 < 10) ?
                ("0" + Integer.toString(arrivalMillis / 10)) : Integer.toString(arrivalMillis / 10);

        System.out.println("Developer " + super.getName() + " enters the office at 8:"
                + arrivalTime);
    }
}
