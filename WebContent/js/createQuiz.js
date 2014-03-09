var questionCount = 1;
var mult_choice_answer_count = 1;

$(document).ready(function() {
	$(document).on('click', '.question_delete_btn', function(event) {
		$(this).parent().closest("div").remove();
	});
});



function removeQuestion() {
	console.log("in button clicked");
	console.log($('this'));
	$('this').parent().closest("div").remove();
}

function addAnswer() {
	$("<div class='row'><div class='col-lg-6'><div class='input-group'><span class='input-group-addon'><input name='mult_choice_checkbox_" + mult_choice_answer_count + "' type='checkbox'></span><input name='mult_choice_answer_" + mult_choice_answer_count + "' type='text' class='form-control' ></div></div></div>").insertBefore('#add_answer_btn');
	var tempQuestionCount = questionCount - 1;
	$("#mult_choice_answer_count_" + tempQuestionCount).val(mult_choice_answer_count);
	mult_choice_answer_count++;
}

//private static final int QUESTION_RESPONSE = 1;
//private static final int FILL_BLANK = 2;
//private static final int MULT_CHOICE = 3;
//private static final int PIC_RES = 4;



$('#qtn_res_qtn').click(function(event) {
	
   $("<div id='question" + questionCount + "'>" + 
		   "<div class='input-group'><h3>" + questionCount + ".) Question-Response <button type='button' class='question_delete_btn btn btn-default btn-sm'>" + 
		   "<span class='glyphicon glyphicon-remove-circle'></span> Delete </button></h3>" + 
		   "<input type='text' class='form-control quiz_qtn_field' name='question" + questionCount + "' placeholder='Question'>&nbsp;" + 
		   "<input type='text' class='form-control quiz_qtn_field' name='answer" + questionCount + "' placeholder='Response'>&nbsp;" + 
		   "</div></div>").insertBefore("#question_count_field");
   $("#question" + questionCount).after("<input type='hidden' name='question_type_" + questionCount + "' value='1'>");
   $('#question_count_field').val(questionCount); 
   questionCount++;
});

$('#fill_blank_qtn').click(function(event) {
   $("<div id='question" + questionCount + "'><div class='input-group'><h3>" + questionCount + ".) Fill in the Blank</h3><input type='text' class='form-control quiz_qtn_field' name='question" + questionCount + "' placeholder='Sentence'>&nbsp;<input type='text' class='form-control' name='answer" + questionCount + "' placeholder='Word to be left blank'>&nbsp;</div></div>").insertBefore("#question_count_field");
   $("#question" + questionCount).after("<input type='hidden' name='question_type_" + questionCount + "' value='2'>");
   $('#question_count_field').val(questionCount); 
   questionCount++;
});

$('#mult_choice_qtn').click(function(event) {
	if ($('#add_answer_btn').length != 0) {
		$('#add_answer_btn').remove();
	}
	$("<div id='question" + questionCount + "'><div class='input-group'><h3>" + questionCount + ".) Multiple Choice</h3><input type='text' class='form-control quiz_qtn_field' name='question" + questionCount + "' placeholder='Question'><h4>Answers:</h4><h5>Please select the correct answer</h5><button onclick='addAnswer()' type='button' id='add_answer_btn' class='btn btn-default'>Add Answer</button><br><br></div></div>").insertBefore("#question_count_field");
	$("#question" + questionCount).after("<input type='hidden' id='question_type_" + questionCount + "' name='question_type_" + questionCount + "' value='3'>");
	
	$("#question_type_" + questionCount).after("<input type='hidden' id='mult_choice_answer_count_" + questionCount + "' name='mult_choice_answer_count_" + questionCount + "' value='0'>");

	$('#question_count_field').val(questionCount);
	questionCount++;
});

$('#pic_res_qtn').click(function(event) {
	
	$("<div id='question" + questionCount + "'><div class='input-group'><h3>" + questionCount + ".) Picture-Response</h3><input type='text' class='form-control quiz_qtn_field' name='question" + questionCount + "' placeholder='Image URL'><h4>Or load image file:</h4><input type='file' >&nbsp;<input type='text' class='form-control quiz_qtn_field' name='answer" + questionCount + "' placeholder='Response'>&nbsp;</div></div>").insertBefore("#question_count_field");
	$("#question" + questionCount).after("<input type='hidden' name='question_type_" + questionCount + "' value='4'>");
	$('#question_count_field').val(questionCount);
	questionCount++;
});

