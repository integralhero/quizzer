<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*, quiz.*" %>
<html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"> 
        <meta charset="utf-8">
        <title>Quizzer- Quiz Site</title>
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
    <% User me = (User)request.getSession(false).getAttribute("currentUser"); %>
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
              <% if(me.checkIsAdmin()) { %>
              <li>
                <a href="admin/index.jsp">Administration</a>
              </li>
              <% } %>
              <li>
                <a href="quiz/createQuiz.jsp" id="createquizBtn">Create Quiz</a>
              </li>
              
              
            </ul>
            <ul class="nav navbar-nav navbar-right">
            	<form class="navbar-form navbar-left" action="SearchUserServlet" method="get" role="search">
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
        <div class="row clearfix">
        <% ArrayList<Announcement> allAnnouncements = (ArrayList<Announcement>) request.getAttribute("announcements"); %>
          <div class="col-md-12 column" id="announcements" >
            <h2>List of announcements</h2>
            <ul>
            <% for(Announcement msg : allAnnouncements)  { %>
            	<li><%= msg.getMessage() %>
            	<% if(me.checkIsAdmin()) { %>
            	<form action="AnnouncementRemove" method="post">
            		<input name="message_id" type="hidden" value="<%= msg.getMessage_id() %>">
            		<button type="submit" class="btn btn-danger" >Delete</button>
            	</form>
            	<% } %>
            	</li>
            	
            <% } %>
            </ul>
          </div>
          
        </div>
        <div class="row clearfix">
          <div class="col-md-8 column" id="quizDiv">
            <h3><small>Quiz Info: </small></h3>
            <div class="row clearfix">
              <div class="col-md-6 column" id="hotQuizzes">
                <h3>Hot Quizzes</h3>
                <p>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus fermentum vestibulum augue vel faucibus. Vivamus a est eget velit iaculis feugiat. Nulla non dui auctor, pharetra felis sit amet, congue ante. Ut tempor erat lacus, vel convallis massa imperdiet vestibulum. Donec ullamcorper ipsum non quam blandit faucibus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Pellentesque egestas ullamcorper faucibus. Morbi at lobortis lectus. Nulla sollicitudin purus vitae dui mollis, nec placerat elit pulvinar. In nec libero leo.
                </p>
              </div>
              <div class="col-md-6 column" id="recentQuizzes">
                <h3>Recent Quizzes</h3>
                <p>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus fermentum vestibulum augue vel faucibus. Vivamus a est eget velit iaculis feugiat. Nulla non dui auctor, pharetra felis sit amet, congue ante. Ut tempor erat lacus, vel convallis massa imperdiet vestibulum. Donec ullamcorper ipsum non quam blandit faucibus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Pellentesque egestas ullamcorper faucibus. Morbi at lobortis lectus. Nulla sollicitudin purus vitae dui mollis, nec placerat elit pulvinar. In nec libero leo.
                </p>
              </div>
            </div>
            <div class="row clearfix">
              <div class="col-md-6 column" id="quizzesTaken">
                <h3>Quizzes Taken</h3>
                <p>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus fermentum vestibulum augue vel faucibus. Vivamus a est eget velit iaculis feugiat. Nulla non dui auctor, pharetra felis sit amet, congue ante. Ut tempor erat lacus, vel convallis massa imperdiet vestibulum. Donec ullamcorper ipsum non quam blandit faucibus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Pellentesque egestas ullamcorper faucibus. Morbi at lobortis lectus. Nulla sollicitudin purus vitae dui mollis, nec placerat elit pulvinar. In nec libero leo.
                </p>
              </div>
              <div class="col-md-6 column" id="quizzesCreated">
                <h3>Quizzes Created</h3>
                <p>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus fermentum vestibulum augue vel faucibus. Vivamus a est eget velit iaculis feugiat. Nulla non dui auctor, pharetra felis sit amet, congue ante. Ut tempor erat lacus, vel convallis massa imperdiet vestibulum. Donec ullamcorper ipsum non quam blandit faucibus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Pellentesque egestas ullamcorper faucibus. Morbi at lobortis lectus. Nulla sollicitudin purus vitae dui mollis, nec placerat elit pulvinar. In nec libero leo.
                </p>
              </div>
            </div>
          </div>
          <a href="#" id="feedBtn">View Friend Activity</a>
          <a href="#" id="messagesBtn">View Messages</a>
          <div class="col-md-2 column" id="messages" style="display:none">
            <h3><small>Your Messages:</small></h3>
            <h4>Friend Requests:</h4>
            <% ArrayList<FriendRequest> frqs = (ArrayList<FriendRequest>) request.getAttribute("allRequests"); %>
            <ul>
            <% for(FriendRequest f: frqs) {%>
            <%  User sender = UserDao.getUserById(f.getSenderID());  
            	int senderid = sender.getUserid();
            	User us = (User) request.getSession(false).getAttribute("currentUser");
            	int recipientid = us.getUserid();
            	int messageID = f.getMessageID();
            %>
            <li>Request from: <a href="/Quizzer/user/<%= senderid %>"><%= sender.getUsername() %></a>
            	<form action="FriendConfirm" method="post">
            		<select name="choice">
					  <option value="yes">Confirm</option>
					  <option value="no">Deny</option>
					</select>
					<input type="hidden" name="senderid" value="<%= senderid %>">
					<input type="hidden" name="recipientid" value="<%= recipientid %>">
					<input type="hidden" name="messageID" value="<%= messageID %>">
					
            		<button type="submit" class="btn btn-success">Confirm Choice</button>
            	</form>
            </li>
            <% } %>
            </ul>
          </div>
          <div class="col-md-2 column" id="activities" style="display:none">
            <h3><small>Recent Activity</small></h3>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus fermentum vestibulum augue vel faucibus. Vivamus a est eget velit iaculis feugiat. Nulla non dui auctor, pharetra felis sit amet, congue ante. Ut tempor erat lacus, vel convallis massa imperdiet vestibulum. Donec ullamcorper ipsum non quam blandit faucibus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Pellentesque egestas ullamcorper faucibus. Morbi at lobortis lectus. Nulla sollicitudin purus vitae dui mollis, nec placerat elit pulvinar. In nec libero leo.
            </p>
          </div>
        </div>
        <div class="row clearfix">
          <div class="col-md-12" id="achievements">
            <p>
              <h3>Achievements:</h3>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus fermentum vestibulum augue vel faucibus. Vivamus a est eget velit iaculis feugiat. Nulla non dui auctor, pharetra felis sit amet, congue ante. Ut tempor erat lacus, vel convallis massa imperdiet vestibulum. Donec ullamcorper ipsum non quam blandit faucibus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Pellentesque egestas ullamcorper faucibus. Morbi at lobortis lectus. Nulla sollicitudin purus vitae dui mollis, nec placerat elit pulvinar. In nec libero leo.
            </p>
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