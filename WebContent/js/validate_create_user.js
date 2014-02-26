$('#create_new_user_form').submit(function(event) {
	validateForm();
	event.preventDefault();

});


function checkPassword()
{
	var pass1=document.forms["login"]["password"].value;
	var pass2=document.forms["login"]["passwordcheck"].value;
    if (pass1 != pass2) {
    	alert("Passwords don't match.");
    	return false;
    }
}

function validateForm() {
	alert("in validate form");
	var email = document.forms["login"]["email"].value;
	var at = email.indexOf("@");
	var period = email.lastIndexOf(".");
	if (at == -1 || period == -1 || period < 1 || period < at + 2 || period + 2 >= email.length) {
		alert("Not a valid e-mail address.");
		return false;
	}
	
	checkPassword();
}

function checkEmptyFields() {
	return true;
}
