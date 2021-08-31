<?php
//******************************
//login.php
//Authors: Mark Rumpel & Kyle Shaw
//Winter 2015
//Version 1.6

//this page handles checking the users credentials with the database and redirecting once status is known
//credentials are stored in a cookie once verified as valid

//******************************

//storing the connection details for the database in variables
$servername = "localhost";
$username = "root";
$password = "gymtron";
$dbname = "Gymtron_DB";

//retrieves the entered id from the form on index.php
$userid = $_POST['login'];



//create connection
$conn = mysqli_connect($servername, $username, $password);

//check connection and error out if no connection
if (!$conn) {
	die("Connection Failed: " . mysqli_connect_error());
}

//tell sql which db to use
$sqlusedb = "USE Gymtron_DB";
if (mysqli_query($conn, $sqlusedb)) {
	echo "Using Gymtron_DB";
} else {
	echo "Not using Gymtron_DB";
}
echo "<br>";

//queries the db with the entered credentials
$sql = "SELECT * from GTUser_Data WHERE User_ID='" . $userid . "'";
$result = mysqli_query($conn, $sql);

//checking to see if credentials are contained in the db
//if nothing entered, return to login page
if (empty($_POST['login'])){
	echo "please input data";
	header( "Location: index.php" );

}else{
//if entered id is a user, set a cookie containing the user id for use later and redirect to the workouts page
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  if (mysqli_num_rows($result) > 0) {

	setcookie('usernum', $userid, time() + 120, "/");
	echo "cookie set";
	header( "Location: workouts.php" );


  } else {
	echo "cookie not set";
	header( "Location: index.php" );

 	}
	}
}

//close the connection to the db
mysqli_close($conn);

?>
