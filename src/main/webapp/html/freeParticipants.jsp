<form action="users/add" method="post" onsubmit="return validate()"
	) enctype="multipart/form-data">
	<div class="form-group">
		<div class="col-sm-10 text-center">
			First name: <input type="text" class="first_name" name="first_name"
				onchange="isNameOk()"> <br> <span class="name_span"></span><br>
			<br> Last name: <input type="text" class="last_name"
				name="last_name" onchange="isLastNameOk()"><br> <span
				class="lastName_span"></span><br>
			<br> E-mail: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text"
				class="e-mail" name="e-mail" onchange="isMailOk()"><br>
			<span class="mail_span"></span><br>
			<br> Password: <input type="text" class="password"
				name="password" onchange="isPasswordOk()"><br> <span
				class="password_span"></span><br>
			<br> Upload Picture: <input type="file"
				onchange="readURL(this);" name="picture"
				accept="image/jpeg, image/png"> <img id="img"
				style="display: none" src="#" /> <br>
			<br> <input type="submit"
				class="btn btn-success bottom-aligned-text" id="addSubmit"
				value="Add">
		</div>
	</div>
</form>


<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>


<script type="text/javascript">
	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function(e) {
				$('#img').attr('src', e.target.result).width(100).height(100)
						.show();
			};

			reader.readAsDataURL(input.files[0]);
		}
	}

	function isPasswordOk() {
		var regex = /^[a-zA-Z0-9]{4,10}$/;
		if (!regex.test($('.password').val())) {
			$(".password_span")
					.text(
							"Password can contain characters and numbers and lenght 4-10.");
			$(".password_span").css('color', 'red');
			$(".password_span").css('font-size', '11px');
			return false;
		} else {
			$(".password_span").text("");
			return true;
		}
	}

	function isNameOk() {
		var regex = /^[a-zA-Z]{3,30}$/;
		if (!regex.test($('.first_name').val())) {
			$(".name_span").text(
					"Name should contain only characters with lenght 3-30.");
			$(".name_span").css('color', 'red');
			$(".name_span").css('font-size', '11px');
			return false;
		} else {
			$(".name_span").text("");
			return true;
		}
	}

	function isLastNameOk() {
		var regex = /^[a-zA-Z]{3,30}$/;
		if (!regex.test($('.last_name').val())) {
			$(".lastName_span").text(
					"Name should contain only characters with lenght 3-30.");
			$(".lastName_span").css('color', 'red');
			$(".lastName_span").css('font-size', '11px');
			return false;
		} else {
			$(".lastName_span").text("");
			return true;
		}
	}

	function isMailOk() {
		var regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$/;
		if (!regex.test($('.e-mail').val())) {
			$(".mail_span").text("Invalid E-Mail.");
			$(".mail_span").css('color', 'red');
			$(".mail_span").css('font-size', '11px');
			return false;
		} else {
			$(".mail_span").text("");
			return true;
		}
	}

	function validate() {

		if (isNameOk() && isLastNameOk() && isMailOk() && isPasswordOk()) {
			return true;
		} else
			return false;

	}
</script>