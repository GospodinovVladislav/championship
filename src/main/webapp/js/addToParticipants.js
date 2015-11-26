$( document ).ready(function() {
$('.addToParticipants').on('click',function() {
		var id = $(this).parent().parent().find('.id').text();
		$('<div></div>')
		.html('<div><h6>Are you sure you want to add this user to the participants?</h6></div>')
		.dialog(
		{
			modal : true,
			title : 'Add user to participants',
			zIndex : 10000,
			autoOpen : true,
			width : 'auto',
			resizable : false,
			buttons : {
				Yes : function() {
					self.parent.location = '/championship/app/participants/addFromUsers/' + id;
					$(this).dialog("close");

				},
				No : function() {
					$(this).dialog("close");
				}
			},
			close : function(event, ui) {
				$(this).remove();
			}
		});
	});
});

