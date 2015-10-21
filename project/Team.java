import java.util.concurrent.CyclicBarrier;

class Team {
    private int teamNumber;
    private Developer[] developers;
    private TeamLead lead;
    private CyclicBarrier standupBarrier;
    private Meeting standup;

    public Team() {
        this.standup = new Meeting("Team " + teamNumber);
        this.standupBarrier = new CyclicBarrier(4, standup);
    }

    public TeamLead getLead() {
        return lead;
    }
    
    /*
     * Assign developers to a team once they've been instantiated.
     */
    public void setDevelopers(Developer[] developers) {
      this.developers = developers; 
    }
    
    /*
     * Assign team leads to a team once they've been instantiated.
     */
    public void setLead(TeamLead lead) {
      this.developers = developers; 
    }

    public CyclicBarrier getStandupBarrier() {
      return this.standupBarrier; 
    }

}
