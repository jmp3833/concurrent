import akka.actor.*;
import java.util.regex.*;

class Configure {
    
    String fileName;
    Pattern reg;
    ActorRef collection;  

    public Configure(String fileName, ActorRef collection, Pattern reg) {
        this.fileName = fileName;
        this.collection = collection;    
        this.reg = reg;
    }
}
