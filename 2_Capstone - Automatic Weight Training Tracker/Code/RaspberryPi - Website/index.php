<!-- 
******************************
index.php
Authors: Mark Rumpel & Kyle Shaw
Winter 2015
Version 1.6

this is the home page, displays a login form and prompts user for credentials
this page will then send the input from the form to login.php

******************************
-->


<!DOCTYPE html>
<head>
<title>Login Form</title>
<link rel="stylesheet" type="text/css" href="mystyle.css">
</head>

<body id="loginbody">
  <section class="container">
    <div class="login">
      <h1>Login to Gymtron Web App</h1>
		<br/>
		<br/>
		<br/>
      <form method="post" action="login.php">
        <p><input type="text" name="login" value="" placeholder="Enter Gymtron ID"></p>
       <!-- <p><input type="password" name="password" value="" placeholder="Password"></p> -->
        <p class="submit"><input type="submit" name="commit" value="Login"></p>
      </form>
    </div>
	
  </section>
</body>
</html>
