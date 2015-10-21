import java.util.concurrent.CyclicBarrier;

class Team {
    private int teamNumber;
    private Developer[] developers;
    private TeamLead lead;
    private CyclicBarrier standupBarrior;
    private Meeting standup;

    public Team(Developer[] developers, TeamLead lead) {
        this.developers = developers;
        this.lead = lead;
        this.standup = new Meeting("Team " + teamNumber);
        this.standupBarrior = new CyclicBarrier(4, standup);
    }

    public TeamLead getLead() {
        return lead;
    }

}