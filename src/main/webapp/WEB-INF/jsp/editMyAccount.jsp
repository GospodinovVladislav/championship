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


	<form action="/championship/app/editMyAccount" class="form-horizontal"
		method="post" onsubmit="return validateEditMyAccount()"
		enctype="multipart/form-data">
		<input type="hidden" name="id" value='${id}'><br>

		<!-- Text input-->
		<div class="form-group">
			<label class="col-md-4 control-label" for="textinput">Email</label>
			<div class="col-md-4">
				<input id="e-mail" name="e-mail" onkeyup="checkEmail()" type="text"
					value="${email}" class="e-mail form-control input-md"> <span
					class="mail_span" />
			</div>
		</div>

		<!-- Text input-->
		<div class="form-group">
			<label class="col-md-4 control-label" for="textinput">First
				Name</label>
			<div class="col-md-4">
				<input id="first_name" onkeyup="isNameOk()" name="first_name"
					value="${first_name}" type="text"
					class="first_name form-control input-md"> <span
					class="name_span" />
			</div>

		</div>

		<!-- Text input-->
		<div class="form-group">
			<label class="col-md-4 control-label" for="textinput">Last
				Name</label>
			<div class="col-md-4">
				<input id="last_name" name="last_name" onkeyup="isLastNameOk()"
					type="text" value="${last_name}"
					class=" last_name form-control input-md"> <span
					class="lastName_span"></span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label" for="filebutton">Current
				Picture</label> <img src="../images/${picture}" height="100" id="img"
				class="input-md" width="100" />
		</div>
		<!-- File Button -->
		<div class="form-group">
			<label class="col-md-4 control-label" for="filebutton">Change
				Picture</label>
			<div class="col-md-4">
				<input type="file" onchange="readURL(this);" name="picture"
					accept="image/jpeg, image/png">
			</div>
		</div>

		<br><br>
		

		<div class="form-group">
		<label class="col-md-6 control-label" for="textinput">Change Password</label>
		</div>


		<div class="form-group">
			<label class="col-md-4 control-label" for="textinput">Old Password</label>
			<div class="col-md-4">
				<input id="old_pass" onchange="checkPass()" name="old_pass" type="text"
					class="old_pass form-control input-md"> 
					<span
					class="old_pass_span"></span>
			</div>

		</div>

		<!-- Text input-->
		<div class="form-group">
			<label class="col-md-4 control-label" for="textinput">New Password</label>
			<div class="col-md-4">
				<input id="new_pass" name="new_pass" onchange="isPasswordOk()"
					type="password"
					class="password form-control input-md"> <span
					class="password_span"></span>
			</div>
		</div>


		<div class="form-group">
			<div class="row">
				<div class="col-sm-6 col-sm-offset-3">
					<input type="submit" name="register-submit" id="register-submit"
						tabindex="4" class="password btn btn-primary center-block"
						value="Edit User">
				</div>
			</div>
		</div>

	</form>

	<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>

	<script type="text/javascript">

		<jsp:include page="/js/addUser.js"/>

		function validateEditMyAccount() {
			if (checkEmail() && isNameOk() && isLastNameOk() && checkPass() && isPasswordOk()) {
				return true;
			} else
				return false;
		}
		
		
		function checkPass(){
			
			var old_pass = $('.old_pass').val();
			
			if(old_pass == ""){
				return true;
			}
			
			$.ajax({
			    type : "GET",
			    url : "/championship/app/checkPass/" + old_pass,
			    success: function(response) {
			    	
			    		$(".old_pass_span").text(response);
			    		$(".old_pass_span").css('color','red');
			    		$(".old_pass_span").css('font-size','13px');
			    	
			      },
			    error: function(err) {
		            $(".old_pass_span").text("Password do not match!");
		          }
			}); 
			
			if($(".old_pass_span").text() != ""){
				return false;
			} else {
				return true;
			}
		}

		function checkEmail() {

			var regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$/;
			if (!regex.test($('.e-mail').val())) {
				$(".mail_span").text("Invalid E-Mail.");
				$(".mail_span").css('color', 'red');
				$(".mail_span").css('font-size', '13px');
				return false;
			} else {
				$(".mail_span").text("");
				return true;
			}
		}
	</script>

</body>
</html>