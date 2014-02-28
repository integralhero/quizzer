<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/black-tie/jquery-ui.min.css" rel="stylesheet">
<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">

<link href="../css/style.css" rel="stylesheet">
<link href="../css/login.css" rel="stylesheet">

</head>

<body>
	<div class="container">
		<div class="row">
			<div id="loginMenu">
				<h2>Welcome! <small>Please sign in to continue</small></h2>
				<form name="login" action="LoginServlet" method="post" > 
					<input type="text" name="username" id="usernameInput" placeholder="username"><BR>
					<input type="password" name="password" id="passwordInput" placeholder="password"><BR>
					<div class="pull-left">
						<small>or <a href="/Quizzer/login/create.jsp">Create an Account</a></small>
					</div>
					<input type="submit" class="btn btn-success pull-right" value="Login" id="loginSubmit"><BR>
				</form>
			</div>
		</div>
	</div>
	<script type='text/javascript' src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type='text/javascript' src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
	
    <script type='text/javascript' src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script> 
</body>
</html>