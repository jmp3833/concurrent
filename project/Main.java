class Main {
  public static void main(String[] args) {
    ConferenceRoom cr = new ConferenceRoom();

    PM pm = new PM("The Boss", cr);

    //Team leads
    TeamLead tl1 = new TeamLead("TL1 Joe");
    TeamLead tl2 = new TeamLead("TL2 Larry");
    TeamLead tl3 = new TeamLead("TL3 Bob");

    //Team 1
    Developer d11 = new Developer("D11 Jake");
    Developer d12 = new Developer("D12 Jane");
    Developer d13 = new Developer("D13 Jill");
    Developer[] team1 = {d11, d12, d13};

    //Team 2
    Developer d21 = new Developer("D21 Lily");
    Developer d22 = new Developer("D22 Lance");
    Developer d23 = new Developer("D23 Laurance");
    Developer[] team2 = {d21, d22, d23};

    //Team 3
    Developer d31 = new Developer("D31 Bill");
    Developer d32 = new Developer("D32 Baily");
    Developer d33 = new Developer("D33 Biff");
    Developer[] team3 = {d31, d32, d33};

    Team t1 = new Team(team1, tl1);
    Team t2 = new Team(team2, tl2);
    Team t3 = new Team(team3, tl3);


  }
}
