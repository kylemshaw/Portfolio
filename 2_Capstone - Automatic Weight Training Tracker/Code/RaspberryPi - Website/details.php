<!-- 
******************************
details.php
Authors: Mark Rumpel & Kyle Shaw
Winter 2015
Version 1.6

Adapted upon example code provided by W3Schools

this page will displays the details of a workout to the user
this page will query the database using the data stored in the cookies 
and display the results back to the user in a table

******************************
-->

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="mystyle.css">
</head>

<body>
<h1>Gymtron</h1>
<center><a id="toplinks" href="workouts.php">Back to Workouts</a></center>
<br/>
<center><a id="toplinks" href="logout.php">logout</a></center>
<br/>


<?php
//checks to ensure a user is logged in, otherwise redirects to the login page for security reasons
if(!(isset($_COOKIE['usernum']))){
header( "Location: index.php" );
}

//gets data for variables from the cookies  
//stores the workout # the user choose to see details for in the workouts page
$wchoice = $_COOKIE['wochoice'];
//stores the users gymtron id from login page
$userid = $_COOKIE['usernum'];

//echo "Userid = " . $userid;
//echo "WOChoice = " . $wchoice;

echo "<h2>User " . $userid . "'s details for workout " . $wchoice . ": </h2>";

//database connection info contained in variables
$servername = "localhost";
$username = "root";
$password = "gymtron";
$dbname = "Gymtron_DB";

//create connection
$conn = mysqli_connect($servername, $username, $password);

//check connection and output error message if unsuccessful
if (!$conn) {
	die("Connection Failed: " . mysqli_connect_error());
}

//echo "Connected Successfully";

//telling sql which db to use
$sqlusedb = "USE Gymtron_DB";
if (mysqli_query($conn, $sqlusedb)) {
	//echo "Using Gymtron_DB";
} else {
	echo "Not using Gymtron_DB";
}

//queries db for all columns for specific user and workout #
//while loop displays data on page in tabular form
echo "<table>";
//query the db and take all records with matching user id and workout number
$sql = "SELECT * from GTUser_Data WHERE User_ID='" . $userid . "' AND Workouts='" . $wchoice . "'";
//result of query stored
$result = mysqli_query($conn, $sql);
//if records found, display the data contained in $result
if (mysqli_num_rows($result) > 0) {
	//setting up the table header
	echo "<tr><th>User ID</th><th>Workout</th><th>Set</th><th>Reps</th><th>Weight</th><th>Set Time</th><th>Date</th></tr>";
	//output data of each row by stepping through the array row by row
	while($row = mysqli_fetch_assoc($result)) {
		echo "<tr>";
		echo "<td>" . $row["User_ID"]. "</td><td>" . $row["Workouts"]. "</td><td>" . $row["Sets"]. "</td><td>" . $row["Reps"]. "</td><td>" . $row["Weight"]. "</td><td>" . $row["WOtime"]. "</td><td>" . $row["WOdate"]. "</td>";		
		echo "</tr>";	
}
} else {
	echo "0 Results";
}

//close connection to db
mysqli_close($conn);

?>

</table>
</body>
</html>

