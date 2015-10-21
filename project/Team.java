import java.util.concurrent.CyclicBarrier;

class Team {
    private Developer[] developers;
    private TeamLead lead;
    private CyclicBarrier standupBarrior;
    private Meeting standup;

    public Team(Developer[] developers, TeamLead lead) {
        this.developers = developers;
        this.lead = lead;
        this.standup = new Meeting();
        this.standupBarrior = new CyclicBarrier(4, standup);
    }

}