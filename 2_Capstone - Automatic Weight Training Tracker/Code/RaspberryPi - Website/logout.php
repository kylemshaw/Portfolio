<?php

//******************************
//logout.php
//Authors: Mark Rumpel & Kyle Shaw
//Winter 2015
//Version 1.6

//this page will clear all cookies and redirect to the login page

//******************************

//this page deletes the data contained in the cookies and redirects to the login page
setcookie('usernum', '', time()-60*60*24*365, '/');
setcookie('wochoice', '', time()-60*60*24*365, '/');

echo "Redirecting...";

//redirects to the login page
header( "Location: login.php" );

?>
