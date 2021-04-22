# Sweater REST API

This server contains a set of endpoints for processing, 
writing data to the database and providing them to the client. 
Check out their descriptions 
on the swagger page when starting the server 
localhost:8080/swagger-ui.html<br/>
Or visit an already running server at 
[Heroku](https://csv-controller.herokuapp.com/swagger-ui.html)<br/>
This server contains 3 controllers, while only one is covered 
with a swagger - this is the company-controller.

### Let's start our server.

+ Make sure you have [Maven installed](https://maven.apache.org/download.cgi).


+ Add Maven location to the PATH Environment Variable.


+ Download the project **zip** file from git and unzip it.


+ Launch Command Line and navigate to the main directory
  of the unpacked project.<br/><br/>
  Create an executable **jar** file with the command:
  
  `mvn package`<br/>
  
  This command will create a folder named **target**, 
  which will contain our jar file called **sweater-1.0-SNAPSHOT.jar**.
  
  
+ In the Command Line, go above to the **target** directory.<br/><br/>
  Start the server with the command:
  
  `java -jar sweater-1.0-SNAPSHOT.jar`<br/>

  To stop the execution, in the console where you ran the command to start the service,
  press `Ctrl` + `C` on your keyboard.
  

