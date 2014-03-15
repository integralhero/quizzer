<%@ page import="java.util.*, quiz.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"> 
        <meta charset="utf-8">
        <title>Edit Quiz</title>
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
	              <form action="LogoutServlet" method="get">
	                <button type="submit" class='btn-default'>Logout</button>
	             </form>
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
             	int curNumQuestions = curQuiz.questions.size();
             	
           %>
          
          	<h1>Edit Quiz</h1>
          	 
          	
          	
			<form name="create_quiz_form" action="/Quizzer/UpdateQuizServlet" method="post" > 
				<label for="quiz_name_field">Quiz Name:</label><input type='text' id="quiz_name_field" name='quiz_name_field' class='form-control ' placeholder='Quiz Name' value="<%= curQuiz.getName() %>" style="width:50%;">
				<input type="hidden" name="quizID" value="<%= curQuiz.getID() %>">
				&nbsp;
				<BR>
				Description: <input type="text" name="description" style="width:50%;" value="<%=curQuiz.getDescription() %>"><BR>
				Category: <input type="text" name="category">
				<HR>
				<% 
				final int QUESTION_RESPONSE = 1;
				final int FILL_BLANK = 2;
				final int MULT_CHOICE = 3;
				final int PIC_RES = 4;
				int questionCount = 1;

				for (Question question : curQuiz.questions) {
					int questionType = QuestionTypes.getType(question.getQuestionType());
					switch (questionType) {
						case QUESTION_RESPONSE:
				%>
						<div id='question<%= questionCount %>'>
							<div class='input-group'><h3>Question-Response <button type='button' class='question_delete_btn btn btn-default btn-sm'>
								<span class='glyphicon glyphicon-remove-circle'></span> Delete </button></h3> 
								<input type='text' class='form-control quiz_qtn_field' name='question<%= questionCount %>' placeholder='Question' value='<%= ((QuestionResponse)question).getQuestion() %>'>&nbsp;
				<%
								for (String answer : ((QuestionResponse)question).getAnswers()) {
				%>
									<input type='text' class='form-control quiz_qtn_field' name='answer<%= questionCount %>' placeholder='Response' value='<%= answer %>'>&nbsp;
				<%
								}
				%> 
							</div>
							<input type='hidden' name='question_type_<%= questionCount %>' value='1'>
						</div>
				<%
							break;
						case FILL_BLANK:
				%>
					   	<div id='question<%= questionCount %>'>
							<div class='input-group'>
								<h3>Fill in the Blank</h3>
								<input type='text' class='form-control quiz_qtn_field' name='question<%= questionCount %>' placeholder='Sentence' value='<%= ((FillBlankQuestion)question).getQuestion() %>'>&nbsp;
				<%
								for (String answer : ((FillBlankQuestion)question).getAnswers()) {
				%>
									<input type='text' class='form-control' name='answer<%= questionCount %>' placeholder='Word to be left blank' value='<%= answer %>'>&nbsp;
				<%
								}
				%> 
							   	</div>
							<input type='hidden' name='question_type_<%= questionCount %>' value='2'>
						</div>
				<%
							break;
						case MULT_CHOICE:
				%>
						<div id='question<%= questionCount %>'> 
							<div class='input-group'>
								<h3>Multiple Choice</h3> 
								<input type='text' class='form-control quiz_qtn_field' name='question<%= questionCount %>' placeholder='Question' value='<%= ((MultipleChoiceQuestion)question).getQuestion() %>'> 
				<%
								int multipleChoiceAnswerCount = 0;
								ArrayList<String> correctAnswersArray = question.getAnswers();
								HashSet<String> correctAnswersSet = new HashSet<String>(correctAnswersArray);

								for (String choice : ((MultipleChoiceQuestion)question).getChoices()) {									
				%>
									<div class='row'>
										<div class='col-lg-6'>
											<div class='input-group'>
								<%
											if(correctAnswersSet.contains(choice)) {
								%>
												<span class='input-group-addon'><input name='mult_choice_checkbox_<%= multipleChoiceAnswerCount %>' type='checkbox' checked></span>
								<%
											} else {
								%>
												<span class='input-group-addon'><input name='mult_choice_checkbox_<%= multipleChoiceAnswerCount %>' type='checkbox'></span>
								<%
											}
								%>
								
												<input name='mult_choice_answer_<%= multipleChoiceAnswerCount %>' type='text' class='form-control' value='<%= choice %>' >
											</div>
										</div>
									</div>
				<%
									multipleChoiceAnswerCount++;
								}
				%> 
								
								<button onclick='addAnswer()' type='button' id='add_answer_btn' class='btn btn-default'>Add Answer</button><br><br> 
							</div>
							<input type='hidden' id='question_type_<%= questionCount %>' name='question_type_<%= questionCount %>' value='3'>
							<input type='hidden' id='mult_choice_answer_count_<%= questionCount %>' name='mult_choice_answer_count_<%= questionCount %>' value='<%=multipleChoiceAnswerCount %>'>
						</div>
				<%
							break;
						case PIC_RES:
				%>
							<div id='question<%= questionCount %>'>
								<div class='input-group'>
									<h3>Picture-Response</h3>
									<input type='text' class='form-control quiz_qtn_field' name='question<%= questionCount %>' placeholder='Image URL' value='<%= ((PictureResponseQuestion)question).getURL() %>'>
									<h4>Or load image file:</h4>
									<input type='file' >&nbsp;
				<%
								for (String answer : ((PictureResponseQuestion)question).getAnswers()) {
				%>
									<input type='text' class='form-control' name='answer<%= questionCount %>' placeholder='Response' value='<%= answer %>'>&nbsp;
				<%
								}
				%> 
									
								</div>
								<input type='hidden' name='question_type_<%= questionCount %>' value='4'>
							</div>
				
				<%
							break;
						default: break;

					} // close switch statement
					questionCount++;
				} // close for loop
				curNumQuestions++;
				%>
				<input type='hidden' id='question_count_field' name='question_count_field' value = <%= curNumQuestions %>>
	          	<div id="add_qtn_btn">
		          	<div class="row">
					  <div class="col-lg-6">
					    <div class="input-group">
					      <div class="input-group-btn">
					        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">Add Question <span class="caret"></span></button>
					        <ul class="dropdown-menu">
					          <li><a id="qtn_res_qtn" href="#">Question-Response</a></li>
					          <li><a id="fill_blank_qtn" href="#">Fill in the Blank</a></li>
					          <li><a id="mult_choice_qtn" href="#">Multiple Choice</a></li>
					          <!--<li><a id="mult_choice_mult_ans_qtn" href="#">Multiple Choice w/ Multiple Answers</a></li>-->
					          <li><a id="pic_res_qtn" href="#">Picture Response</a></li>
					          
					           <!-- <li class="divider"></li>
					          <li><a href="#">Separated link</a></li>  -->
					        </ul>
					      </div><!-- /btn-group -->
					    </div> <!-- /input-group --> 
					  </div> <!-- /.col-lg-6 --> 
					</div> <!-- / row --> 
				</div> <!-- /add_qtn_btn -->
				<HR>
				Randomize Questions: <input type="checkbox" name="randomize"><BR>
				Multiple Pages: <input type="checkbox" name="mult_pages"><BR>
				Immediate Feedback: <input type="checkbox" name="feedback"><BR>
				Allow Practice Mode: <input type="checkbox" name="practice"><BR>
				<HR>
				<input type="submit" class="btn btn-default">
			  
			  </form>
			  
          		
			


          </div>
        </div>
      </div>

      <script type='text/javascript' src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
      <script type='text/javascript' src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
      <script type='text/javascript' src="../js/createQuiz.js"></script>
      <script type='text/javascript' src="../js/editQuiz.js"></script> 
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