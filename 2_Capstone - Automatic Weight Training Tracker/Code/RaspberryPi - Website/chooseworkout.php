<?php
//******************************
//chooseworkout.php
//Authors: Mark Rumpel & Kyle Shaw
//Winter 2015
//Version 1.6

//this page is used to retrieve the data from the form on the workouts.php page 
//and store the contents in a cookie that can be used later in the details.php page
//******************************

//retrieves data from form
$wchoice = $_POST['wochoice'];

echo "whoice = " . $wchoice;

//inserts data from form into a cookie
setcookie('wochoice', $wchoice, time() + 120, "/");
echo "cookie set";
//redirects to details page upon completion
header( "Location: details.php" );

?>
