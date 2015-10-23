class Main {
  public static void main(String[] args) {
    ConferenceRoom cr = new ConferenceRoom();

    //PM pm = new PM("The Boss", cr);
    
    //Teams 
    Team t1 = new Team();
    Team t2 = new Team();
    Team t3 = new Team();

    //Team leads
    TeamLead tl1 = new TeamLead("TL1 Joe", t1);
    t1.setLead(tl1);
    TeamLead tl2 = new TeamLead("TL2 Larry", t2);
    t2.setLead(tl2);
    TeamLead tl3 = new TeamLead("TL3 Bob", t3);
    t3.setLead(tl3);

    //Team 1
    Developer d11 = new Developer("D11 Jake", t1);
    Developer d12 = new Developer("D12 Jane", t1);
    Developer d13 = new Developer("D13 Jill", t1);
    Developer[] team1 = {d11, d12, d13};

    t1.setDevelopers(team1);

    //Team 2
    Developer d21 = new Developer("D21 Lily", t2);
    Developer d22 = new Developer("D22 Lance", t2);
    Developer d23 = new Developer("D23 Laurance", t2);
    Developer[] team2 = {d21, d22, d23};

    t2.setDevelopers(team2);

    //Team 3
    Developer d31 = new Developer("D31 Bill", t3);
    Developer d32 = new Developer("D32 Baily", t3);
    Developer d33 = new Developer("D33 Biff", t3);
    Developer[] team3 = {d31, d32, d33};

    t3.setDevelopers(team3);
    
    //Fire off the team leads 
    tl1.start();
    tl2.start();
    tl3.start();
    
    //Fire off all devs on all teams
    for (int i = 0; i < team1.length; i++) {
      team1[i].start();
      team2[i].start(); 
      team3[i].start();
    }

  }
}
