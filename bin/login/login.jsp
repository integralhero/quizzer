<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>

<body>
	<h2>Welcome!</h2>
	<form name="login" action="LoginServlet" method="post" > 
		Username: <input type="text" name="username"><BR>
		Password: <input type="password" name="password"><BR>
		<input type="submit" value="Login"><BR>
		<a href="newuser.jsp">Create New Account</a>
	</form>
</body>
</html>