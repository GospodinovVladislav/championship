$(document)
		.ready(
				function() {
					$('.deleteParticipant')
							.on(
									'click',
									function() {
										var id = $(this).parent().parent()
												.find('.id').text();
										$('<div></div>')
												.html(
														'<div><h6>Are you sure you want to remove this participant?</h6></div>')
												.dialog(
														{
															modal : true,
															title : 'Remove Participant',
															zIndex : 10000,
															autoOpen : true,
															width : 'auto',
															resizable : false,
															buttons : {
																Yes : function() {
																	self.parent.location = '/championship/app/participants/'
																			+ id
																			+ '/delete';
																	$(this)
																			.dialog(
																					"close");

																},
																No : function() {
																	$(this)
																			.dialog(
																					"close");
																}
															},
															close : function(
																	event, ui) {
																$(this)
																		.remove();
															}
														});
									});
				});
