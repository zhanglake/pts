function doLogin(form)
{
	var hash = hex_md5($('#password').val());
	$('#password').val(hash);
	var alertError = $('.alert-error', $('.login-form'));
	
	//App.blockUI($('.login-form'));
	$(form).ajaxSubmit(
	{
		success:function(rst)
		{
			//App.unblockUI($('.login-form'));
			if(rst.msg == "")
			{
				console.log(rst.url);
				window.location = rst.url;
			}
			else
			{
            	alertError.find("span").text(rst.msg);
                alertError.show();
			}
		 },
		 error:function(msg)
		 {
			 //App.unblockUI($('.login-form'));
			 alertError.find("span").text("网络异常,请联系管理员！");
             alertError.show();
		 }
	});
}

var Login = function () {
    
    return {
        //main function to initiate the module
        init: function () {
        	
           $('.login-form').validate({
	            errorElement: 'label', //default input error message container
	            errorClass: 'help-inline', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                }
	            },

	            messages: {
	                username: {
	                    required: "请输入您的用户名."
	                },
	                password: {
	                    required: "请输入您的密码."
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   
	            	var alertError = $('.alert-error', $('.login-form'));
	            	alertError.find("span").text("请正确输入您的认证信息。");
	                alertError.show();
	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element).closest('.control-group').addClass('error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.control-group').removeClass('error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) 
	            {
	            	doLogin(form);
	            }
	        });
           	
	        $('.login-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.login-form').validate().form()) {
	                	doLogin($('.login-form'));
	                }
	                return false;
	            }
	        });
	        
        }

    };

}();