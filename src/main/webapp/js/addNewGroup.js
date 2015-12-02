

$('.createGroup').on('click',function(){
	$('<div></div>')
    .html('<form action="groups/createGroup" method="post" onsubmit="return validate()")> ' +
    		 '<div class="col-sm-10 text-center"><br>' +
    		'Enter Group Name: <input class="group_name" name="group_name" onchange="isNameOk()" onkeyup="isNameOk()" type="text"> ' +
    		'<span class="group_span"></span><br>' +
    			
    		'<br><br>'+
    	
    		'Choose number of players: <br>'+
    		'<select id="numberOfParticipants" name="number_players">'+
    		'	<option value="one">1</option>'+
    		'	<option value="two">2</option>'+
    		'	<option value="three">3</option>'+
    		'	<option value="four">4</option>'+
    		'	<option value="five">5</option>'+
    		'</select>'+
    		
    		'<br><br><br><br><input type="submit" class="btn btn-success bottom-aligned-text" id="addSubmit" value="Create">' +
    		
    		
    		'</div>' +
    	  '</form>')
    .dialog({
        modal: true,
        title: 'Group',
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
	
	var groupName = $('.group_name').val();
	
	$.ajax({
	    type : "GET",
	    url : "/championship/app/groups/"  + groupName + "/checkGroupName",
	    success: function(response) {
	    	if(($(".group_span").text() == "")){
	    		$(".group_span").text(response);
	    		$(".group_span").css('color','red');
	    		$(".group_span").css('font-size','13px');
	    	}
	      },
	    error: function(err) {
            $(".group_span").text("Enter group name!");
          }
	}); 
	
	
	var regex = /^[a-zA-Z ]{3,30}$/;
	if(!regex.test($('.group_name').val())){
		$(".group_span").text("Name should contain only characters with lenght 3-30.");
		$(".group_span").css('color','red');
		$(".group_span").css('font-size','11px');
	} else {
		if($(".group_span").text() != "")
			$(".group_span").text("");
	}
	
	
	
	
}

function checkGroupSpan(){
	if($(".group_span").text() == ""){
		return true;
		
	} else {
		return false;
	}
}



function validate(){
	
	if(checkGroupSpan()){
		return true;
	} else {
		return false;
	}
}