$( document ).ready(function() {
$('.editGroup').on('click',function() {
		var id = $(this).parent().parent().find('.id').text();
		var groupName = $(this).parent().parent().find('.groupName').text();
		$('<div></div>')
		.html('<div><h6>Edit '+groupName+' ?</h6></div>')
		.dialog(
		{
			modal : true,
			title : 'Edit',
			zIndex : 10000,
			autoOpen : true,
			width : 'auto',
			resizable : false,
			buttons : {
				Yes : function() {
					self.parent.location = '/championship/app/groups/' + id  +'/edit';
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