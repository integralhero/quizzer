<%@ page import="quiz.*, java.util.*, javax.*" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"> 
        <meta charset="utf-8">
        <title>Quiz Page</title>
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
              <li>
                <a href="#" id="feedBtn">View Friend Activity</a>
              </li>
              <li>
                <a href="#" id="messagesBtn">View Messages</a>
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
              
            </ul>
          </nav>
        </div>
      </header>

      <div class="container">
        <div class="row">
          <div class="col-xs-9">
             <% 
               	User us = (User)session.getAttribute("currentUser");
             	int curUserID = us.getUserid();
             	Quiz curQuiz = (Quiz)request.getAttribute("curQuiz");
               %>
			<h1>Quiz Title: <%= curQuiz.getName() %></h1><BR>
			Created by: <a href="/Quizzer/user/<%= curQuiz.getUserID() %>">Quiz Creator Profile</a><BR>
			<% if(curQuiz.getCategory() != null) { %> <h3> <small><%= curQuiz.getCategory() %></small></h3><BR><%} %>
			<p><%= curQuiz.getDescription() %></p>
			
          </div>
          <div class="col-xs-3">
          	<a href="/Quizzer/qz/<%= curQuiz.getID() %>"><button type="button" class="btn btn-success btn-lg">Take Quiz</button></a>
          </div>
        </div>
        <div class="row">
        	<div class="col-xs-4">
        		<h3>Your previous performance:</h3>
        		<% ArrayList<QuizTaken> list = QuizTakenDao.getRecentQuizzesByUserID(curUserID);
        		%> <table class="table table-striped">
        			<tr>
        				<th>Date:</th>
        				<th>Score:</th>
        				<th>Time taken:</th>
        			</tr>
        			<% if(list.size() == 0)  { %>
        				<tr>
        					<td>Error, no records found!</td>
        				</tr>
        			<% }  %>
        		<% for(int i = 0 ; i < list.size(); i++) { %>
        		<tr>
        		<td> <%= list.get(i).getTimeTakingQuiz() %></td>
        		<td><%= list.get(i).getScore() %></td>
        		<td><%= list.get(i).getTimeElapsed() %></td>
        		</tr>
        		<% } %>
        		</table>
        	</div>
        	<div class="col-xs-4">
        		<h3>Highest performers (of all time):</h3>
        		<% ArrayList<QuizTaken> lists = QuizTakenDao.getHighScores(curQuiz.getID());
        		%> <table class="table table-striped">
        			<tr>
        				<th>User:</th>
        				<th>Score:</th>
        			</tr>
        			<% if(list.size() == 0)  { %>
        				<tr>
        					<td>Error, no records found!</td>
        				</tr>
        			<% }  %>
        		<% for(int i = 0 ; i < list.size(); i++) { %>
        		<tr>
        		<td> <%=UserDao.getUserById(lists.get(i).getUserID()).getUsername() %></td>
        		<td><%= lists.get(i).getScore() %></td>
        		</tr>
        		<% } %>
        		</table>
        	</div>
        	<div class="col-xs-4">
        		<h3>Highest performers (today):</h3>
        		
        	</div>
        </div>
        <div class="row">
        	<div class="col-xs-4">
        		<h3>Recent performance:</h3>
        		<% ArrayList<QuizTaken> listss = QuizTakenDao.getHighScores(curQuiz.getID());
        		%> <table class="table table-striped">
        			<tr>
        				<th>User:</th>
        				<th>Score:</th>
        			</tr>
        			<% if(list.size() == 0)  { %>
        				<tr>
        					<td>Error, no records found!</td>
        				</tr>
        			<% }  %>
        		<% for(int i = 0 ; i < list.size(); i++) { %>
        		<tr>
        		<td> <%=UserDao.getUserById(listss.get(i).getUserID()).getUsername() %></td>
        		<td><%= listss.get(i).getScore() %></td>
        		</tr>
        		<% } %>
        		</table>
        	</div>
        	<div class="col-xs-8">
        		<h3>Summary Statistics: </h3>
        		Average Score: <%= QuizTakenDao.getAverageScore(curQuiz.getID()) %><BR>
        		Average Time Taken: <%= QuizTakenDao.getAverageTimeElapsed(curQuiz.getID()) %>
        	</div>
        </div>
      </div>



	  
      <script type='text/javascript' src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
      <script type='text/javascript' src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>  
      <script type='text/javascript' src="/Quizzer/js/multiple_pages.js"></script>  
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