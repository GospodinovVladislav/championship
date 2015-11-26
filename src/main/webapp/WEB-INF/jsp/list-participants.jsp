<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>



<html>
<head>
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>Championship - Participants list</title>


<%@include file="header.jsp"%>

<h1 style="color: red" class="text-center">Participants List</h1>

<br>



<div class="container">
	<display:table requestURI="participants" id="participant" name="participants" class="table">
		<display:column property="id" title="ID" class="hidden id"
			headerClass="hidden" media="html"></display:column>
		<display:column property="email" title="email" class="hidden email"
			headerClass="hidden" media="html"></display:column>
		<display:column>
			<img src="../images/${participant.photoFileName}" height="60"
				width="60" />
		</display:column>
		<display:column title="First name" sortable="true" property="firstName"
			class="firstName" />
		<display:column title="Last name" sortable="true" property="lastName" class="lastName" />
		<display:column title="Matches Won" sortable="true" property="score.matchesWon"
			class="matchesWon" />
		<display:column title="Matches Lost" sortable="true" property="score.matchesLost"
			class="matchesLost" />
		<display:column title="Points Made" sortable="true" property="score.pointsMade"
			class="pointsMade" />
		<display:column title="Points Taken" sortable="true" property="score.pointsTaken"
			class="pointsTaken" />
		<display:column title="Score" sortable="true" property="score.score" class="score" />
		<shiro:hasRole name="admin">
			
			<display:column>
				<a href='#' class="deleteParticipant">Remove</a>
			</display:column>
		</shiro:hasRole>
	</display:table>
</div>

<shiro:hasRole name="noSuchRole">

<button type="button"
	class="addParticipant center btn btn-lg btn-success center-block">Add
	Participant</button>
	</shiro:hasRole>


<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<script type="text/javascript">
	<jsp:include page="/js/deleteParticipant.js"/>
	<jsp:include page="/js/addParticipant.js"/>
	<jsp:include page="/js/editParticipant.js"/>
</script>

</head>
</html>