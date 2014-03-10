$(document).ready(function(){
	
	$('div.question:gt(0)').hide();
	$('div.question:lt(1) > ul li.previous').hide();
	$('div.question:last-child > ul li.next').hide();
	
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
		var qtnNum = $(this).parent().parent().parent().parent().parent().find("input.qtnNum").val();
		if ($(this).is(":checked")) {
			$(this).parent().siblings("input.answerField").attr("name", "answerField" + qtnNum);
		} else {
			$(this).parent().siblings("input.answerField").removeAttr("name");
		}
	});

});