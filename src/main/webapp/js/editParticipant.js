$('.editParticipant').on('click',function(){
	
	var id = $(this).parent().parent().find('.id').text();
	var firstName = $(this).parent().parent().find('.firstName').text();
	var lastName = $(this).parent().parent().find('.lastName').text();
	var email = $(this).parent().parent().find('.email').text();
	var gamesWon = $(this).parent().parent().find('.gamesWon').text();
	var gamesLost = $(this).parent().parent().find('.gamesLost').text();
	var points = $(this).parent().parent().find('.points').text();
	
	
	$('<div></div>')
    .html('<form action="participants/edit" method="post" onsubmit="return validateEdit()")> ' +
    		  '<input type="hidden" name="id" value='+id+'><br>' +
    		  '<div class="form-group">' +
    		  '<div class="col-sm-10 text-center">' +
    		  'First name: <br> <input type="text"  class="first_name" name="first_name"  onchange="isNameOk()" value='+firstName+'><br>' +
    		  '<span class="name_span"></span><br><br>' +
    		  'Last name: <br> <input type="text" class="last_name" name="last_name" onchange="isLastNameOk()"  value='+lastName+'><br>	' +
    		  '<span class="lastName_span"></span><br>' +
    		  'E-mail: <br> <input type="text" class="e-mail" name="e-mail" onchange="isMailOk()"  value='+email+'><br>	' +
    		  '<span class="mail_span"></span><br><br>' +
    		  '<input type="submit" class="btn btn-success bottom-aligned-text" id="addSubmit" value="Edit">' +
    		  '</div></div>' +
    	  '</form>')
    .dialog({
        modal: true,
        title: 'Edit Participant',
        zIndex: 10000,
        autoOpen: true,
        height: 550,
        width: 270,
        resizable: false,
        close: function (event, ui) {
            $(this).remove();
        }
    });
});	


function validateEdit(){
	if(isNameOk() && isLastNameOk() && gamesWonCheck() && pointsCheck() && pointsCheck()){
		return true;
	} else {
		return false;
	}
}

function gamesWonCheck(){
	var gamesWon = parseInt($('.games_won').val());
	if(gamesWon < 0){
		$(".games_won_span").text("Games should be positive number");
		$(".games_won_span").css('color','red');
		$(".games_won_span").css('font-size','11px');
		return false;
	} else {
		$(".games_won_span").text("");
		return true;
	}
}

function gamesLostCheck(){
	var gamesLost = parseInt($('.games_lost').val());
	if(gamesLost < 0){
		$(".games_lost_span").text("Games should be positive number");
		$(".games_lost_span").css('color','red');
		$(".games_lost_span").css('font-size','11px');
		return false;
	} else {
		$(".games_lost_span").text("");
		return true;
	}
}

function pointsCheck(){
	var points = parseInt($('.points_done').val());
	if(points < 0){
		$(".points_span").text("Points should be positive number");
		$(".points_span").css('color','red');
		$(".points_span").css('font-size','11px');
		return false;
	} else {
		$(".points_span").text("");
		return true;
	}
}