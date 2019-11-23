$('#register').click(function () {
	$('#register-modal').modal();
});

$("#login-form").submit(function (e) {
	e.preventDefault();
	
	$.ajax({
	    method: "POST",
	    url: "/login",
	    data: { 
	        username: $("#username").val(), 
	        password: $("#password").val()
	    },
	    success: function(data){
	        window.location.replace(data);
	    },
	    fail: function() {
	    	window.location.href = "error.html";
	    }
	});
});