<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Account</title>
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/black-tie/jquery-ui.min.css" rel="stylesheet">
<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">


<link href="../css/login.css" rel="stylesheet">

</head>

<body>
	<div class="container">
		<div class="row">
			<div id="loginMenu">
				<h2>Welcome! <small>Please create an account to continue</small></h2>
				<form id="create_new_user_form" action="CreateServlet" method="post" > 
					<input type ="text" id="email" name = "email" placeholder="email"><BR>
					<input type="text" id="username" name = "username" placeholder="username"><BR>
					<input type="password" id="password" name = "password" placeholder="password"><BR>
					<input type="password" id="password-check" name = "passwordcheck" placeholder="confirm password"><BR>
					<input type="submit" class="btn btn-primary" value="Create" id="submit"><BR>
				</form>
			</div>
		</div>
	</div>
	
	<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
	<script type='text/javascript' src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type='text/javascript' src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
	<script type='text/javascript' src="../js/validater_create_users.js"></script>
    <script type='text/javascript' src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script> 
</body>
</html>