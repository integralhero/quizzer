<%@ page import="quiz.*" %>
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
            <a href="/Quizzer" class="navbar-brand">Quizzer</a>
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
          <div class="col-xs-12">
            <% User reqUser = (User) request.getAttribute("curUser"); 
               User us = (User)session.getAttribute("currentUser");
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