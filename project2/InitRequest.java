import akka.actor.*;

class InitRequest {

  ActorRef secStation;
  ActorRef bagScanner;
  ActorRef bodyScanner;
  ActorRef jail;

  public InitRequest(ActorRef secStation, ActorRef bagScanner, ActorRef bodyScanner, ActorRef jail) {
    this.secStation = secStation;
    this.bagScanner = bagScanner;
    this.bodyScanner = bodyScanner; 
    this.jail = jail;
  }
}
