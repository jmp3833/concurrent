class WoolieTest {
  public static void main(String[] args) {
    Bridge trollBridge = new Bridge(); 
    Woolie jack = new Woolie("Jack Bauer", 5, "Merctan", trollBridge);
    Woolie donald = new Woolie("Donald Trump", 8, "Sicstine", trollBridge);

    jack.start();
    donald.start();
  }
}
