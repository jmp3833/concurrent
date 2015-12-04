public class BagScannedRequest {
    Bag b;
    boolean passed;

    public BagScannedRequest(Bag b) {
        this.b = b;
        passed = false;
    }

    public void setPassed(boolean pass) {
        this.passed = pass;
    }
}
