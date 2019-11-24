// съкратен синтаксис на document.ready
$(function() {
	$.ajax({
		url: "/whoAmI",
		method: "GET",
		success: function(data) {
			$("#register-user").val(data.username);
			$("#register-email").val(data.email);
		},
		fail: function() {
			window.location.href = "index.html";
		}
	});
});

$("#updateProfile").click(function() {
	$.ajax({
		url: "profile/updateMyProfile",
		method: "PUT",
		data: {
			username: $("#register-user").val(),
			email: $("#register-email").val(),
			password: $("#register-pass").val(),
			rePassword: $("#confirm-register-pass").val()
		},
		success: function() {
			alert("Success");
			$("#register-pass").val("");
			$("#confirm-register-pass").val("");
		},
		fail: function() {
			alert("Error");
		}
	});
});