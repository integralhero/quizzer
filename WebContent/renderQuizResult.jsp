<%@ page import="quiz.*, java.util.*, javax.*" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"> 
        <meta charset="utf-8">
        <title>Quiz Results</title>
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
             <% 
             	int curUserID = us.getUserid();
             	Quiz curQuiz = (Quiz)request.getAttribute("curQuiz");
             	ArrayList<String[]> userAns = (ArrayList<String[]>)request.getAttribute("userAnswers");
               %>
			<h1>Quiz Title:<%= curQuiz.getName() %></h1>
			<%
			ArrayList<Question> questions = curQuiz.getQuestions();
			
			%>
			<input type=hidden value="<%= questions.size() %>" name="num_questions">
			<%
				for(int i = 0; i < questions.size(); i++)  {
					Question question = questions.get(i);
					ArrayList<String> answers = question.answers; 
					String[] userAnsCurQ = userAns.get(i);
					String[] rightAnswers = answers.toArray(new String[answers.size()]);
						%>
						<div class='question'>
						<%
							switch (QuestionTypes.getType(question.getQuestionType())) {
							case 1:	//QR %>
								<h3>Question: <%= ((QuestionResponse)question).getQuestion() %></h3>
								Your answer: <%= Arrays.toString(userAnsCurQ) %>
								Correct answer: <%= Arrays.toString(rightAnswers) %>
						<% 
							break;
							case 2: //FIB %>
								<h3>Question: <%= ((FillBlankQuestion)question).getQuestion() %></h3>
								Your answer: <%= Arrays.toString(userAnsCurQ) %>
								Correct answer: <%= Arrays.toString(rightAnswers) %>
								<% 
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
											<span class='input-group-addon'><input class="mult_choice_checkbox" type='<%= typeOfInput %>' ></span><input type='text' value="<%= choice %>" class='form-control answerField' readonly >
										</div>
									</div>
								</div>
											
						<%
								}
								%> Your answer: <%= Arrays.toString(userAnsCurQ) %>
								Correct answer: <%= Arrays.toString(rightAnswers) %> <%
							break;
							case 4: //PR %>
								<h3>Question: </h3><img alt="" src="<%=((PictureResponseQuestion)question).getURL() %>"><br><br>					
								Your answer: <%= Arrays.toString(userAnsCurQ) %>
								Correct answer: <%= Arrays.toString(rightAnswers) %>
								<% 
						  break;	
							}
						%>
						
						</div>  <!-- Close question div -->
						<%
				} //end for loop	%>
			<% int j = (Integer)request.getAttribute("score"); %>
			<h1>Final Score: <%= j %></h1>
			<form  action ="ReviewServlet" method = "post">
			
				<textarea rows="6" cols="75" name="review" placeholder='Write a review of this quiz.'></textarea>
				<input type="hidden" name="quizid" value="<%= curQuiz.getID() %>">
				<input type="hidden" name="userid" value="<%= us.getUserid() %>">
				<p>Rate This Quiz From 1 to 5</p>
   					<select name="rate">
 						 <option value="1">1</option>
  					     <option value="2">2</option>
  						 <option value="3">3</option>
                         <option value="4">4</option>
                         <option value="5">5</option>
                    </select>

  				<button type="submit">Submit</submit>
  			</form>
			
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