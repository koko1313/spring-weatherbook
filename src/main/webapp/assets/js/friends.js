$("#searchMe").click(function() {
	var username = $("#search-friend").val();
	
	$.ajax({
		method: "GET",
		url: "/user/search",
		data: {
			username: username
		},
		complete: function(data) {
			switch(data.status) {
				case 200:
					// изчистваме намерените до момента резултати, запазвайки шаблона
					var userResult = $("#cloneMe").clone();
					$("#friendsList").html("");
					$("#friendsList").append(userResult);
					
					var users = data.responseJSON;
					
					for(var i = 0; i< users.length; i++) {
						var userResult = $("#cloneMe").clone();
						userResult.removeAttr("id");
						userResult.find("#username").text(users[i].username);
						userResult.show();
						$("#friendsList").append(userResult);
					}
					break;
				case 401:
					window.location.href = "index.html";
					break;
			}
		}
	});
});