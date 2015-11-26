$('.editUser').on('click',function(){
	
	var id = $(this).parent().parent().find('.id').text();
	
	window.location="/championship/app/users/" + id +"/openForEdit";
	
});	