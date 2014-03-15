<%@ page import="quiz.*, javax.*, java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"> 
        <meta charset="utf-8">
        <title>Search for User</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
        <link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
        <!--[if lt IE 9]>
          <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <link rel="shortcut icon" href="/bootstrap/img/favicon.ico">
        <link rel="apple-touch-icon" href="/bootstrap/img/apple-touch-icon.png">
        <link rel="apple-touch-icon" sizes="72x72" href="/bootstrap/img/apple-touch-icon-72x72.png">
        <link rel="apple-touch-icon" sizes="114x114" href="/bootstrap/img/apple-touch-icon-114x114.png">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        
        <style type="text/css">
            header {
              margin-bottom:30px;
            }
        </style>

    </head>
    <% User us = (User)request.getSession(false).getAttribute("currentUser"); %>
    <body> 
      <header class="navbar navbar-default navbar-static-top" role="banner">
        <div class="container">
          <div class="navbar-header">
            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a href="/Quizzer/" class="navbar-brand">Quizzer</a>
          </div>
          <nav class="collapse navbar-collapse" role="navigation">
            <ul class="nav navbar-nav">

              <% if(us.checkIsAdmin()) { %>
	          <li>
	             <a href="admin/index.jsp">Administration</a>
	          </li>
	          <% } %>
	          <li>
	             <a href="quiz/createQuiz.jsp" id="createquizBtn">Create Quiz</a>
	           </li>
              
            </ul>
            <ul class="nav navbar-nav navbar-right">
              <form class="navbar-form navbar-left" action="/Quizzer/SearchUserServlet" method="get" role="search">
                <div class="form-group">
                  <input type="text" class="form-control" placeholder="Add a friend" name="usernamequery">
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
              </form>
              <li>
	              <form action="LogoutServlet" method="get">
	                <button type="submit" class='btn-default'>Logout</button>
	             </form>
              </li>
              
            </ul>
          </nav>
        </div>
      </header>

      <div class="container">
        <div class="row">
          <div class="col-xs-12">
            <% User reqUser = (User) request.getAttribute("curUser"); 
               int senderid = us.getUserid();
               int recipientid = reqUser.getUserid();
               boolean friendsExist = MessageDao.checkIfFriendsExist(senderid, recipientid);
               boolean reqExistsPrim = MessageDao.checkIfRequestExist(senderid, recipientid);
               boolean reqExistsSecon = MessageDao.checkIfRequestExist(recipientid, senderid);
            %>
            
            <h3>Name: <%= reqUser.getUsername() %></h3> 
            <p>Email: <%= reqUser.getEmail() %></p>
            <% if(!friendsExist && !reqExistsPrim && !reqExistsSecon) { %>
            <form action="/Quizzer/FriendRequestServlet" method="post">
            	<input name="friendID" type="hidden" value="<%= recipientid %>">
            	<input name="myID" type="hidden" value="<%= senderid %>">
            	<button type="submit" class="btn btn-success">Send Friend Request</button>
            </form>
            <% } else if(!friendsExist && reqExistsPrim && !reqExistsSecon) { %>
            	Friend request already sent!
            <% } else if(!friendsExist && !reqExistsPrim && reqExistsSecon) { %>
            	You have already received a friend request from this person. Check your messages!
            <% } else if(friendsExist) { %>
            	You are already friends!
            	<form action="/Quizzer/DeleteFriend" method="post">
            		<input name="friendID" type="hidden" value="<%= recipientid %>">
            		<input name="myID" type="hidden" value="<%= senderid %>">
            		<button type="submit" class="btn btn-danger">Delete Friend</button>
            	</form>
            <% } %>
            <% if(us.checkIsAdmin()) { %>
	            <% if(reqUser.checkIsAdmin()) { %>
	            	<form action="/Quizzer/DemoteUser" method="post">
	            		<input name="user_id" type="hidden" value="<%= recipientid %>">
	            		<button type="submit" class="btn btn-danger">Demote User</button>
	            	</form>
	            <% } else { %>
	            	<form action="/Quizzer/PromoteUser" method="post">
	            		<input name="user_id" type="hidden" value="<%= recipientid %>">
	            		<button type="submit" class="btn btn-success">Promote User</button>
	            	</form>
	            <% } %>
            <% } %>
			<%  ArrayList<User> allFriends = UserDao.getAllFriendsOf(us.getUserid()); %>
			<h3>Send a message to a friend:</h3>
			<% if(allFriends.size() == 0) { %>
				You should add some friends!
			<% } else { %>
				<form action="/Quizzer/NoteServlet" method="post">
	            	<input name="myID" type="hidden" value="<%= us.getUserid() %>">
	            	To: <select name="friendID">
	            	<% for(User friend: allFriends)  { %>
	            		<option value="<%= friend.getUserid() %>"><%= friend.getUsername() %></option>
	            	<% }  %>
	            	</select><BR>
	            	<BR>
	            	<textarea rows="5" cols="80" name="message"></textarea><BR>
	            	<BR>
	            	<button type="submit" class="btn btn-success">Send Message</button>
	            </form>
			<% } %>
          </div>
        </div>
      </div>

      <script type='text/javascript' src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
      <script type='text/javascript' src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>    
      <script type='text/javascript'>
      
        $(document).ready(function() {
          $(function() {
             $('a#feedBtn').click(function() {
                $('div#activities').toggle();
                return false;
             });
             $('a#messagesBtn').click(function() {
                $('div#messages').toggle();
                return false;
             });
          });   
        });
      
      </script>
        
    </body>
</html>