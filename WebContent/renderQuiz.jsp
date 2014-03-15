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
             	int curUserID = us.getUserid();
             	Quiz curQuiz = (Quiz)request.getAttribute("curQuiz");
               %>
			<%= curQuiz.getName() %>
			<form id="take_quiz_form" name="take_quiz_form" action="/Quizzer/TakeQuizServlet" method="post" > 
			
			<input type="hidden" value="<%= curQuiz.getID() %>" name="quiz_id">
			<input type="hidden" value="" id="dateTaken" name="dateTaken">
			<input type="hidden" value="" id="time" name="time">
			<%
			ArrayList<Question> questions = curQuiz.getQuestions();
			System.out.println("On the front end: " + questions.size());
			%>
			<input type=hidden value="<%= questions.size() %>" name="num_questions">
			<%
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
						<%		for(int answerNum = 0; answerNum < question.getScore(); answerNum++) { %>
									<label for="answerField">Answer:&nbsp;</label><input type="text" class="answerField" name="answerField<%=i%>">
						<% 		}
						
							for (String answer : question.getAnswers()) {
						%>
							<input type="hidden" value="<%= question.getQuestionType() %>" name="questionType">
							<input type="hidden" class="hiddenAnswer" value="<%= answer %>" name="hiddenAnswer<%=i%>">
													
						<%
							}
							break;
							case 2: //FIB %>
								<h3>Question: <%= ((FillBlankQuestion)question).getQuestion() %></h3>
						<%		for(int answerNum = 0; answerNum < question.getScore(); answerNum++) { %>
									<label for="answerField">Answer:&nbsp;</label><input type="text" class="answerField" name="answerField<%=i%>">
						<% 		}
							for (String answer : question.getAnswers()) {
						%>
							<input type="hidden" class="hiddenAnswer" value="<%= answer %>" name="hiddenAnswer<%=i%>">
													
						<%
							}
						 	break;
							case 3: //MC  %>
								<h3>Question: <%= ((MultipleChoiceQuestion)question).getQuestion() %></h3>
								<input type="hidden" value="<%= question.getQuestionType() %>" name="questionType">
								<input type='hidden' value='<%=i%>' class="qtnNum">
								
						<%		String typeOfInput = "radio";
								if(question.getNumAnswers() > 1) {
									typeOfInput = "checkbox";
								}
								for (String choice : ((MultipleChoiceQuestion)question).getChoices()) {
						%>
								<div class='row'>
									<div class='col-lg-6'>
										<div class='input-group'>
											<span class='input-group-addon'><input class='mult_choice_checkbox' type='<%= typeOfInput %>' name="checkbox_radio_btn" ></span><input type='text' value="<%= choice %>" class='form-control answerField' readonly >
										</div>
									</div>
								</div>
											
						<%
								}
							for (String answer : question.getAnswers()) {
						%>
							<input type="hidden" class="hiddenAnswer" value="<%= answer %>" name="hiddenAnswer<%=i%>">
													
						<%
							}
							break;
							case 4: //PR %>
								<h3>Question: </h3><img alt="" src="<%=((PictureResponseQuestion)question).getURL() %>"><br><br>
						<%		for(int answerNum = 0; answerNum < question.getScore(); answerNum++) { %>
									<label for="answerField">Answer:&nbsp;</label><input type="text" class="answerField" name="answerField<%=i%>">
						<% 		}
							
							for (String answer : question.getAnswers()) {
						%>
							<input type="hidden" class="hiddenAnswer" value="<%= answer %>" name="hiddenAnswer<%=i%>">
													
						<%
							}	
						  break;
						  
							}
						%>
						<ul class="pager">
						  <li class="previous"><a href="#">&larr; Older</a></li>
						  <li class="next"><a href="#">Newer &rarr;</a></li>
						</ul>
						<div class="feedback"></div>
						</div>  <!-- Close question div -->
						<%
				} //end for loop	%>
				
				
				<% 
			} else {
				for(int i = 0; i < questions.size(); i++)  {
					Question question = questions.get(i);
						%>
						<div class='questionn'>
						<%
							switch (QuestionTypes.getType(question.getQuestionType())) {
							
							case 1:	//QR %>
								<h3>Question: <%= ((QuestionResponse)question).getQuestion() %></h3>
						<%		for(int answerNum = 0; answerNum < question.getNumAnswers(); answerNum++) { %>
									<label for="answerField">Answer:&nbsp;</label><input type="text" class="answerField" name="answerField<%=i%>">
						<% 		}
						
							for (String answer : question.getAnswers()) {
						%>
							<input type="hidden" class="hiddenAnswer" value="<%= answer %>" name="hiddenAnswer<%=i%>">
													
						<%
							}
							break;
							
							case 2: //FIB %>
								<h3>Question: <%= ((FillBlankQuestion)question).getQuestion() %></h3>
						<%		for(int answerNum = 0; answerNum < question.getNumAnswers(); answerNum++) { %>
									<label for="answerField">Answer:&nbsp;</label><input type="text" class="answerField" name="answerField<%=i%>">
						<% 		}
							for (String answer : question.getAnswers()) {
						%>
							<input type="hidden" class="hiddenAnswer" value="<%= answer %>" name="hiddenAnswer<%=i%>">
													
						<%
							}
						 	break;
							case 3: //MC  %>
								<h3>Question: <%= ((MultipleChoiceQuestion)question).getQuestion() %></h3>
								<input type="hidden" value="<%= question.getQuestionType() %>" name="questionType">
								<input type='hidden' value='<%=i%>' class="qtnNum">
								
						<%		String typeOfInput = "radio";
								if(question.getNumAnswers() > 1) {
									typeOfInput = "checkbox";
								}
								for (String choice : ((MultipleChoiceQuestion)question).getChoices()) {
						%>
									<div class='row'>
										<div class='col-lg-6'>
											<div class='input-group'>
												<span class='input-group-addon'><input class='mult_choice_checkbox' type='<%= typeOfInput %>' name="checkbox_radio_btn"></span><input type='text' value="<%= choice %>" class='form-control answerField' readonly >
											</div>
										</div>
									</div>
						<%
								}
							for (String answer : question.getAnswers()) {
						%>
							<input type="hidden" class="hiddenAnswer" value="<%= answer %>" name="hiddenAnswer<%=i%>">
													
						<%
							}
							break;
							
							case 4: //PR %>
								<h3>Question: </h3><img alt="" src="<%=((PictureResponseQuestion)question).getURL() %>"><br><br>
						<%		for(int answerNum = 0; answerNum < question.getNumAnswers(); answerNum++) { %>
									<label for="answerField">Answer:&nbsp;</label><input type="text" class="answerField"  name="answerField<%=i%>">
						<% 		}
							for (String answer : question.getAnswers()) {
						%>
							<input type="hidden" class="hiddenAnswer" value="<%= answer %>" name="hiddenAnswer<%=i%>">
													
						<%
							}	
						  break;	
							}
						%>
						<div class="feedback"></div>
						</div> <!-- Close question div -->
						<%
				} //end for loop	%>
				<button type="button" onclick="submitTakeQuizForm()" id="submitbtn">Submit</button>
				<% 
			}
			%>
			</form>
          </div>
        </div>
      </div>



	  
      <script type='text/javascript' src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
      <script type='text/javascript' src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>  
      <% if(curQuiz.getMultiplePages()) { %>
      <script type='text/javascript' src="/Quizzer/js/multiple_pages.js"></script>  
      <% } %>
      <script type='text/javascript' src="/Quizzer/js/source.js"></script>  
      <% if(curQuiz.getImmediateCorrection()) { %>
      <script type='text/javascript' src="/Quizzer/js/instant.js"></script>  
      <% } %>
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