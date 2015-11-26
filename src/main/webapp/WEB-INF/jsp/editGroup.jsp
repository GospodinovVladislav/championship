<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="header.jsp"%>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<center>
	<font size="5">This group must contain
		${group.numberParticipants} participants</font>
</center>

<br>
<br>

<div class="container">

	<display:table id="participants" name="${group.participants}"
		class="table">
		<display:column property="id" title="ID" class="hidden id"
			headerClass="hidden" media="html"></display:column>
		<display:column>
			<img src="../../../images/${participants.photoFileName}" height="50"
				width="50" />
		</display:column>

		<display:column title="First name" property="firstName"
			class="firstName" />
		<display:column title="Last name" property="lastName" class="lastName" />

		<display:column>
			<a href='#' class="removeParticipant">Remove From Group</a>
		</display:column>
	</display:table>

	<c:set var="lenght" value="${fn:length (group.participants)}" />
	<c:set var="numbers" value="${group.numberParticipants}" />


	<center>
		<font size="5">Current number of participants in the group 
			${fn:length (group.participants)}</font>
			
		<br>
		<br>
	<c:if test="${lenght < numbers}">

		<form method="get"
			action="/championship/app/groups/editGroupParticipants/${group.id}"+ >
			<button type="submit" class=" btn btn-primary">Add Participants</button>
		</form>

	</c:if>
	</center>

</div>

<script type="text/javascript">
	$(document).ready(function() {
		$('.removeParticipant').on('click', function() {

			var id = $(this).parent().parent().find('.id').text();

			$.ajax({
				type : "GET",
				url : "/championship/app/groups/removeParticipants/" + id,
				error : function(e) {
					location.reload();
				}
			});

		});
	});
</script>
</html>