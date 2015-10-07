class Banker {
  private final int nUnits;
  public Banker(int nUnits) {
    this.nUnits = nUnits; 
  }

  /*
   * The current thread attempts to register a claim for n units of
   * the given resource
   */
  public void setClaim(int nUnits) {
     
  } 
  
  /*
   * The current thread requests nUnits more resources.
   */
  public boolean request(int nUnits) {
    return true; 
  }
  
  /*
   * The current thread releases nUnits resources
   */
  public void release() {
    
  }
  
  /*
   * Returns number of units allocated to the current thread
   */
  public int allocated() {
    return 0; 
  }
  
  /*
   * Returns number of units remaining in the current thread's claim
   */
  public int remaining() {
    return 0; 
  }
}
