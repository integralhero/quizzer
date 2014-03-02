var questionCount = 1;

$('#qtn_res_qtn').click(function(event) {
   $("<div id='question" + questionCount + "'><div class='input-group'><h3>" + questionCount + ".) Question-Answer</h3><input type='text' class='form-control quiz_qtn_field' placeholder='Question'>&nbsp;<input type='text' class='form-control quiz_qtn_field' placeholder='Response'>&nbsp;</div></div>").insertBefore("#add_qtn_btn");
   questionCount++;
});

$('#fill_blank_qtn').click(function(event) {
	   $("<div id='question" + questionCount + "'><div class='input-group'><h3>" + questionCount + ".) Fill in the Blank</h3><input type='text' class='form-control quiz_qtn_field' placeholder='Sentence'>&nbsp;<input type='text' class='form-control' placeholder='Word to be left blank'>&nbsp;</div></div>").insertBefore("#add_qtn_btn");
	   questionCount++;
});

$('#mult_choice_qtn').click(function(event) {
	$("<div id='question" + questionCount + "'><div class='input-group'><h3>" + questionCount + ".) Multiple Choice</h3><input type='text' class='form-control quiz_qtn_field' placeholder='Question'><h4>Answers:</h4><h5>Please select the correct answer</h5><button onclick='addAnswer()' type='button' id='add_answer_btn' class='btn btn-default'>Add Answer</button><br><br></div></div>").insertBefore("#add_qtn_btn");
	questionCount++;
});

$('#pic_res_qtn').click(function(event) {
	$("<div id='question" + questionCount + "'><div class='input-group'><h3>" + questionCount + ".) Multiple Choice</h3><input type='text' class='form-control quiz_qtn_field' placeholder='Question'><h4>Answers:</h4><h5>Please select the correct answer</h5><button onclick='addAnswer()' type='button' id='add_answer_btn' class='btn btn-default'>Add Answer</button><br><br></div></div>").insertBefore("#add_qtn_btn");
	questionCount++;
});

function addAnswer() {
	$("<div class='row'><div class='col-lg-6'><div class='input-group'><span class='input-group-addon'><input type='checkbox'></span><input type='text' class='form-control' ></div></div></div>").insertBefore('#add_answer_btn');
}
