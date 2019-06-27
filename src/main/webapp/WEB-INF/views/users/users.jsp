<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html class="no-js" lang="en">
<head>
	<%@include file="../common/_header.jsp" %>
</head>
<body>
	
    <%@include file="../common/_sidebar.jsp" %>
    <!-- Start Welcome area -->
    <div class="all-content-wrapper">
    	<%@include file="../common/_menu.jsp" %>
    	<br/><br/><br/><br/>
    	<div class="main-wrapper">
	    	<div class = "header-list">
				<button class = "btn btn-primary pull-right" id = "add-user" title = "Ajouter un nouveau utilisateur"><i class = "glyphicon glyphicon-plus"></i> Ajouter</button>			</div>
    		<h1>Users List</h1>  
			<table class="table table-striped">
			  <thead>
			    <tr>
			      <th scope="col">Id</th>
			      <th scope="col">Nom</th>
			      <th scope="col">Prenom</th>
			      <th scope="col">Login</th>
			      <th scope="col">Mail</th>
			      <th scope="col">Type</th>
			    </tr>
			  </thead>
			  <tbody>  
			   <c:forEach var="user" items="${users}">   
				   <tr id="user-${user.getId()}">  
				   <td scope="row">${user.getId()}</td>  
				   <td>${user.getLastname()}</td>
				   <td>${user.getFirstname()}</td>
				   <td>${user.getUsername()}</td>  
				   <td>${user.getEmail()}</td>  
				   <td>${user.getTypeComputed()}</td>  
				   <td>
				   		<span class = "btn-group pull-right">
				   			<a class = "btn btn-sm btn-info detail-user" href="user/details?id=${user.getId()}" title="afficher les details concernant l'utilisateur"><i class = "glyphicon glyphicon-eye-open"></i></a>
				        	<button class = "btn btn-sm btn-primary edit-user" id-user = "${user.getId()}" title = "Edit user">
				        		<i class = "glyphicon glyphicon-pencil"></i>
				        	</button> 
				        	<button class = "btn btn-sm btn-danger delete-user" id-user-delete = "${user.getId()}" title = "Delete user">
				        		<i class = "glyphicon glyphicon-trash"></i>
				        	</button>
				        </span>
				    </td>  
			   </tr>  
			   </c:forEach>
			  </tbody>  
			</table>  
    	</div>
    	<%@include file="userdetails.jsp" %>
    	<%@include file="user_forms.jsp" %>
    	<%@include file="../common/_footer.jsp" %>
    </div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/user.js" />"></script>
</body>
</html>
