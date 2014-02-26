<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Username Exists</title>
</head>
<body>

	<h2><%= request.getParameter("username") %> exists already! Pick a new one</h2>
	<form method="post" action="CreateServlet"> 
		Username: <input type="text" name="username"><BR>
		Password: <input type="password" name="password">
		<input type="submit" value="Create"><BR>
		
	</form>

</body>
</html>