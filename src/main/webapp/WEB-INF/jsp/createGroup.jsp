<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<style type="text/css">

.checkedHighlight {
	background-color: #32CD32  ;
}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%@include file="header.jsp"%>
</head>
<body>

	<center>
		<font size="5">Check
		<font size="6" id="numberOfParticipants">${nubmerOfPersons}</font>
		<font id="persons">persons </font>
		to join <font id="groupName">${groupName}</font></font>
	</center>
	

	<br>
	<br>

	<div class="container">
		<display:table id="freeParticipant" name="freeParticipants"
			class="table">
			<display:column property="id" title="ID" class="hidden id"
				headerClass="hidden" media="html"></display:column>
			<display:column>
				<img src="/championship/images/${freeParticipant.photoFileName}"
					height="35" width="35" />
			</display:column>
			<display:column property="email" title="email" class="hidden email"
				headerClass="hidden" media="html"></display:column>
			<display:column title="First name" property="firstName"
				class="firstName" />
			<display:column title="Last name" property="lastName"
				class="lastName" />
			<display:column>
				<input type="checkbox" name="addToGroup" class="toGroup"
					value="${freeParticipant.id}">
			</display:column>
		</display:table>
	</div>


	<button type="button" id="createButton"
		class="create center btn btn-lg btn-success center-block">Continue</button>

	<script type="text/javascript">
		<jsp:include page="/js/newGroupControl.js"/>
	</script>

</body>
</html>