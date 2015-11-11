###Authors
-Justin Peterson
-Ethan Jurman
-Kevin Mulligan

###Execution Instructions:

```
//Compile all relevant java files
javac *.java 

//Run program
java CGrep "<regex>" infile1 infile2 [...infile n]

//Example Execution:
java CGrep "/^[a-z0-9-]+$/" RomeoAndJuliet.txt Hamlet.txt
```

This program can be executed on a limitless number of files and will print the name 
of the file that is being processed in addition to any results of the grep that it finds

NOTE: The regular expression passed in on the command line must be wrapped in quotes
in order to ensure that it works appropriately. 
