$(document).ready(function(){
	$('div.question:gt(0)').hide();
	$(document).on('click', '.next', function() {
		$(this).parent().closest("div.question").hide();
		$(this).parent().closest("div.question").next().show();
	});
	$(document).on('click', '.previous', function() {
		$(this).parent().closest("div.question").hide();
		$(this).parent().closest("div.question").prev().show();
	});
});