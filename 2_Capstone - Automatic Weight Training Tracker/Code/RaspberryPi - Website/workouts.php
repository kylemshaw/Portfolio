<!-- 
******************************
workouts.php
Authors: Mark Rumpel & Kyle Shaw
Winter 2015
Version 1.6

Adapted upon example code provided by W3Schools

this page will displays a summary of the details for each workout to the user
this page will query the database using the data stored in the cookies to gather workout data
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
<center><a id="toplinks" href="logout.php">logout</a></center>


<h2>Welcome!</h2>
<h3>View Details for Past Workouts</h3>

<form method="post" action="chooseworkout.php">
        <p><input type="text" name="wochoice" value="" placeholder="Enter Workout Choice"></p>
        <p class="submit"><input type="submit" name="commit" value="Enter"></p>
      </form>



<?php

//checks to ensure a user is logged in, if not redirects to login page
//for security reasons
if(!(isset($_COOKIE['usernum']))){
header( "Location: index.php" );
}
 
//initializing and setting up the info about the database and storage variables
$servername = "localhost";
$username = "root";
$password = "gymtron";
$dbname = "Gymtron_DB";
$userid = $_COOKIE['usernum'];
$count = "0";
$worknum = "";
$totsets = "";
$workdate = "";

if(isset($_COOKIE['usernum'])){
	//echo "cookie set";
	//echo "welcome back " . $_COOKIE['usernum'];
}else {echo "cookie not set";}

//echo "UserID= " . $userid;
//echo "<br>";

//create connection
$conn = mysqli_connect($servername, $username, $password);

//check connection, display error if not connected properly
if (!$conn) {
	die("Connection Failed: " . mysqli_connect_error());
}

//telling sql which db to use
$sqlusedb = "USE Gymtron_DB";
if (mysqli_query($conn, $sqlusedb)) {
	//echo "Using Gymtron_DB";
} else {
	echo "Not using Gymtron_DB";
}

//find the largest workout number for display purposes
$sqlmax = "SELECT MAX(Workouts) as WONum FROM GTUser_Data WHERE User_ID='" . $userid . "'";
$resultmax = mysqli_query($conn, $sqlmax);
$row2 = mysqli_fetch_assoc($resultmax);
$worknum = $row2["WONum"];
//echo "MaxWO = " . $worknum;


echo "<h3>Past Workouts</h3>";

//displaying the workout, the number of sets in that workout to the user in a table
$count = $worknum;
echo "<table>";
echo "<tr><th>Workout</th><th>Sets</th><th>Date</th></tr>";
//counts down through each set
while($count > 0){
	echo "<tr>";
	//finds the maximum set number with an sql query and storess the result
	$sqlsets = "SELECT MAX(Sets) as Totsets FROM GTUser_Data WHERE Workouts='" . $count . "' AND User_ID='" . $userid . "'";
	$resultsets = mysqli_query($conn, $sqlsets);
	$row3 = mysqli_fetch_assoc($resultsets);
	$totsets = $row3["Totsets"];

	//finds the date of the first set of the given user and workout and stores the result
	$sqldate = "SELECT WOdate FROM GTUser_Data WHERE User_ID='" . $userid . "' AND Sets='1' AND Workouts='" . $count . "'";
	$resultdate = mysqli_query($conn, $sqldate);
	$row4 = mysqli_fetch_assoc($resultdate);
	$workdate = $row4["WOdate"];

	//prints the workout, number of sets in the workout, and the date of each workout
	echo "<td>" . $count . "</td><td>" . $totsets ."</td><td>" . $workdate . "</td>";
	$count = $count - 1;
	echo "</tr>";
}
//closes connection to database
mysqli_close($conn);

?>

</table>
</body>
</html>

