public class Meeting implements Runnable{
    //The printable name of this meeting
    private String name;

    public Meeting(String name) {
        this.name = name;
    }

    public void run() {
        System.out.println("Meeting \'" + name + "\' has started");
        try {
            //Sleep 15 minutes
            Thread.sleep(150);
        }
        catch (InterruptedException e) {
        }
        System.out.println("Meeting \'" + name + "\' has ended");
    }
}

