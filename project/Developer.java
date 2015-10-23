import java.util.Random;

class Developer extends Thread implements Runnable {

    private Random rng;
    private Team team;

    public Developer(String name, Team t) {
        super(name);
        this.team = t;
        rng = new Random();
    }

    /*
     * Conduct a standard day for the developer
     */
    public void run() {
        try {
            //Enter the office anywhere between 8 and 8:30 AM
            int arrivalMillis = rng.nextInt(290);
            Thread.sleep(arrivalMillis);

            String arrivalTime = (arrivalMillis / 10 < 10) ?
                    ("0" + Integer.toString(arrivalMillis / 10)) : Integer.toString(arrivalMillis / 10);

            System.out.println("Developer " + super.getName() + "enters the office at 8:"
                    + arrivalTime);

            //Wait for the daily developer standup
            team.getStandupBarrier().await();
          
            //Keep going once standup is complete
            team.getLead().askQuestion();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
