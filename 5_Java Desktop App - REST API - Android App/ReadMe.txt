//------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Authors: Adolphus Cox, Xiaoyan Deng, Snow Tran, Kyle Shaw
//		 Description: This document outlines how to run each of the
//									projects that we have submitted.    
//------------------------------------------------------------------

General System Requirements:
 - Running instance of MariaDB/mySQL server configured with user 
   that has full database permissions
 - phpMyAdmin for SQL server management
 - intelliJ installation
 - Android Studio with Android Emulator (Nexus 6, API 30)
 - JBoss/Wildfly server set to run on  localhost:8080 and configured to 
   allow connections from localhost (for REST service)
 - A Second JBoss/Wildfly server set to run on  localhost:9080 (for webform)

------------------------------------
Database Install
------------------------------------
1) Open phpMyAdmin
2) Drop any existing database named "travelexperts".
2) On the right click new (database)
3) Click import from the top menu
4) Choose the travelexperts.sql file included with
	 the submission
5) Click go and the database will be added.


------------------------------------
PROJ207_Group5_Module6_JavaFX
------------------------------------
1) Open project with intelliJ
2) Navigate to reosurces/config/connection.properties and open
3) Edit database url and login information to match your system
4) Build and run the app
4) Example Agent login:
		Username: BJones
		Password: password


------------------------------------
PROJ207_Group5_Module7_REST
------------------------------------
1) Open in intelliJ
2) After indexes have been built click Add configuration... (top right)
3) Choose JBoss/Wildfly -> Local and make sure the port is set to 8080
4) Under Server configuration tab scroll to the bottom and see
	 Warning: No artifacts marked for deployment
5) Fix this warning by clicking Fix and choosing "TravelExperts: war exploded".
	 IMPORTANT: Do not choose the TravelExperts.war file as the agent images are not
						  added to it and the server will not be able to find the images for the 
						  getagentimg method. 
6) Open the main.java.com.example.travelexperts/ConnectionResource.java and 
	 adjust database url and login credentials to match your local system
7) Run the project and the API should now be available at:
   http://localhost:8080/TravelExpertsREST-1.0-SNAPSHOT/api/<resource>/<method> 


------------------------------------
PROJ207_Group5_Module7_WebClient
------------------------------------
1) Ensure REST API is running
2) Open project in intelliJ and configure to run on second Wildfly server
3) Run project and navigate to package.html
4) Use the form to access the packages table through the REST API


------------------------------------
PROJ207_Group5_Module8_Android
------------------------------------
1) Ensure the REST API is running on a local server as described above
2) Open the project in Android Studio.
3) Ensure your emulator is set as described in system requirements
4) Build and run the app in the emulator.
5) Example customer login:
		Email: LEnison@gmail.com
		Password: password