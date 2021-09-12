/////////////////////////////////////////////////////////////
//
//		Project: TravelExperts Database Manager
//		Author: Kyle Shaw
//		Date: August 31, 2021
//		
//		Description:
//		This project was originally built as part of a group  
//		project in the Object Orientated Software Developement
//		Certificate program at the Southern Albera Institute
//		of Technology (SAIT). I have since cleaned it up and have 
//		written ~90% of the code
//		
//		The app allows travel agents to make changes to three
//		different but related tables stored in a database 
//		running in MS SQL server.
//
////////////////////////////////////////////////////////////

1. TravelExperts_DB_Creation_Script.sql

		Download MS SQL Server Management Studio and run this
		script to create the database that is managed by the
		app.
		
		This script was provided by the course instructor and 
		contains some small modifications made by our group.

2. TravelExperts_DB_Manager

		After the database has been created open this shortcut 
		to run the application exe file.

3. TravelExperts_W4_DesktopApp

		This folder contains the visual studio solution file 
		for the app, providing access to source code. You can 
		also run the app from visual studio using the sln file.
		
Improvements To Do List
- Fix missing try/catch error handling in frmMain
  (keep try/catch here or can I clean up frmMain code and move them 
	to manager class and propagate errors through to the presentation
	layer somehow?)
- Clean up forms and make consistent buttons, text formatting
- Review DTOs (do they add anything?)
- Look into dpendancy injection to allow the creation of mock database
  to test Manager classes
- add unit testing

	
