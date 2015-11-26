

$('.addParticipant').on('click',function(){
	$('<div></div>')
    .html('<form action="participants/add" method="post" onsubmit="return validate()")> ' +
    		  '<div class="form-group">' +
    		  '<div class="col-sm-10 text-center">' +
    		  'First name: <br> <input type="text"  class="first_name" name="first_name" onchange="isNameOk()"><br>' +
    		  '<span class="name_span"></span><br>' +
    		  'Last name: <br> <input type="text" class="last_name" name="last_name" onchange="isLastNameOk()"><br>	' +
    		  '<span class="lastName_span"></span><br>' +
    		  'E-mail: <br> <input type="text" class="e-mail" name="e-mail" onchange="isMailOk()"><br>' +
    		  '<span class="mail_span"></span><br>' +
    		  '<input type="submit" class="btn btn-success bottom-aligned-text" id="addSubmit" value="Add">' +
    		  '</div></div>' +
    	  '</form>')
    .dialog({
        modal: true,
        title: 'Add Participant',
        zIndex: 10000,
        autoOpen: true,
        height: 400,
        width: 270,
        resizable: false,
        close: function (event, ui) {
            $(this).remove();
        }
    });
});	



function isNameOk(){
	var regex = /^[a-zA-Z ]{3,30}$/;
	if(!regex.test($('.first_name').val())){
		$(".name_span").text("Name should contain only characters with lenght 3-30.");
		$(".name_span").css('color','red');
		$(".name_span").css('font-size','11px');
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
		$(".lastName_span").css('font-size','11px');
		return false;
	} else {
		$(".lastName_span").text("");
		return true;
	}
}

function isMailOk(){
	var regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$/;
	if(!regex.test($('.e-mail').val())){
		$(".mail_span").text("Invalid E-Mail.");
		$(".mail_span").css('color','red');
		$(".mail_span").css('font-size','11px');
		return false;
	} else {
		$(".mail_span").text("");
		return true;
	}
}



function validate(){
	
	if(isNameOk() && isLastNameOk() && isMailOk()){
		return true;
	} else 
		return false;
	
}