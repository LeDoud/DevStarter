$(document).ready(function () {  
	  var top = $('#menu').offset().top - parseFloat($('#menu').css('marginTop').replace(/auto/, 0));
	  $(window).scroll(function (event) {
	    // what the y position of the scroll is
	    var y = $(this).scrollTop();
	  
	    // whether that's below the form
	    if (y >= top) {
	      // if so, ad the fixed class
	      $('#menu').addClass('fixed');
	    } else {
	      // otherwise remove it
	      $('#menu').removeClass('fixed');
	    }
	  });
	});