import akka.actor.*;

class CollectionActor extends UntypedActor {

  private int numProcessed = 0;

  public void addProcessed (){
    this.numProcessed++;
  }

  public void onReceive(Object message) {
    
    int numFiles = 0;

    if(message instanceof FileCount) {
      FileCount fc = (FileCount) message;
      numFiles = fc.numFiles;
    }     

    else {
      //Print off the results for a file
      Found result = (Found) message; 
      System.out.println("Results for filename " + result.fileName + ": ");

      for(int i = 0; i < result.resultsFound.size(); i++) {
        System.out.println(result.resultsFound.get(i)); 
      }
      this.addProcessed();
    }
    
    if (this.numProcessed == numFiles) {
      Actors.registry().shutdownAll();
    }
  }
}
