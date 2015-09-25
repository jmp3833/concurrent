class Bridge {
  Troll t;

  public Bridge(Troll t) {
    this.t = t; 
  }
  public void enterBridge() {
    t.waitForApproval();
  }

  public void leaveBridge() {
    t.endExecution();
  }
}
