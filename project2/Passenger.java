import java.util.ArrayList;
import java.util.List;

public class Passenger {
    String name;
    List<Bag> bags;

    public Passenger(String n, int numBags) {
        this.name = n;
        this.bags = new ArrayList<Bag>();
        for(int i = 0; i < numBags; i++) {
            this.bags.add(new Bag(this));
        }
    }

    public List<Bag> getBags() {
        return bags;
    }
}
