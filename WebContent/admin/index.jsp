<%@ page import="quiz.*, java.util.*" %>
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
        <link rel="stylesheet" type="text/css" href="css/carous.css">
        
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
              <li>
                <a href="#">Get Started</a>
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
                <a href="#" id="logout">Logout</a>
              </li>
              <% if(us.checkIsAdmin()) { %>
	          <li>
	             <a href="admin/index.jsp">Administration</a>
	          </li>
	          <% } %>
	          <li>
	             <a href="quiz/createQuiz.jsp" id="createquizBtn">Create Quiz</a>
	           </li>
            </ul>
          </nav>
        </div>
      </header>
	
      <div class="container">
      	<div class="row">
      		<div class="col-xs-8">
      			<h3>Add an announcement:</h3>
      			<form action="AnnouncementAdd" method="post">
      				<input type="text" placeholder="Add a new announcement" name="announcement_message">
      				<button type="submit" class="btn btn-default">Add</button>
      			</form>
      			<HR>
      			<h3>Remove Users:</h3>
      			<% List<User> users = UserDao.getAllUser(); %>
      			<% if(users.size() == 0)  { %>
      				Sorry, no flagged quizzes found!
      			<% } else { %>
	      			<form action="RemoveUser" method="post">
	      				<select multiple class="form-control" name="user-ids">
	      					<% for(User u: users) { %>
	      						<option value="<%= u.getUserid() %>" ><a href="/Quizzer/user/<%= u.getUserid() %>"><%= u.getUsername()  %></a></option>
	      					<% } %>
	      				</select>
	      				<button type="submit" class="btn btn-danger">Remove User</button>
	      			</form>
      			<% } %>
      		</div>
      		<div class="col-xs-4">
      			<h5>Flagged Quizzes:</h5>
      			<% ArrayList<Quiz> flagged = QuizDao.getAllFlaggedQuizzes(); %>
      			<% if(flagged.size() == 0)  { %>
      				Sorry, no flagged quizzes found!
      			<% } else { %>
	      			<form action="RemoveQuiz" method="post">
	      				<select multiple class="form-control" name="quiz-ids">
	      					<% for(Quiz q: flagged) { %>
	      						<option value="<%= q.getID() %>" ><a href="/Quizzer/ql/<%= q.getID() %>"><%= q.getName() %></a> has been flagged <%= q.getNumFlags() %> times.</option>
	      					<% } %>
	      				</select>
	      				<button type="submit" class="btn btn-danger">Remove Quizzes</button>
	      			</form>
      			<% } %>
      			<HR>
      			<h5>All Quizzes:</h5>
      			<% ArrayList<Quiz> all = QuizDao.getAllCreatedQuizzes(); %>
      			<% if(all.size() == 0)  { %>
      				Sorry, no quizzes found!
      			<% } else { %>
	      			<form action="RemoveQuiz" method="post">
	      				<select multiple class="form-control" name="quiz-ids">
	      					<% for(Quiz a: all) { %>
	      						<option value="<%= a.getID() %>" ><a href="/Quizzer/ql/<%= a.getID() %>"><%= a.getName() %></a></option>
	      					<% } %>
	      				</select>
	      				<button type="submit" class="btn btn-danger">Remove Quizzes</button>
	      			</form>
      			<% } %>
      		</div>
      	</div>
      	<div class="row">
      		<div class="col-xs-4">
      			<h5>Delete History:</h5>
      			<% ArrayList<Quiz> allQs = QuizDao.getAllCreatedQuizzes(); %>
      			<% if(allQs.size() == 0)  { %>
      				Sorry, no quizzes found!
      			<% } else { %>
	      			<form action="RemoveHistory" method="post">
	      				<select multiple class="form-control" name="quiz-ids">
	      					<% for(Quiz s: allQs) { %>
	      						<option value="<%= s.getID() %>" ><a href="/Quizzer/ql/<%= s.getID() %>"><%= s.getName() %></a></option>
	      					<% } %>
	      				</select>
	      				<button type="submit" class="btn btn-danger">Remove History</button>
	      			</form>
      			<% } %>
      		</div>
      		<div class="col-xs-4">
      		<h1>Site Statistics</h1>
      		Number of quizzes created: <%= QuizDao.getQuizNum() %>
      		Number of users: <%= UserDao.getUserNum() %>
      		</div>
      		<div class="col-xs-4">
      		</div>
      	</div>
      </div>

      <script type='text/javascript' src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
      <script type='text/javascript' src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>    
        
    </body>
</html>