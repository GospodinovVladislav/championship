<!-- Latest compiled and minified CSS -->
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</head>
<body style="padding-top: 70px">
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/championship">Championship</a>
			<ul class="nav navbar-nav">
				<!--  <li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Manage</a>
					<ul class="dropdown-menu">-->
					
						<li><a href="/championship/app/users">Users</a></li>
						<li><a href="/championship/app/participants">Participants</a></li>
						<li><a href="/championship/app/groups">Groups</a></li>
						<li><a href="/championship/app/matches">Matches</a></li>
					</li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false"> 
					
					
					
					<shiro:guest>
   							Hello, Guest!
   							<ul class="dropdown-menu">
							<li><a href="/championship/app/login">Register/Login</a></li>
						</ul>
					</shiro:guest>
					
					<shiro:user>
					Hello, <shiro:principal property="firstName"/>
						<ul class="dropdown-menu">
							<li><a href="/championship/app/editMyAccount">My Account</a></li>
							<shiro:hasRole name="admin">
								<li><a href="/championship/app/admin">Admin Panel</a></li>
							</shiro:hasRole>
							<li><a href="/championship/app/logout">Log out</a></li>
						</ul>
					</shiro:user>
					
				</li>
			</ul>
		</div>
	</nav>