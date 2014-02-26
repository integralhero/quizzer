$('form').submit(function(event) {
	validateForm();

});


function checkPassword()
{
	var pass1 = $('#password').val();
	var pass2 = $('#passwordcheck').val();
    if (pass1 != pass2) {
    	alert("Passwords don't match.");
    	return false;
    }
}

function validateForm() {
	if (fieldsAreEmpty()) {
		alert("Field(s) empty!")
	} else {
		var email = $('#email').val();
		var at = email.indexOf("@");
		var period = email.lastIndexOf(".");
		if (at == -1 || period == -1 || period < 1 || period < at + 2 || period + 2 >= email.length) {
			alert("Not a valid e-mail address.");
			return false;
		}
		
		checkPassword();
	}
}

function fieldsAreEmpty() {
	if ($('#email').val() == "" || $('#username').val() == "" || $('#password').val() == "" || $('#passwordcheck').val() == "") {
		return true;
	}
	return false;
}
