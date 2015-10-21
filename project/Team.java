import java.util.concurrent.CyclicBarrier;

class Team {
    private Developer[] developers;
    private TeamLead lead;
    private CyclicBarrier standupBarrior;
    private TeamStandup standup;

    public Team(Developer[] developers, TeamLead lead) {
        this.developers = developers;
        this.lead = lead;
        this.standup = new TeamStandup();
        this.standupBarrior = new CyclicBarrier(4, standup);
    }

}

class TeamStandup implements Runnable {
    public void run() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
        }
    }
}