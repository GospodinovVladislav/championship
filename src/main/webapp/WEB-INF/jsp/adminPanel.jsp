<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


	Admin Options!! Like generate matches etc

	<form method="get" action="/championship/app/admin/generateGroupMatches">
		<button type="submit" id="generateMatchesButton"
			class="generateMatches center btn btn-lg btn-success center-block">Generate
			group matches</button>
	</form>
	
		<form method="get" action="/championship/app/admin/generateQuarterFinals">
		<button type="submit" id="generateQuarterFinalsButton"
			class="generateQuarterFinals center btn btn-lg btn-success center-block">Generate
			Quarter Finals</button>
	</form>
	
	<form method="get" action="/championship/app/admin/generateQuarterFinalsMatches">
		<button type="submit" id="generateQuarterFinalsMatchesButton"
			class="generateQuarterFinalsMatches center btn btn-lg btn-success center-block">Generate
			Quarter Finals Matches</button>
	</form>
	
	<form method="get" action="/championship/app/admin/generateSemiFinals">
		<button type="submit" id="generateSemiFinalsButton"
			class="generateSemiFinals center btn btn-lg btn-success center-block">Generate
			Semi Finals</button>
	</form>
	
	<form method="get" action="/championship/app/admin/generateSemiFinalsMatches">
		<button type="submit" id="generateSemiFinalsMatchesButton"
			class="generateSemiFinalsMatches center btn btn-lg btn-success center-block">Generate
			Semi Finals Matches</button>
	</form>
	
	<form method="get" action="/championship/app/admin/generateFinals">
		<button type="submit" id="generateFinals"
			class="generateFinals center btn btn-lg btn-success center-block">Generate
			Finals</button>
	</form>
	
	<form method="get" action="/championship/app/admin/generateFinalsMatches">
		<button type="submit" id="generateFinalsMatches"
			class="generateFinalsMatches center btn btn-lg btn-success center-block">Generate
			Finals Matches</button>
	</form>
	
	
</body>
</html>