# Spring REST API

##Let's start our server.

+ Make sure you have [Maven installed](https://maven.apache.org/download.cgi).


+ Add Maven location to the PATH Environment Variable.


+ Download the project **zip** file and unzip it.


+ Launch Command Line and navigate to the main directory
  of the unpacked project.<br/><br/>
  Create an executable **jar** file with the command:
  >mvn package

  This command will create a folder named **target**, 
  which will contain our jar file called **sweater-1.0-SNAPSHOT.jar**.
  
  
  
+ In the Command Line, go above to the **target** directory.<br/><br/>
  Start the server with the command:
  >java -jar sweater-1.0-SNAPSHOT.jar

  To stop the execution, in the console where you ran the command to start the service, 
  press Ctrl + C on your keyboard.