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
    	<h2  class="admin-name" style="${empty username ? 'display: none' : ''}">${username}</h2>
    	<div class="main-wrapper">
	    	<div class = "header-list">
				<button class = "btn btn-primary pull-right" id = "add-user" title = "Ajouter un nouveau utilisateur"><i class = "glyphicon glyphicon-plus"></i> Ajouter</button>
				<h3>Ajouter un nouveau utilisateur</h3>
			</div>
    		<h1>Users List</h1>  
			<table class="table table-striped">
			  <thead>
			    <tr>
			      <th scope="col">Id</th>
			      <th scope="col">Name</th>
			      <th scope="col">Mail</th>
			      <th scope="col">Type</th>
			      <th scope="col">Action</th>
			    </tr>
			  </thead>
			  <tbody>  
			   <c:forEach var="user" items="${users}">   
				   <tr>  
				   <td scope="row">${user.getId()}</td>  
				   <td>${user.getUsername()}</td>  
				   <td>${user.getEmail()}</td>  
				   <td>${user.getTypeComputed()}</td>  
				   <td><a class = "detail-user" href="user/details?id=${user.getId()}">Detail</a></td>
				   <td>
				   		<span class = "btn-group pull-right">
				        	<button class = "btn btn-sm btn-primary edit-user" title = "Edit cycle">
				        		<i class = "glyphicon glyphicon-pencil"></i>
				        	</button> 
				        	<button class = "btn btn-sm btn-danger delete-user" title = "Delete cycle">
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
