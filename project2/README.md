###Authors
-Justin Peterson
-Ethan Jurman
-Kevin Mulligan

###Configurable Constants
All configurable parameters are specified as final constants at the top
of Main.java. The configureable parameters are as follows: 

NUM_PASSENGERS: The number of passengers to enter the TSA line on Initalization
MAX_BAGS: Max num of bags a passenger could have. Any passenger will have MINBAGS to MAXBAGS.
NUM_LINES: The number of TSA lines that are in use

###Compilation and execution
All source code is in the source directory. When in the source directory
Main.java is the root executable file. To run eith execute the running script
at the root of the project (*.cmd for windows or *.sh for unix/osx) or do the following:

cd source
javac Main.java
java Main

NOTE: Make sure to have the appropriate Akka 1.2 jars on your path.



