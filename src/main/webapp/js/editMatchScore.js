$('.editMatchScore').on('click',function(){
	$("<div></div>")
    .html("/championship/groups")
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