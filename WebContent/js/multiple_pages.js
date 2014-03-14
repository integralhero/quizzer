$(document).ready(function(){
	
	$('div.question:gt(0)').hide();
	$('div.question:lt(1) > ul li.previous').hide();
	$('div.question:nth-last-child(1) > ul li.next').hide();
	$('div.question:last-child').append("<button type='button' onclick='submitTakeQuizForm()' id='submitbtn'>Submit</button>");

	$(document).on('click', '.next', function() {
		$(this).parent().closest("div.question").hide();
		$(this).parent().closest("div.question").next().show();
	});
	$(document).on('click', '.previous', function() {
		$(this).parent().closest("div.question").hide();
		$(this).parent().closest("div.question").prev().show();
	});
	$(document).on('keyup', 'input.answerField', function(){
		
		var sibs = $(this).siblings('.hiddenAnswer');
		var userResp = $(this).val();
		var isCorrect = false;
		$(sibs).each(function() {
			if($(this).val() == userResp) {
				isCorrect = true;
				
			}			
		});
		if(isCorrect) {
			$(this).siblings('.feedback').html("Correct!");
		}
		else {
			$(this).siblings('.feedback').html("Wrong!");
		}
		
	});
	
//	multiple choice checkbox code
	$(document).on('click', '.mult_choice_checkbox', function() {
		console.log($(this).parent().parent().parent().parent().parent().find("input.qtnNum").val());
		var qtnNum = $(this).parent().parent().parent().parent().parent().find("input.qtnNum").val();
		console.log("QuestionNum: " + qtnNum);
		if ($(this).is(":checked")) {
			$(this).parent().siblings("input.answerField").attr("name", "answerField" + qtnNum);
		} else {
			$(this).parent().siblings("input.answerField").removeAttr("name");
		}
	});
	var date = new Date($.now());
	var year = date.getFullYear();
	var month = date.getMonth();
	var day = date.getDate();
	var hour = date.getHours();
	var minutes = date.getMinutes();
	var seconds = date.getSeconds();
	
	var dateString = year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
	$('#dateTaken').val(dateString);
	
	var startTime = date.getTime();
	$('#time').val(startTime);
	
//	$(document).on('submit', '#take_quiz_form', function(e) {
//		
//		e.preventDefault();
//		console.log("hello");
//		var endTime = (new Date($.now())).getTime();
//		var timeElapsed = endTime - parseInt($('#time').val());
//		$('#time').val(timeElapsed);
//		
//		$('#take_quiz_form').submit();
//	});
	
//	alert(date);

});


function submitTakeQuizForm() {
	console.log("hello");
	var endTime = (new Date($.now())).getTime();
	var timeElapsed = endTime - parseInt($('#time').val());
	$('#time').val(timeElapsed);
	
	$('#take_quiz_form').submit();

}