<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>



<html>
<head>
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>Championship - Users list</title>
</head>

<body>
<%@include file="header.jsp"%>


<h1 style="color:blue" class="text-center">Users List</h1>

<br>
<div class="container">
	<display:table id="user" requestURI="users"  name="users" class="table">
		<display:column property="id" title="ID" class="hidden id"
			headerClass="hidden" media="html"></display:column>
		<display:column > 
			<img src="../images/${user.photoFileName}" height="60" width="60"/>
		</display:column>
		<display:column title="First name" sortable="true"  property="firstName" class="firstName"/>
		<display:column title="Last name" property="lastName" class="lastName"/>
		<display:column title="E-mail" property="email" class="email" />
		<shiro:hasRole name="admin">
		<display:column>
			<a href='#' class="addToParticipants">Add To Participants</a>
		</display:column>
		<display:column>
			<a href='#' class="editUser">Edit</a>
		</display:column>
		<display:column>
			<a href='#' class="deleteUser">Delete</a>
		</display:column>
		</shiro:hasRole>
	</display:table>
</div>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<script type="text/javascript">
	<jsp:include page="/js/deleteUser.js"/>
	<jsp:include page="/js/addUser.js"/>
	<jsp:include page="/js/editUser.js"/>
	<jsp:include page="/js/addToParticipants.js"/>
</script>
</body>
</html>