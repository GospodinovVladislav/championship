var numberOfParticipants = parseInt($('#numberOfParticipants').text());
var groupName = $('#groupName').text();

var localNumber = numberOfParticipants;
var choosenNumberOfParticipants = 0;
checkButton();


$(':checkbox').change(function() {
	var value = this.value;

	if (this.checked) {
		if (localNumber > 0) {
			$(this).closest("tr").find("td").toggleClass("checkedHighlight", this.checked);
			send(value)
			localNumber--;
			choosenNumberOfParticipants++;
			$('#numberOfParticipants').text(localNumber);
			checkButton();
			
			if (localNumber == 1){
				$('#persons').text("person");
			}
			
			if (localNumber == 0) {
				$("input:checkbox:not(:checked)").attr('disabled', true);
			}
		}
	} else {
		$("input:checkbox:not(:checked)").attr('disabled', false);
		$(this).closest("tr").find("td").toggleClass("checkedHighlight", this.checked);
		remove(value);
		if (localNumber < numberOfParticipants) {
			choosenNumberOfParticipants--;
			localNumber++;
			if (localNumber > 1){
				$('#persons').text("person");
			}
			$('#numberOfParticipants').text(localNumber);
			checkButton();
		}
	}

});


function checkButton(){
	if (choosenNumberOfParticipants != numberOfParticipants) {
		document.getElementById("createButton").style.visibility="hidden";
	} else {
		document.getElementById("createButton").style.visibility="visible";
}
	
}

$('.create').on('click', function() {
	window.location = "/championship/app/groups";
});

function remove(id) {
	$.ajax({
		type : "GET",
		url : "/championship/app/groups/removeParticipants/" + id,
	});
};

function send(id) {
	$.ajax({
		type : "GET",
		url : "/championship/app/groups/" + groupName + "/addParticipants/" + id,
	});
};