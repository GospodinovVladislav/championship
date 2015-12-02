<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ page import="bg.proxiad.demo.championship.model.Group"%>
<%@ page import="bg.proxiad.demo.championship.model.Participant"%>
<%@ page import="bg.proxiad.demo.championship.model.Match"%>
<%@ page import="java.util.List" %>


<html>
<head>
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Championship - Groups</title>

<%@include file="header.jsp"%>

</head>

<body>
	<h1 style="color: purple" class="text-center">Groups</h1>

	<shiro:hasRole name="admin">
		<a data-toggle="modal" class="btn btn-info" href="/championship/app/groups/createGroup" data-target="#myModal">Create Group</a>
	</shiro:hasRole>


	<br>


	<div class="container">
		<display:table id="group" name="groups" class="table">
			<display:column property="id" title="ID" class="hidden id"
				headerClass="hidden" media="html"></display:column>
			<display:column title="" property="groupName" class="groupName" />


			<%
				Group selectedGroup = (Group) pageContext.findAttribute("group");
				if(selectedGroup != null){
					
				
					String stage = selectedGroup.getStage();
					if (stage.equals("group")) {
						pageContext.setAttribute("participantsToPrint", selectedGroup.getParticipants());
						pageContext.setAttribute("showAllInfo", true);
					} else if (stage.equals("quarter")) {
						pageContext.setAttribute("participantsToPrint", selectedGroup.getQuarterFinalsParticipants());
						pageContext.setAttribute("showAllInfo", false);
						pageContext.setAttribute("showWins", true);
					} else if (stage.equals("semi")){
						pageContext.setAttribute("participantsToPrint", selectedGroup.getSemiFinalsParticipants());
						pageContext.setAttribute("showAllInfo", false);
						pageContext.setAttribute("showWins", true);
					} else if (stage.equals("final")){
						pageContext.setAttribute("participantsToPrint", selectedGroup.getFinalsParticipants());
						pageContext.setAttribute("showAllInfo", false);
						pageContext.setAttribute("showWins", true);
					}
				}
			%>






			<display:column>
				<display:table requestURI="groups" id="participants"
					name="${participantsToPrint}" class="table  table-hover">
					<display:column>
						<img src="../images/${participants.photoFileName}" height="50"
							width="50" />
					</display:column>
					<display:column title="First name" property="firstName"
						class="firstName" />
					<display:column title="Last name" property="lastName"
						class="lastName" />

					<c:if test="${showAllInfo}">
						<display:column title="Matches Won" sortable="true"
							property="score.matchesWon" class="matchesWon" />
						<display:column title="Matches Lost" sortable="true"
							property="score.matchesLost" class="matchesLost" />
						<display:column title="Points Made" sortable="true"
							property="score.pointsMade" class="pointsMade" />
						<display:column title="Points Taken" sortable="true"
							property="score.pointsTaken" class="pointsTaken" />
						<display:column title="Score" sortable="true"
							property="score.score" class="score" />
					</c:if>

					<c:if test="${showWins}">
					
					
					<%
						Group selectedGroupPart = (Group) pageContext.findAttribute("group");
						Participant participant = (Participant) pageContext.findAttribute("participants");
						
						int wins = 0;
						int loses = 0;
						
						List<Match> matches = selectedGroup.getMatches();
						
						for(Match m : matches){
							if(m.getHost().getId() == participant.getId() || m.getGuest().getId() == participant.getId()){
								if(m.getWinner() != null){
									if(m.getWinner().getId() == participant.getId()){
										wins++;
									} else {
										loses++;
									}
								}
							}
						}
							
						pageContext.setAttribute("wins", wins);
						pageContext.setAttribute("loses", loses);
					
					%>
					
					
					
					

						<display:column title="Matches Won" sortable="true" class="matchesWon"> 
							${wins}
						</display:column>
						<display:column title="Matches Lost" sortable="true" class="matchesLost">
							 ${loses}
							 </display:column>
					</c:if>

				</display:table>
				<br>
				<br>
				<br>
			</display:column>

			<shiro:hasRole name="admin">
			
				<c:if test="${showAllInfo}">
				<display:column>
					<a href='#' class="editGroup">Edit</a>
				</display:column>
				</c:if>
				<display:column>
					<a href='#' class="deleteGroup">Delete</a>
				</display:column>
			</shiro:hasRole>

		</display:table>
	</div>
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        </div> <!-- /.modal-content -->
    </div> <!-- /.modal-dialog -->
</div> <!-- /.modal -->

	<link rel="stylesheet"
		href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

	<script type="text/javascript">
		<jsp:include page="/js/deleteGroup.js"/>
		<jsp:include page="/js/editGroup.js"/>
		
			$('body').on('hidden.bs.modal', '.modal', function () {
			    $(this).removeData('bs.modal');
			});
			
			
			function submit(){
				if(existsCheck() && regexCheck()){
					$("#new_group_submit").click();
					$('#myModal').modal('hide')
					location.reload();
				}
			}
			
			
			
			function existsCheck(){
				var groupName = $('.group_name').val();
				
				$.ajax({
				    type : "GET",
				    url : "/championship/app/groups/"  + groupName + "/checkGroupName",
				    success: function(response) {
				    		$("#groupNameExists").text(response);
				    		$("#groupNameExists").css('color','red');
				    		$("#groupNameExists").css('font-size','13px');
				      },
				    error: function(err) {
			            $("#groupNameExists").text("Enter group name!");
			            return false;
			          }
				}); 
				
				if(($.trim($("#groupNameExists").html()) == "")){
						return true;
				} else {
					return false;
				}
				
			}
			
			function regexCheck(){
				var regex = /^[a-zA-Z ]{3,30}$/;
				if(!regex.test($('.group_name').val())){
					$("#groupNameWrongReg").text("Name should contain only characters with lenght 3-30.");
					$("#groupNameWrongReg").css('color','red');
					$("#groupNameWrongReg").css('font-size','11px');
					return false;
				} else {
					$("#groupNameWrongReg").text("");
					return true;
				}
			}
			
	</script>

</body>
</html>