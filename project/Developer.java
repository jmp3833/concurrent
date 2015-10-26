import java.util.Random;

class Developer extends Thread implements Runnable {

    protected Random rng;
    protected Team team;

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
            arrive();

            //Wait for the daily developer standup
            team.getStandupBarrier().await();
          
            //Do work, asking questions when necessary
            team.getLead().askQuestion();

            //Go to final meeting somewhere between 4:00 and 4:15

        } catch (Exception e) {
            e.printStackTrace();
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
