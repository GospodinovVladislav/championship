


function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#img')
                .attr('src', e.target.result)
                .width(100)
                .height(100)
                .show();
        };

        reader.readAsDataURL(input.files[0]);
    }
}

function isPasswordOk(){
	var regex = /^[a-zA-Z0-9]{4,10}$/;
	
	if(!regex.test($('.password_reg').val())){
		$(".password_span").text("Password can contain characters and numbers with lenght 4-10.");
		$(".password_span").css('color','red');
		$(".password_span").css('font-size','13px');
		return false;
	} else {
		$(".password_span").text("");
		return true;
	}
}

function isNameOk(){
	var regex = /^[a-zA-Z]{3,30}$/;
	if(!regex.test($('.first_name').val())){
		$(".name_span").text("Name should contain only characters with lenght 3-30.");
		$(".name_span").css('color','red');
		$(".name_span").css('font-size','13px');
		return false;
	} else {
		$(".name_span").text("");
		return true;
	}
}

function isLastNameOk(){
	var regex = /^[a-zA-Z]{3,30}$/;
	if(!regex.test($('.last_name').val())){
		$(".lastName_span").text("Name should contain only characters with lenght 3-30.");
		$(".lastName_span").css('color','red');
		$(".lastName_span").css('font-size','13px');
		return false;
	} else {
		$(".lastName_span").text("");
		return true;
	}
}

function mailExists(){
	
	var mail = $('.e-mail').val();
	
	$.ajax({
	    type : "GET",
	    url : "/championship/app/checkMail/" + mail,
	    success: function(response) {
	    		$(".exist_mail").text(response);
	    		$(".exist_mail").css('color','red');
	    		$(".exist_mail").css('font-size','13px');
	      },
	    error: function(err) {
            $(".exist_mail").text("Enter e-mail");
          }
	}); 
	
	if(($.trim($(".exist_mail").html()) == "")){
		return true;
} else {
	return false;
}
	
}

function mailRegEx(){
	var regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$/;
	if(!regex.test($('.e-mail').val())){
		$(".mail_span").text("Invalid E-Mail Format.");
		$(".mail_span").css('color','red');
		$(".mail_span").css('font-size','13px');
		return false;
	} else {
			$(".mail_span").text("");
			return true;
	}
}

function validate(){
	
	if(mailExists() && mailRegEx() && isPasswordOk() && isNameOk() && isLastNameOk() ){
		return true;
	} else 
		return false;
	
}