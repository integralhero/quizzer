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
	

});

