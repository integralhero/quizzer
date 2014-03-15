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


});

