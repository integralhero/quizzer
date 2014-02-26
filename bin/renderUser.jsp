<%@ page import="quiz.User" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IT WORKED</title>
</head>
<body>
	<% User reqUser = (User) request.getAttribute("curUser"); %>
	<h3>Name: <%= reqUser.getUsername() %></h3>
	<p>Email: <%= reqUser.getEmail() %></p>
</body>
</html>