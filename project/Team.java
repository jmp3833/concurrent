import java.util.concurrent.CyclicBarrier;

class Team {
    private int teamNumber;
    private Developer[] developers;
    private TeamLead lead;
    private CyclicBarrier standupBarrier;
    private Meeting standup;

    public Team(Developer[] developers, TeamLead lead) {
        this.developers = developers;
        this.lead = lead;
        this.standup = new Meeting("Team " + teamNumber);
        this.standupBarrier = new CyclicBarrier(4, standup);

        for(int i = 0; i < developers.length; i++) {
          developers[i].setStandupBarrier(this.standupBarrier);
        }
    }

}
