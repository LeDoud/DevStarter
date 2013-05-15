// Additional JS functions here
window.fbAsyncInit = function() {
	FB.init({
		appId : '190681314417918', // App ID
		channelUrl : 'http://localhost:8080/DevStarter', // Channel File
		status : true, // check login status
		cookie : true, // enable cookies to allow the server to access the
						// session
		xfbml : true
	// parse XFBML
	});

	FB.Event.subscribe('auth.authResponseChange', function(response) {

		if (response.status === 'connected') {

			login();
		} else if (response.status === 'not_authorized') {

			FB.login();
		} else {

			FB.login();
		}
	});
};

// Load the SDK asynchronously
(function(d) {
	var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
	if (d.getElementById(id)) {
		return;
	}
	js = d.createElement('script');
	js.id = id;
	js.async = true;
	js.src = "//connect.facebook.net/en_US/all.js";
	ref.parentNode.insertBefore(js, ref);
}(document));

function login() {
	console.log('Welcome!  Fetching your information.... ');

	FB.api('/me', function(response) {
		$.ajax({
			url : '/DevStarter/user/signinFacebook',
			data : {
				firstName : response.first_name,
				lastName : response.last_name,
				email : response.email,
				password : response.id
			},
			async : false,
			type : "POST",
			success : function(result) {
				if (result === 'refresh') {
					window.location.reload();
				}
			}
		});

	});
}

function logout() {

	FB.api('/me/permissions', 'delete', function(response) {
		$.ajax({
			url : '/DevStarter/user/logout',
			data : {},
			async : false,
			type : "POST",
			success : function(result) {
				window.location.reload();
			}

		});
	});
}