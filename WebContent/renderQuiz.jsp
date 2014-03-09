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
          <div class="col-xs-12">
             <% 
               	User us = (User)session.getAttribute("currentUser");
             	int curUserID = us.getUserid();
             	Quiz curQuiz = (Quiz)request.getAttribute("curQuiz");
               %>
			<%= curQuiz.getName() %>
			<form name="take_quiz_form" action="" method="post" > 
			<%
			ArrayList<Question> questions = curQuiz.questions;
			if (curQuiz.getRandomizeQuestions()) {
				Collections.shuffle(questions);
			}
			if (curQuiz.getMultiplePages()) {
				for(int i = 0; i < questions.size(); i++)  {
					Question question = questions.get(i);
						%>
						<div class='question'>
						<%
							switch (QuestionTypes.getType(question.getQuestionType())) {
							case 1:	//QR %>
								<h3>Question: <%= ((QuestionResponse)question).getQuestion() %></h3>
						<%		for(int answerNum = 0; answerNum < question.getNumAnswers(); answerNum++) { %>
									<label for="answerField">Answer:&nbsp;</label><input type="text" class="answerField">
						<% 		}
						
							for (String answer : question.getAnswers()) {
						%>
							<input type="hidden" class="hiddenAnswer" value="<%= answer %>">
													
						<%
							}
							break;
							case 2: //FIB %>
								<h3>Question: <%= ((FillBlankQuestion)question).getQuestion() %></h3>
						<%		for(int answerNum = 0; answerNum < question.getNumAnswers(); answerNum++) { %>
									<label for="answerField">Answer:&nbsp;</label><input type="text" id="answerField">
						<% 		}
							for (String answer : question.getAnswers()) {
						%>
							<input type="hidden" class="hiddenAnswer" value="<%= answer %>">
													
						<%
							}
						 	break;
							case 3: //MC  %>
								<h3>Question: <%= ((MultipleChoiceQuestion)question).getQuestion() %></h3>
								
						<%		String typeOfInput = "radio";
								if(question.getNumAnswers() > 1) {
									typeOfInput = "checkbox";
								}
								for (String choice : ((MultipleChoiceQuestion)question).getChoices()) {
						%>
									<div class='row'>
										<div class='col-lg-6'>
											<div class='input-group'>
												<span class='input-group-addon'><input type='<%= typeOfInput %>' name="mult_choice_answer"></span><input type='text' value="<%= choice %>" class='form-control' id="answerField"  >
											</div>
										</div>
									</div>
						<%
								}
							for (String answer : question.getAnswers()) {
						%>
							<input type="hidden" class="hiddenAnswer" value="<%= answer %>">
													
						<%
							}
							break;
							case 4: //PR %>
								<h3>Question: </h3><img alt="" src="<%=((PictureResponseQuestion)question).getURL() %>"><br><br>
						<%		for(int answerNum = 0; answerNum < question.getNumAnswers(); answerNum++) { %>
									<label for="answerField">Answer:&nbsp;</label><input type="text" id="answerField">
						<% 		}
							for (String answer : question.getAnswers()) {
						%>
							<input type="hidden" class="hiddenAnswer" value="<%= answer %>">
													
						<%
							}	
						  break;	
							}
						%>
						<ul class="pager">
						  <li class="previous disabled"><a href="#">&larr; Older</a></li>
						  <li class="next"><a href="#">Newer &rarr;</a></li>
						</ul>
						<div class="feedback"></div>
						</div> <!-- Close question div -->
						<%
				} //end for loop	
			} else {
				//single page code
			}
			%>
			</form>
          </div>
        </div>
      </div>



	  
      <script type='text/javascript' src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
      <script type='text/javascript' src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>  
      <script type='text/javascript' src="js/multiple_pages.js"></script>  
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