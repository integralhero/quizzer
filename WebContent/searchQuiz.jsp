<%@ page import="java.util.*, quiz.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"> 
        <meta charset="utf-8">
        <title>Search for Quiz</title>
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
              <li>
                <a href="#">Get Started</a>
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
          	<h4>Here are the results of your search...</h4>
            <% 
              ArrayList<Quiz> allQuiz = (ArrayList<Quiz>)request.getAttribute("quizzesMatching");
              for(Quiz q: allQuiz) { %>
              	<div class="quizSearch" style="background: rgba(144,144,144,0.1); padding: 20px; border-radius: 10px;">
              		<h1><a href="/Quizzer/qz/<%= q.getID() %>"><%= q.getName() %></a></h1>
              		<p><b>Description</b>: <%= q.getDescription() %></p>
             	</div><BR>
            <% } %>
            <% if(allQuiz.size() == 0) { %>
              Sorry! No results found...
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