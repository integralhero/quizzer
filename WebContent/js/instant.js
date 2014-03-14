$(document).ready(function(){
	
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

