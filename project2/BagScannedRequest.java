public class BagScannedRequest {
    Bag b;
    boolean passed;

    public BagScannedRequest(Bag b, boolean p) {
        this.b = b;
        passed = p;
        b.scanned(p);
    }
}
