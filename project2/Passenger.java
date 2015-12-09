import java.util.ArrayList;
import java.util.List;

public class Passenger {
    String name;
    List<Bag> bags;
    int numBags;

    public Passenger(String n, int numBags) {
        this.name = n;
        this.numBags = numBags;

        this.bags = new ArrayList<Bag>();
        for(int i = 0; i < numBags; i++) {
            this.bags.add(new Bag(this));
        }
    }

    public List<Bag> getBags() {
        return bags;
    }

    public Bag getBagAtIndex(int index) {
      return this.bags.get(index);
    }

    public int getNumBags() {
      return this.numBags; 
    }
}
