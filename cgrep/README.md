###Execution Instructions:

```
//Compile all relevant java files
javac *.java 

//Run program
java CGrep "<regex>" infile1 infile2 [...infile n]
```

This program can be executed on a limitless number of files and will print the name 
of the file that is being processed in addition to any results of the grep that it finds

NOTE: The regular expression passed in on the command line must be wrapped in quotes
in order to ensure that it works appropriately. 

File Structure: 

CGrep.java - take in command line arguments, construct executor objects and collect results
GrepTask.java - Callable object to compute the results of grep on a single file. 
