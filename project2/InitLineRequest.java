import akka.actor.*;

class InitLineRequest {

  ActorRef secStation;
  ActorRef bagScanner;
  ActorRef bodyScanner;

  public InitLineRequest(ActorRef secStation, ActorRef bagScanner, ActorRef bodyScanner) {
    this.secStation = secStation;
    this.bagScanner = bagScanner;
    this.bodyScanner = bodyScanner; 
  }
}
