var me;

function getWhoAmI() {
	$.ajax({
		url: "/whoAmI",
		method: "GET",
		success: function(data) {
			me = data.id;
		},
		fail: function() {
			window.location.href = "index.html";
		}
	});
}

function getAllComments() {
	$.ajax({
		url: "/comment/all",
		method: "GET",
		success: function(data) {
			data.forEach(function(obj) {
				addCommentTemplate(obj.id, obj.city, obj.comment, obj.temp, obj.user.id);
			});
		},
		fail: function(err) {
			alert(err);
		}
	});
}

function addCommentTemplate(id, city, comment, temp, userId) {
	var miniMe = $('#cloneMe').clone();
	
	miniMe.attr('id', id);
	miniMe.find('h4').text(city);
	miniMe.find('p').html(comment);
	miniMe.find('.current-temp').html(temp);
	
	// ако коментара е на логнатия потребител
	//if(me == userId) {
		miniMe.find('button').click(function (){
			deleteComment(miniMe, id);
		});
	//} else {
	//	miniMe.find('button').hide();
	//}
	
	miniMe.show();
	
	$('#contentList').append(miniMe);
}

function deleteComment(miniMe, id) {
	$.ajax({
		url: "/comment/delete",
		method: "DELETE",
		data: {
			id: id
		},
		// complete, за да хванем статуса
		complete: function(data) {
			switch(data.status) {
				case 200:
					miniMe.remove();
					break;
				case 401:
					window.location.href = "index.html";
					break;
				case 404:
					alert("There is no such comment! Are you drunk?!");
					break;
				case 418:
					alert("Malicious intention detected! The polica has been informed!");
					break;
			}
		},
	});
}

function addComment(comment, city, temp, image) {
	$.ajax({
		url: "/comment/add",
		method: "POST",
		data: {
			comment: comment,
			selectCity: city,
			temp: temp,
			picture: image
		},
		success: function(data) {
			if(data == "error") {
				window.location.href = "index.html";
			} else if(data == -1) {
				alert("Insertion of the comment failed, please try again");
			} else {
				addCommentTemplate(data, city, comment, temp, me);
				
				$('#comment').val("");
			}
		},
		fail: function() {
			alert("something went wrong!");
		}
	});
}

function logout() {
	$.ajax({
		method: "POST",
		url: "profile/logout",
		success: function() {
			window.location.href = "index.html";
		},
		error: function() {
			alert("Unauthorized");
		}
	});
}

$(document).ready(function (){
	getWhoAmI(); // взимаме ID-то на логнатия потребител
	getAllComments(); // зареждаме всички коментари
	
	var apiid = "891be3d8ea6c5763c5d8e6ad1267c77b";

	setInterval(function() {
		getPlovdivTemp(apiid);
	}, 5000);
	

	$('#postComment').click(function (){
		cityName = $('#select-city').val();

		$.ajax({
			method: "GET",
			url: "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiid + "&units=metric",
			success: function(resp){
				addComment($('#comment').val(), cityName, resp.main.temp, "no_image");
			},
			error: function(){
				alert("Something went TEREBLY WRONG!!!! \nYOU BROKE IT!");
			}	
		});
		
	});
	
});

function getPlovdivTemp(apiid) {
	$.ajax({
		method: "GET",
			url: "http://api.openweathermap.org/data/2.5/weather?q=" + "plovdiv" + "&appid=" + apiid + "&units=metric",
			success: function(resp){
				$("#current-temperature").html(resp.main.temp);
			},
			error: function(){
				//
			}	
	});
}