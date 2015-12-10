import akka.actor.*;

class InitRequest {

  ActorRef secStation;
  ActorRef bagScanner;
  ActorRef bodyScanner;
  ActorRef jail;
  int numLines;

  public InitRequest(ActorRef secStation, ActorRef bagScanner, ActorRef bodyScanner, ActorRef jail, int numLines) {
    this.secStation = secStation;
    this.bagScanner = bagScanner;
    this.bodyScanner = bodyScanner; 
    this.jail = jail;
    this.numLines = numLines;
  }
}
