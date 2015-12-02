<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<%@include file="header.jsp"%>
</head>
<body>

	<form:form action="/championship/app/participants/add"
		class="form-horizontal" method="post" enctype="multipart/form-data"
		commandName="participant">



		<div class="form-group">
			<form:label path="firstName" class="col-md-4 control-label"
				for="textinput">First Name</form:label>
			<div class="col-md-4">
				<form:input path="firstName" id="first_name" name="firstName"
					type="text" class="first_name form-control input-md"></form:input>
				<form:errors path="firstName" style="color:red"></form:errors>
			</div>
		</div>
		
		<div class="form-group">
			<form:label path="lastName" class="col-md-4 control-label"
				for="textinput">Last Name</form:label>
			<div class="col-md-4">
				<form:input path="lastName" id="last_name" name="lastName"
					type="text" class="last_name form-control input-md"></form:input>
				<form:errors path="lastName" style="color:red"></form:errors>
			</div>
		</div>
		
		<div class="form-group">
			<form:label path="email" class="col-md-4 control-label" for="textinput">Email</form:label>
			<div class="col-md-4">
				<form:input path="email" id="e-mail" name="email" type="text" class="e-mail form-control input-md"></form:input>
				<form:errors path="email" style="color:red"></form:errors>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-md-4 control-label" for="filebutton">Choose Picture</label>
			<div class="col-md-4">
				<input type="file" onchange="readURL(this);" name="picture"
					accept="image/jpeg, image/png">
			</div>
		</div>
		
		<div class="form-group">
		<label class="col-md-4 control-label" for="filebutton"></label>
			 <img height="100" id="img"
				class="input-md" width="100" />
				</div>
		</div>
		
		
		<div class="form-group">
			<div class="row">
				<div class="col-sm-6 col-sm-offset-3">
					<input type="submit" name="register-submit" id="register-submit"
						tabindex="4" class="btn btn-primary center-block"
						value="Add Participant">
				</div>
			</div>
		</div>


	</form:form>
	
	<script type="text/javascript">
	function readURL(input) {
	    if (input.files && input.files[0]) {
	        var reader = new FileReader();

	        reader.onload = function (e) {
	            $('#img')
	                .attr('src', e.target.result)
	                .width(100)
	                .height(100)
	                .show();
	        };

	        reader.readAsDataURL(input.files[0]);
	    }
	}</script>
</body>
</html>