$(document).ready(function(){

//	multiple choice checkbox code
	$(document).on('click', '.mult_choice_checkbox', function() {
		
		var qtnNum = $(this).parent().parent().parent().parent().parent().find("input.qtnNum").val();
		
		if ($(this).is(":checked")) {
			$(this).parent().siblings("input.answerField").attr("name", "answerField" + qtnNum);
		} else {
			$(this).parent().siblings("input.answerField").removeAttr("name");
		}
	});
	var date = new Date($.now());
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	var hour = date.getHours();
	var minutes = date.getMinutes();
	var seconds = date.getSeconds();
	
	var dateString = year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
	
	$('#dateTaken').val(dateString);
	
	var startTime = date.getTime();
	
	$('#time').val(startTime);
	


});


function submitTakeQuizForm() {
	
	var endTime = (new Date($.now())).getTime();
	var timeElapsed = endTime - parseInt($('#time').val());
	$('#time').val(timeElapsed);
	
	$('#take_quiz_form').submit();

}