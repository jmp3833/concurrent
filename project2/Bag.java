public class Bag {
    private Passenger pass;
    private boolean scanCompleted;
    private boolean scanPassed;

    public Passenger getPassenger() {
        return pass;
    }
    public boolean getScanCompleted() {
        return scanCompleted;
    }
    public boolean getScanPassed() {
        return scanPassed;
    }

    public Bag(Passenger p) {
        this.pass = p;
        this.scanCompleted = false;
        this.scanPassed = false;
    }

    public void scanned(boolean p) {
        this.scanCompleted = true;
        this.scanPassed = p;
    }
}
