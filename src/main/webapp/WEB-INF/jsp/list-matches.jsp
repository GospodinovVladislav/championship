<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<%@include file="header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


	<div id="div1"  class="col-md-1" >
		<c:forEach items="${groupNames}" var="name">

			<button type="button" value="${name}" class="showMatches btn btn-lg btn-success">${name}</button>

			<br>
			<br>
		</c:forEach>
	</div>

	<div id="div2"  class="col-md-11">

	</div>





 

<script type="text/javascript">

$(".showMatches").on('click',function(){
	var groupName = $(this).val();
	
	
	$.get("matches/showGroup/" + groupName).success(function (html) {
		$("#div2").html(html);
	});
	
	
});

</script>
</body>
</html>