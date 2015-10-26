#Project run instructions:

//Compile all project files
javac Main.java

//Fires off all threads and executes a 'day in the life' of the shop
java Main

#Configurable variables (found in Main.java):
* questionFrequency - scaling value to determine how frequently a developer will ask questions. Because this is bound to 
  processor ticks in a while loop, the factor is scaled up by a value of 10,000,000. As the value increases questions will be
  asked less frequently, as it acts as a larger seed for a random number generator. 
