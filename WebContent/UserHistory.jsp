<%@ page import="java.util.*, quiz.*" %>
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
        <link rel="stylesheet" type="text/css" href="../css/style.css">
        <link rel="stylesheet" type="text/css" href="../css/createQuiz.css">
        
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
              <li>
              	<a href="/Quizzer/quiz/createQuiz.jsp" id="createquizBtn">Create Quiz</a>
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
          <div class="col-xs-12">
          	<h1>Your Past Performance</h1>
          	
          	<table class="table"> 
          	<th>QuizID</th>
          	<th>Quiz Name</th>
          	<th>Score</th> 
          	<th>Time Elapsed</th> 
          	<th>Time Taken</th>
          	<% 
         	User me = (User) request.getSession(false).getAttribute("currentUser");
           	ArrayList<QuizTaken> pastQuizzes = QuizTakenDao.getAllQuizzesTakenByUser(me.getUserid());
           	Collections.sort(pastQuizzes);
           	for(int i = pastQuizzes.size() - 1; i>= 0; i--) { %>
           	
           	<%
           	QuizTaken quizTaken = pastQuizzes.get(i);
       		Quiz quiz = QuizDao.getQuizByID(quizTaken.getQuizID());
           	%> 
           	
          	<tr> 
          		<td><%= quiz.getID() %></td> 
          		<td><%= quiz.getName() %></td> 
          		<td><%= ((double)(quizTaken.getScore())/quiz.getScore()) * 100 + "%" %></td>
          		<td><%= quizTaken.getTimeElapsed() %></td>
          		<td><%= quizTaken.getTimeTakingQuiz() %></td>
          	</tr> 
          	
          	<% } %> 
          	</table>
          	
          	</div>
          	</div>
          	</div>
          	
          	<script type='text/javascript' src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
      <script type='text/javascript' src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
      <script type='text/javascript' src="../js/createQuiz.js"></script>    
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