import java.util.List;

/*
 * Object message to display results found by the collection actor
 */
class Found {

  //Everything we need to find the results of a grep 
  String fileName;
  List<String> resultsFound;

  public Found (String fileName, List<String> resultsFound) {
    this.fileName = fileName;  
    this.resultsFound = resultsFound;
  }
}
