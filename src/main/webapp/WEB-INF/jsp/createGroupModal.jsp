<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">&times;</button>
	<h4 class="modal-title">Create New Group</h4>
</div>
<!-- /modal-header -->

<div class="modal-body">

<form:form action="/championship/app/groups/createGroup" method="post" class="form-horizontal" commandName="group">


	<form:input type="hidden" name="id" path="id"></form:input>

	<div class="form-group">
			<form:label path="groupName" class="col-md-4 control-label"
				for="textinput">Group Name</form:label>
			<div class="col-md-4">
				<form:input onchange="existsCheck()" onkeyup="regexCheck()" path="groupName" id="group_name" name="groupName"
					type="text" class="group_name form-control input-md"></form:input>
				<span id="groupNameWrongReg"></span>
				<span id="groupNameExists"></span>
			</div>
		</div>
		
		
	<div class="form-group">
			<form:label path="numberParticipants" class="col-md-4 control-label"
				for="textinput">Number of participants</form:label>
			<div class="col-md-4">
			<form:select path="numberParticipants">
				<form:option value="2">2</form:option>
				<form:option value="3">3</form:option>
				<form:option value="4">4</form:option>
				<form:option value="5">5</form:option>
				<form:option value="6">6</form:option>
				<form:option value="7">7</form:option>
				<form:option value="8">8</form:option>
			</form:select>
				<form:errors path="numberParticipants" style="color:red"></form:errors>
			</div>
		</div>
		
		<div class="form-group">
			<div class="row">
				<div class="col-sm-6 col-sm-offset-3">
					<input type="submit" style="visibility: hidden;" name="new_group_submit" id="new_group_submit"
						tabindex="4" class="btn btn-primary center-block">
				</div>
			</div>
		</div>

</form:form>


<script type="text/javascript">
	
	</script>


</div>


<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	<button type="button" onclick="submit()" id="submit" class="btn btn-primary" >Save changes</button>
	
	
	
</div>


