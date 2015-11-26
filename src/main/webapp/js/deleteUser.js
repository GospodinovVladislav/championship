$( document ).ready(function() {
$('.deleteUser').on('click',function() {
		var id = $(this).parent().parent().find('.id').text();
		$('<div></div>')
		.html('<div><h6>Are you sure you want to remove this user?</h6></div>')
		.dialog(
		{
			modal : true,
			title : 'Remove User',
			zIndex : 10000,
			autoOpen : true,
			width : 'auto',
			resizable : false,
			buttons : {
				Yes : function() {
					self.parent.location = '/championship/app/users/' + id +  '/delete';
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

