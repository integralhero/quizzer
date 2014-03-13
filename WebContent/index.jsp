
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
        <link rel="stylesheet" type="text/css" href="css/carous.css">
        
        <style type="text/css">
            header {
            	margin-bottom:30px;
            }
        </style>

    </head>
    
    <body> 
    <% if(request.getSession().getAttribute("currentUser") == null)  { %>
    	<h1><%= "Error, not logged in. Redirecting..." %></h1>
    	<META HTTP-EQUIV="refresh" CONTENT="3;URL=/Quizzer/login/login.jsp">

    <% } else { %>
      <% 
      User me = (User)request.getSession(false).getAttribute("currentUser");  
      %>
	      <header class="navbar navbar-default navbar-static-top" id="navNav" role="banner">
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
			<div id="toppic">
			</div>
		    <div id="myCarousel" class="carousel slide" data-ride="carousel">
		        <!-- Wrapper for slides -->
		        <div class="carousel-inner">
		            <div class="item active">
		                <img src="http://placehold.it/1200x300/16a085/ffffff&text=Welcome to Quizzer">
		                <div class="carousel-caption">
		                    <h3>
		                        </h3>
		                        <i id="link" class="fa fa-angle-double-down fa-4x"></i> 
		                </div>
		            </div>
		            <!-- End Item -->
		            <div class="item">
		                <img src="http://placehold.it/1200x300/e67e22/ffffff&text=Announcements">
		                <div class="carousel-caption">
		                	<div id="announce">
			                    <% ArrayList<Announcement> allAnnouncements = (ArrayList<Announcement>) request.getAttribute("announcements"); %>
						          <div class="col-md-12 column" >
						            <% if(allAnnouncements.size() == 0)  { %>
						            	<i>No announcements found!</i>
						            <% } %>
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
		            </div>
		            <!-- End Item -->
		            
		        </div>
		        <!-- End Carousel Inner -->
		        <ul class="nav nav-pills nav-justified">
		            <li data-target="#myCarousel" data-slide-to="0" class="active"><a href="#">Welcome<small>to Quizzer</small></a></li>
		            <li data-target="#myCarousel" data-slide-to="1"><a href="#">Announcements<small>Check Updates!</small></a></li>
		            
		        </ul>
		    </div>
		    <!-- End Carousel -->
		
	      <div class="container" id="bottomDiv">
	      <a name="bottomDiv"/></a>
	        <div class="row clearfix">
	          <div class="col-md-8 column" id="quizDiv">
	            <h3><small>Quiz Info: </small></h3>
	            <div class="row clearfix">
	              <div class="col-md-6 column" id="hotQuizzes">
	                <h3>Hot Quizzes</h3>
	                <p>
						<% ArrayList<Quiz> popularQuizzes = QuizDao.getMostPopularQuizzes(); %>
						<% for(int i = 0; i < popularQuizzes.size(); i++){ %>
						<a href="ql/<%= popularQuizzes.get(i).getID() %>"><%= popularQuizzes.get(i).getName() %></a>
						- <%= popularQuizzes.get(i).getDescription() %><BR>
						<% } %>
	                </p>
	              </div>
	              <div class="col-md-6 column" id="recentQuizzes">
	                <h3>Recent Quizzes</h3>
	                <p>
						<% ArrayList<Quiz> recentQuizzes = QuizDao.getRecentCreatedQuizzes(); %>
						<% for(int i = 0; i < recentQuizzes.size(); i++){ %>
						<a href="ql/<%= recentQuizzes.get(i).getID() %>"><%= recentQuizzes.get(i).getName() %></a>
						- <%= recentQuizzes.get(i).getDescription() %><BR>
						<% } %>
	                </p>
	              </div>
	            </div>
	            <div class="row clearfix">
	              <div class="col-md-6 column" id="quizzesTaken">
	              	<% ArrayList<QuizTaken> takenQuizzes = QuizTakenDao.getRecentQuizzesByUserID(me.getUserid()); %>
	              	<% if(takenQuizzes.size() != 0){ %>
	                <h3>Your Taken Quizzes</h3>
	                <p>
						<% for(int i = 0; i < takenQuizzes.size(); i++){ %>
						<a href="ql/<%= takenQuizzes.get(i).getQuizID() %>"><%= QuizDao.getQuizByID(takenQuizzes.get(i).getQuizID()).getName() %></a>
						- <%= QuizDao.getQuizByID(takenQuizzes.get(i).getQuizID()).getDescription() %><BR>
						<% } %>
					<% } %>
					 </p>
	              </div>
	              <div class="col-md-6 column" id="quizzesCreated">
	              	
	             	<% ArrayList<Quiz> createdQuizzes = QuizDao.getRecentUserCreatedQuizzes(me.getUserid()); %>
	             	<% if(createdQuizzes.size() != 0){ %>
	                <h3>Your Created Quizzes</h3>
	                <p>
						<% for(int i = 0; i < createdQuizzes.size(); i++){ %>
						<a href="ql/<%= createdQuizzes.get(i).getID() %>"><%= createdQuizzes.get(i).getName() %></a>
						- <%= createdQuizzes.get(i).getDescription() %><BR>
						<% } %>	 
					<% } %>  
					</p>
	              </div>
	            </div>
	          </div>
	          <button class="btn btn-default" id="feedBtn" style="margin-right: 20px;">View Friend Activity </button>    
	           
	          <button  class="btn btn-default"  id="messagesBtn">View Messages</button>
	          <div class="col-md-4 column" id="messages" style="display:none">
	            <h3><small>Your Messages:</small></h3>
	            <h4>Friend Requests:</h4>
	            <% ArrayList<FriendRequest> frqs = (ArrayList<FriendRequest>) request.getAttribute("allRequests"); %>
	            <% if(frqs.size() == 0) { %>
	            	<i>No friend requests found.</i>
	            <% } %>
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
	            <HR>
	            <h4>Notes:</h4>
	            <% ArrayList<Note> allnotes = (ArrayList<Note>) request.getAttribute("notes"); %>
	            <% if(allnotes.size() == 0) { %>
	            	<i>No notes found.</i>
	            <% } %>
	            <% for(Note n: allnotes) { %>
	            	<b>From:</b> <%= UserDao.getUserById(n.senderID).getUsername() %><BR>
	            	<b>Message:</b> <%= n.getMessage() %>
	            	<BR>
	            	<BR>
	            <% } %>
	            <HR>
	            <h4>Challenge Requests:</h4>
	            <% ArrayList<ChallengeRequest> challenges = (ArrayList<ChallengeRequest>) request.getAttribute("challenges"); %>
	            <% if(challenges.size() == 0) { %>
	            	<i>No challenges found.</i>
	            <% } %>
	            <% for(ChallengeRequest cr: challenges) { %>
	            	You received a challenge request from <%= UserDao.getUserById(cr.getSenderID()).getUsername() %> to take
	            	<a href="/Quizzer/qz/<%= cr.getQuizID() %>"><%= QuizDao.getQuizByID(cr.getQuizID()).getName() %></a>!<BR>
	            	<BR>
	            	<BR>
	            <% } %>
	          </div>
	          <div class="col-md-4 column" id="activities" style="display:none">
	            <h3><small>Recent Activity</small></h3>
	             <h4>Created Quizzes:</h4>
		            <% ArrayList<Quiz> friendsCreatedQuizzes = QuizDao.getFriendsCreatedQuizzes(me.getUserid()); %>
		            <% if(friendsCreatedQuizzes!= null) { %>
			            <% if(friendsCreatedQuizzes.size() == 0) { %>
			            	<i>No friend activity found.</i>
			            <% } %>
			            <ul>
			            <% for(Quiz q: friendsCreatedQuizzes) {%>
			            <a href="ql/<%= q.getID() %>"><%= q.getName() %></a> - <%= q.getDescription() %><BR>
			            
			            <% } %>
			       	 <% } else {%> 
			        	<i>No friend activity found. NULL</i>
			         <% } %>
		            </ul>
		         <h4>Taken Quizzes:</h4>
		          	<% ArrayList<QuizTaken> friendsTakenQuizzes = QuizTakenDao.getFriendsTakenQuizzes(me.getUserid()); %>
			        <%if(friendsTakenQuizzes != null){ %> 
			            <% if(friendsTakenQuizzes.size() == 0) { %>
			            	<i>No friend activity found.</i>
			            <% } %>
			            <ul>
			            <% for(QuizTaken q: friendsTakenQuizzes) {%>
			            <a href="ql/<%= q.getQuizID() %>"><%= QuizDao.getQuizByID(q.getQuizID()).getName() %></a><BR>
			            
			            <% } %>
		            <% } else { %> 
		        		<i>No friend activity found. NULL</i>
		        	<% } %>
		            </ul>
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
	</div>
	      <script type='text/javascript' src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	      <script type='text/javascript' src="js/carousel.js"></script>
	      <script type='text/javascript' src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>    
	      <script type='text/javascript'>
	      
	        $(document).ready(function() {
	          $(function() {
	             $('#feedBtn').click(function() {
	                $('div#activities').slideDown();
	                $('div#messages').fadeOut();
	                return false;
	             });
	             $('#messagesBtn').click(function() {
	                $('div#messages').slideDown();
	                $('div#activities').fadeOut();
	                return false;
	             });
	          });   
	        });
	      
	      </script>
	   <% }  %>
    </body>
</html>