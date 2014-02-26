<%@page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Account!</title>
</head>

<body>
	<%
		if ((Boolean)application.getAttribute("isUsernameTaken")) {
			out.println("<h2>Username Taken!</h2>");
		} else {
			out.println("<h2>Create New Account!</h2>");
		}
	%>
	
	<form id="create_new_user_form" action="CreateServlet" method="post" > 
		Email Address: <input type ="text" id="email"><BR>
		Username: <input type="text" id="username"><BR>
		Password: <input type="password" id="password"><BR>
		Re-enter Password: <input type="password" id="passwordcheck"><BR>
		<input type="submit" value="Create"><BR>
	</form>
</body>

<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="/Quizzer/js/validate_create_user.js"></script>

</html>

