import java.util.Random;

public class FailureChance {
    private static double chance = 0.8;//chance of success
    private static Random rng = new Random();

    //Returns true ~80% of the time, false 20%
    public static boolean randomFailure() {
        Double d = rng.nextDouble();
        if(d <= chance) {
            return true;
        } else {
            return false;
        }
    }
}
