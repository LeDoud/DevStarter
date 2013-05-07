$(document).ready(
				function() {
					$.validator.addMethod("email-unicity",
							function(value, element) {
								var test=true;
								$.ajax({
			                        url: 'user/checkEmailUnicity',
			                                data: {subEmail:value},
			                                async: false,
			                                type: "POST",
			                                success: function (result) {
			                                	if(result === value)
												  {
													test= false;
												  }
												  else
												  {
													test= true;
											      }
			                                }
			                        });	
								
				        		return test;
				    		},"This mail is already used");	
					
					
					$('#signup-with-email').validate(
							{
								rules : {
									firstname : {
										minlength : 2,
										required : true,
									},
									lastname : {
										minlength : 2,
										required : true,
									},
									password : {
										minlength : 6,
										maxlength : 10,
										required : true,
									},
									email : {
										required : true,
										email : true,
										"email-unicity" : true,
									},
									confirm_email : {
										required : true,
										equalTo : "#signup-email",
									},

									birth_date : {
										required : true,
										age13 : true,
										date : true,
									},
				
								},
								highlight : function(element) {
									$(element).closest('.control-group')
											.removeClass('success').addClass(
													'error');
								},
								success : function(element) {
									element.text('OK!').addClass('valid')
											.closest('.control-group')
											.removeClass('error').addClass(
													'success');
								}
							});
				});