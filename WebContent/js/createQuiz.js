
var mult_choice_answer_count = 1;

var questionCount = 0;
$(document).ready(function() {
	questionCount = $('#question_count_field').val();

	$(document).on('click', '.question_delete_btn', function(event) {
		$(this).parent().parent().parent().closest("div").remove();
	});
	
	$(document).on('click', '.addAnswerField', function(event) {
		var id = $(this).parent().parent().attr("id").substr(8);
		$("<div class='row'>" + 
				"<input type='text' class='form-control quiz_qtn_field' name='answer" + id + "' placeholder='Answer'>&nbsp;" + 
		"</div>").insertBefore("#add_answer_btn" + id);

	});
	
	$(document).on('click', '#add_tag_input', function(event) {
		
		$("<div class='row'>" + 
				"<input type='text' class='form-control' name='tag' placeholder='Input Tag'>&nbsp;" + 
		"</div>").insertAfter('#add_tag_input');

	});
	
});
	
//});

function submitCreateQuizForm() {
	var inputIsValid = true;
	if ($('#quiz_name_field').val() == "") {
		
		alert("Quiz Name Field Left Empty!")
		inputIsValid = false;
	} else if ($('#description').val() == "") {
		
		alert("Description Field Left Empty!")
		inputIsValid = false;
	} else if ($("#category").val() == "") {
		alert("Category field left empty!");
		inputIsValid = false;
	}
	
	var numQuestions = $('#question_count_field').val();

	var questions = $('.quiz_qtn_field');

	for (var questionCount = 0; questionCount < numQuestions; questionCount++) {
		
		if ($(questions[questionCount]).val() == "") {
			alert("Question " + (questionCount + 1) + " left blank!");
			inputIsValid = false;
		} else if ($("#maxScore" + (questionCount + 1)).val() == "") {
			alert("Max Score field left empty!");
			inputIsValid = false;
		}
	}
	if (inputIsValid) {
		$('#create_quiz_form').submit();
	} else {

	}
}


function removeQuestion() {
	
	

	$(this).remove();

}



function addAnswer() {
	$("<div class='row'>" + 
			"<div class='col-lg-6'>" + 
				"<div class='input-group'>" + 
					"<span class='input-group-addon'>" + 
						"<input name='mult_choice_checkbox_" + mult_choice_answer_count + "' type='checkbox'>" + 
					"</span>" +
					"<input name='mult_choice_answer_" + mult_choice_answer_count + "' type='text' class='form-control' >" +
				"</div>" +
			"</div>" +
		"</div>").insertBefore("#add_answer_btn");
	var tempQuestionCount = questionCount - 1;
	$("#mult_choice_answer_count_" + tempQuestionCount).val(mult_choice_answer_count);
	mult_choice_answer_count++;
}

function addQuestionResponse() { // non multiple choice
	
//	$("<div class='row'>" + 
//	   		"<input type='text' class='form-control quiz_qtn_field' name='answer" + questionCount + "' placeholder='Answer'>&nbsp;" + 
//		"</div>").insertBefore('#add_answer_btn');
//	var tempQuestionCount = questionCount - 1;
//	$("#mult_choice_answer_count_" + tempQuestionCount).val(mult_choice_answer_count);
//	mult_choice_answer_count++;
}



//function addSingleAnswer() {
//	$("<div class='row'><div class='col-lg-6'><div class='input-group'><span class='input-group-addon'><input name='mult_choice_radio' type='radio' value='" + mult_choice_answer_count + "'></span><input name='mult_choice_answer_" + mult_choice_answer_count + "' type='text' class='form-control' ></div></div></div>").insertBefore('#add_answer_btn');
//	var tempQuestionCount = questionCount - 1;
//	$("#mult_choice_answer_count_" + tempQuestionCount).val(mult_choice_answer_count);
//	mult_choice_answer_count++;
//}


$('#qtn_res_qtn').click(function(event) {
   $("<div id='question" + questionCount  + "'>" + 
		   "<div class='input-group'>" +
		   		"<h3>Question-Response <button type='button' class='question_delete_btn btn btn-default btn-sm'><span class='glyphicon glyphicon-remove-circle'></span> Delete </button></h3>" + 
		   		"<input type='text' class='form-control quiz_qtn_field' name='question" + questionCount + "' placeholder='Question'>&nbsp;" +
		   		"<label for='maxScore'>Max Score (One point per response):</label><input id='maxScore" + questionCount + "' name='maxScore" + questionCount + "' type='text' placeholder='Input Max Score'><br>"+
		   		"<button type='button' id='add_answer_btn" + questionCount + "' class='btn btn-default addAnswerField'>Add Answer</button><br><br>" +
//		   		"<input type='text' class='form-control quiz_qtn_field' name='answer" + questionCount + "' placeholder='Response'>&nbsp;" + 
		   "</div>" +
		   "<input type='hidden' name='question_type_" + questionCount + "' value='1'>" +
   "</div>").insertBefore("#question_count_field");
   
   $('#question_count_field').val(questionCount); 
   questionCount++;
});

$('#fill_blank_qtn').click(function(event) {
   $("<div id='question" + questionCount + "'>" + 
		   "<div class='input-group'>" + 
		   		"<h3>Fill in the Blank <button type='button' class='question_delete_btn btn btn-default btn-sm'><span class='glyphicon glyphicon-remove-circle'></span> Delete </button></h3>" + 
		   		"<input type='text' class='form-control quiz_qtn_field' name='question" + questionCount + "' placeholder='Sentence'>&nbsp;" +
		   		"<label for='maxScore'>Max Score (One point per response):</label><input id='maxScore" + questionCount + "' name='maxScore" + questionCount + "' type='text' placeholder='Input Max Score'><br>"+
		   		"<button type='button' id='add_answer_btn" + questionCount + "' class='btn btn-default addAnswerField'>Add Answer</button><br><br>" +
//		   		"<input type='text' class='form-control' name='answer" + questionCount + "' placeholder='Word to be left blank'>&nbsp;" + 
		   	"</div>" + "<input type='hidden' name='question_type_" + questionCount + "' value='2'>" +
	"</div>").insertBefore("#question_count_field");
   $('#question_count_field').val(questionCount); 
   questionCount++;
});

$('#mult_choice_qtn').click(function(event) {
	if ($('#add_answer_btn').length != 0) {
		$('#add_answer_btn').remove();
	}
	$("<div id='question" + questionCount + "'>" + 
			"<div class='input-group'>" + 
				"<h3>Multiple Choice <button type='button' class='question_delete_btn btn btn-default btn-sm'><span class='glyphicon glyphicon-remove-circle'></span> Delete </button></h3>" + 
				"<input type='text' class='form-control quiz_qtn_field' name='question" + questionCount + "' placeholder='Question'>" + 
				"<h4>Answers:</h4><h5>Please select the correct answer</h4>" + 
				"<button onclick='addAnswer()' type='button' id='add_answer_btn' class='btn btn-default'>Add Answer</button><br><br>" + 
			"</div>" + "<input type='hidden' id='question_type_" + questionCount + "' name='question_type_" + questionCount + "' value='3'>" +
			"<input type='hidden' id='mult_choice_answer_count_" + questionCount + "' name='mult_choice_answer_count_" + questionCount + "' value='0'>" +
		"</div>").insertBefore("#question_count_field");
	
	$('#question_count_field').val(questionCount);
	questionCount++;
});

//$('#mult_choice_qtn').click(function(event) {
//	if ($('#add_answer_btn').length != 0) {
//		$('#add_answer_btn').remove();
//	}
//	$("<div id='question" + questionCount + "'><div class='input-group'><h3>" + questionCount + ".) Multiple Choice</h3><input type='text' class='form-control quiz_qtn_field' name='question" + questionCount + "' placeholder='Question'><h4>Answers:</h4><h5>Please select the correct answer</h5><button onclick='addAnswer()' type='button' id='add_answer_btn' class='btn btn-default'>Add Answer</button><br><br></div></div>").insertBefore("#question_count_field");
//	$("#question" + questionCount).after("<input type='hidden' id='question_type_" + questionCount + "' name='question_type" + questionCount + "' value='3'>");
//	
//	$("#question_type_" + questionCount).after("<input type='hidden' id='mult_choice_answer_count_" + questionCount + "' name='mult_choice_answer_count_" + questionCount + "' value='0'>");
//
//	$('#question_count_field').val(questionCount);
//	questionCount++;
//});




$('#pic_res_qtn').click(function(event) {
	
	$("<div id='question" + questionCount + "'>" +
			"<div class='input-group'>" +
				"<h3>Picture-Response &nbsp;<button type='button' class='question_delete_btn btn btn-default btn-sm'><span class='glyphicon glyphicon-remove-circle'></span> Delete </button></h3>" +
				"<input type='text' class='form-control quiz_qtn_field' name='question" + questionCount + "' placeholder='Image URL'>" +
//				"<h4>Or load image file:</h4>" +
//				"<input type='file' >&nbsp;" +
				"<label for='maxScore'>Max Score (One point per response):</label><input id='maxScore" + questionCount + "' name='maxScore" + questionCount + "' type='text' placeholder='Input Max Score'><br>"+
		   		"<button type='button' id='add_answer_btn" + questionCount + "' class='btn btn-default addAnswerField'>Add Answer</button><br><br>" +
//				"<input type='text' class='form-control quiz_qtn_field' name='answer" + questionCount + "' placeholder='Response'>&nbsp;" +
			"</div>" +
			"<input type='hidden' name='question_type_" + questionCount + "' value='4'>" +
	"</div>").insertBefore("#question_count_field");
	$('#question_count_field').val(questionCount);
	questionCount++;
});

//$('#mult_choice_mult_ans_qtn').click(function(event) {
//	if ($('#add_answer_btn').length != 0) {
//		$('#add_answer_btn').remove();
//	}
//	$("<div id='question" + questionCount + "'><div class='input-group'><h3>" + questionCount + ".) Multiple Choice</h3><input type='text' class='form-control quiz_qtn_field' name='question" + questionCount + "' placeholder='Question'><h4>Answers:</h4><h5>Please select the correct answer</h5><button onclick='addSingleAnswer()' type='button' id='add_answer_btn' class='btn btn-default'>Add Answer</button><br><br></div></div>").insertBefore("#question_count_field");
//	$("#question" + questionCount).after("<input type='hidden' id='question_type_" + questionCount + "' name='question_type_" + questionCount + "' value='5'>");
//	
//	$("#question_type_" + questionCount).after("<input type='hidden' id='mult_choice_answer_count_" + questionCount + "' name='mult_choice_answer_count_" + questionCount + "' value='0'>");
//
//	$('#question_count_field').val(questionCount);
//	questionCount++;
//});

