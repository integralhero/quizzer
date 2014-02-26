<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Example</title>
</head>

<script language="javascript" type="text/javascript">
function checkPassword()
{
	var pass1=document.forms["login"]["password"].value;
	var pass2=document.forms["login"]["passwordcheck"].value;
    if (pass1 != pass2) {
    	alert("Passwords don't match.");
    	return false;
    }
}

function validateForm()
{
	if (checkEmptyFields()) {
		
	}
	var email = document.forms["login"]["email"].value;
	var at = email.indexOf("@");
	var period = email.lastIndexOf(".");
	if (at == -1 || period == -1 || period < 1 || period < at + 2 || period + 2 >= email.length) {
		alert("Not a valid e-mail address.");
		return false;
	}
	
	checkPassword();
}

</script>

<body>
	<h2>Create New Account!</h2>
	<form id="create_new_user_form" action="LoginServlet" method="post" > 
		Email Address: <input type ="text" name="email"><BR>
		Username: <input type="text" name="username"><BR>
		Password: <input type="password" name="password"><BR>
		Re-enter Password: <input type="password" name="passwordcheck"><BR>
		<input type="submit" value="Login"><BR>
		<a href="newuser.jsp">Create New Account</a>
	</form>
</body>

<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="/Quizzer/js/validate_create_user.js"></script>

</html>

