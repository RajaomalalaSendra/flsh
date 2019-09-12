<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html class="no-js" lang="en">
	<head>
		<%@include file="../common/_header.jsp" %>
		<link rel="stylesheet" href="<c:url value="/resources/css/user.css" />">
	</head>
	<body>
	
    <%@include file="../common/_sidebar.jsp" %>
    <!-- Start Welcome area -->
    <div class="all-content-wrapper">
    	<%@include file="../common/_menu.jsp" %>
    	<br/><br/><br/><br/>
    	<div class="main-wrapper">
	    	<form id = "form-my-account" class = "row" autocomplete="off">
	    		<input type = "hidden" name = "id" id = "id-account" value = "${ user.getId() }" />
	    		<div class = "alert alert-danger" style = "display:none" id = "err-save-account"></div>
				<div class = "alert alert-success" style = "display:none" id = "success-save-account"></div>
	    		<div class = "pull-right">
	    			<button class = "btn btn-primary" id = "edit-myaccount">Modifier</button>
	    			<button class = "edit-account btn btn-danger" id = "cancel-editaccount">Annuler</button>
	    			<button class = "edit-account btn btn-success" id = "validate-editaccount">Enregistrer</button>
	    		</div>
	    		<h1>Mon compte</h1>  
	    		<div class = "form-group form-inline">
		      	    <label class="col-sm-3">Nom:</label>
		      		<input class="form-control" type="text" name = "lastname" id = "lastname-account" value = "${ user.getLastname() }" disabled required>
		      		<small class="form-text text-muted error"></small>
		      	</div>
		      	<div class = "form-group form-inline">
		      	    <label class="col-sm-3">Prenom:</label>
		      		<input class="form-control" type="text" name = "firstname" id = "firstname-account" value = "${ user.getFirstname() }" disabled>
		      		<small class="form-text text-muted error"></small>
		      	</div>
		      	 <div class = "form-group form-inline">
		      	    <label class="col-sm-3">Login:</label>
		      		<input class="form-control" type="text" name = "username" id = "name-account"  value = "${ user.getUsername() }" disabled required>
		      		<small class="form-text text-muted error"></small>
		      	</div>
		      	<div class = "form-group form-inline">
		      		<label class="col-sm-3">Email:</label>
			      	<input class="form-control" type="email" name = "email" id = "email-account"  value = "${ user.getEmail() }" disabled required>
			      	<small class="form-text text-muted error"></small>
			    </div>
		      	<div class = "form-group form-inline">
		      		<label class="col-sm-3">Type:</label>
		      		<select class="form-control" name="type" id="type-id" disabled>
		      	    	<option value="3" ${ user.getType() == 3 ? "selected" : "" }>Secretaire</option>
		      	    	<option value="2" ${ user.getType() == 2 ? "selected" : "" }>Professeur</option>
		      	    	<option value="1" ${ user.getType() == 1 ? "selected" : "" }>Super Utilisateur</option>
		      		</select>
		      	</div>
		      	<div class = "form-group form-inline">
		      		<label class="col-sm-3">New password:</label>
			      	<input class="form-control" autocomplete="new-password" type="password" name = "new_password" id = "newpass-account" value = "" disabled>
		      		<small class="form-text text-muted error"></small>
		      	</div>
		      	<div class = "form-group form-inline">
		      		<label class="col-sm-3">Confirm password:</label>
		      		<input class="form-control no-check" autocomplete="new-password" type="password" id = "confirmpass-account" value = "" disabled>
		      		<small class="form-text text-muted error"></small>
		      	</div>
		        <div class = "form-group form-inline">
		      		<label class="col-sm-3">Password:</label>
		      		<input class="form-control no-check" autocomplete="new-password" type="password" name = "last_password" id = "pass-account" value = "" disabled>
		      		<small class="form-text text-muted error"></small>
		      	</div>
	      	</form>
    		<%@include file="../common/_footer.jsp" %>
    	</div>	
    </div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/user.js" />"></script>
<script src="<c:url value="/resources/js/jquery.disableAutofill.min.js" />"></script>
</body>
</html>