<%@ page import="java.util.*, quiz.User" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Searching for User...</title>
</head>
<body>
	<% 
		ArrayList<User> allUser = (ArrayList<User>)request.getAttribute("foundUsers");
	  for(User user: allUser) { %>
		<h1><a href="/Quizzer/user/<%= user.getUserid() %>"><%= user.getUsername() %></a></h1>
	<% } %>
	<% if(allUser.size() == 0) { %>
		Sorry! No results found...
	<% } %>
	
</body>
</html>