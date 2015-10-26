import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CyclicBarrier;

class PM extends Thread {
    public long startTime;
    public long endTime;

    private ConferenceRoom cr;

    private Timer firstTimer;
    private Timer secondTimer;
    private Timer lunchTimer;
    private Timer leaveTimer;
    private Timer finalTimer;

    private Meeting teamLeadStandup;
    private Meeting afternoonMeeting;

    private CyclicBarrier standupBarrier;
    private CyclicBarrier afternoonBarrier;

    private Queue<TeamLead> questionLine;

    public PM(String name, ConferenceRoom c) {
        super(name);
        cr = c;
        this.teamLeadStandup = new Meeting("Team Lead Standup");
        this.afternoonMeeting = new Meeting("Afternoon Meeting");

        this.standupBarrier = new CyclicBarrier(4, teamLeadStandup);
        this.afternoonBarrier = new CyclicBarrier(13, afternoonMeeting);

        this.questionLine = new LinkedList();
    }

    public CyclicBarrier getStandupBarrier() {
        return standupBarrier;
    }
     
    public CyclicBarrier getAfternoonMeetingBarrier() {
        return afternoonBarrier; 
    }

    class meetingTask extends TimerTask {
        Timer timer;
        int duration;

        public meetingTask(Timer timer, int duration) {
            this.timer = timer;
            this.duration = duration;
        }

        @Override
        public void run() {
            //Before the sleep is called message
            if (this.timer.equals(firstTimer)) {
                System.out.println("Project Manager is entering the morning Executive Meeting at " + getClockTime());
            } else if (this.timer.equals(secondTimer)) {
                System.out.println("Project Manager is entering the afternoon Executive Meeting at " + getClockTime());
            } else if (this.timer.equals(lunchTimer)) {
                System.out.println("Project Manager is heading to lunch at " + getClockTime());
            } else if (this.timer.equals(finalTimer)) {
                System.out.println("Project manager is entering status update meeting at " + getClockTime()); 
                try{
                  afternoonBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Project Mangaer leaving for the day at 5:00pm!");
                this.timer.cancel();
            }

            //Sleep for 1 hour
            try {
                PM.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //After the sleep is called
            if (this.timer.equals(firstTimer)) {
                System.out.println("Project Manager is leaving the morning Executive Meeting at " + getClockTime());
            } else if (this.timer.equals(secondTimer)) {
                System.out.println("Project Manager is leaving the afternoon Executive Meeting at " + getClockTime());
            } else if (this.timer.equals(lunchTimer)) {
                System.out.println("Project Manager is finished with lunch at " + getClockTime());
            }
            this.timer.cancel();

        }
    }

    //Returns current system time - start time giving time in ms since 8am.
    public long getTime() {
        return (System.currentTimeMillis() - startTime);

    }

    //Sleeps pm for 10 minutes to answer question.
    public synchronized void askPMQuestion(TeamLead tl) {
        System.out.println(tl.getName() + " is asking the Project Manager a question at " + getClockTime());
        questionLine.add(tl);
    }

    private void answerQuestion() {
        TeamLead tl = questionLine.remove();
        if(getTime() > 4800/*4:00pm*/) {
            System.out.println("Project Manager does not have time to answer " + tl.getName() + "'s question today");
            return;
        }
        try {
            System.out.println("Project Manager starts thinking about " + tl.getName() + "'s question at " + getClockTime());
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Project Manager answered " + tl.getName() + "'s question at " + getClockTime());
    }

    public void run() {
        try {
            this.startTime = System.currentTimeMillis();
            this.endTime = this.startTime + 5400;
            System.out.println("Project Manager enters the office at " + getClockTime());
            firstTimer = new Timer();
            secondTimer = new Timer();
            lunchTimer = new Timer();
            leaveTimer = new Timer();
            finalTimer = new Timer();

            //Standup with Team Leads
            cr.holdMeeting();
            standupBarrier.await();
            cr.endMeeting();

            //First meeting at 10
            firstTimer.schedule(new meetingTask(firstTimer, 600), 1200);
            //Lunch at 12
            lunchTimer.schedule(new meetingTask(lunchTimer, 600), 2400);
            //Second meeting at 2
            secondTimer.schedule(new meetingTask(secondTimer, 600), 3600);
            //Second meeting at 2
            finalTimer.schedule(new meetingTask(finalTimer, 150), 4500);
            leaveTimer.schedule(new meetingTask(leaveTimer, 600), 5400);

            //During the day, just answer questions as they come up
            while(getTime() < 4800 /*4:00pm*/) {
                if(!questionLine.isEmpty()) {
                    answerQuestion();
                }
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Finish (not) answering the rest of the questions
            while(!questionLine.isEmpty()) {
                answerQuestion();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //give a string of the current time
    public String getClockTime() {
        //Time since 8:00AM in milliseconds (10ms = 1min)
        long milliseconds = getTime();
        long minutes = milliseconds / 10;
        long printMin = minutes % 60;
        long hours = (minutes / 60) + 7;

        return String.format("%1$s:%2$02d", (hours % 12) + 1, printMin);
    }

}



