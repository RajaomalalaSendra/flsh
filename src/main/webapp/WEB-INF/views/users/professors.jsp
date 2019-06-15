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
				<button class = "btn btn-primary pull-right" id = "add-professor" title = "Ajouter un nouveau professeur"><i class = "glyphicon glyphicon-plus"></i> Ajouter</button>
				<h3>Ajouter un nouveau professeur</h3>
			</div>
    		<h1>Professors List</h1>  
			<table class="table table-striped">
			  <thead>
			    <tr>
			      <th scope="col">Id</th>
			      <th scope="col">Name</th>
			      <th scope="col">Last Name</th>
			      <th scope="col">Mail</th>
			      <th scope="col">Adresse</th>
			      <th scope="col">Contact</th>
			    </tr>
			  </thead>
			  <tbody>  
			   <c:forEach var="professor" items="${professors}">   
				   <tr id="prof-${professor.getProfessorId()}">  
					   <td scope="row">${professor.getProfessorId()}</td>  
					   <td>${professor.getProfessorName()}</td>
					   <td>${professor.getProfessorLastName()}</td>  
					   <td>${professor.getProfessorEmail()}</td>
					   <td>${professor.getProfessorAdresse()}</td>
					   <td>${professor.getProfessorContact()}</td>    
					   <td>
					   <span class = "btn-group pull-right">
					   			<a class = "btn btn-sm btn-info detail-professor" href="professor/details?id=${professor.getProfessorId()}" title="afficher les details concernant le professeur"><i class = "glyphicon glyphicon-eye-open"></i></a>
					        	<button class = "btn btn-sm btn-primary edit-professor" id-prof = "${professor.getProfessorId()}" id-prof-for-user = "${professor.getUserId()}" title = "Edit Professor">
					        		<i class = "glyphicon glyphicon-pencil"></i>
					        	</button> 
					        	<button class = "btn btn-sm btn-danger delete-professor"  id-profd = "${professor.getProfessorId()}" id-profdelete = "${professor.getUserId()}" title = "Delete Professor">
					        		<i class = "glyphicon glyphicon-trash"></i>
					        	</button>
					        </span>
					    </td>  
			   	  </tr>  
			   </c:forEach>
			  </tbody>  
			</table>  
    	</div>
    	<%@include file="professordetails.jsp" %>
    	<%@include file="professor_forms.jsp" %>
    	<%@include file="../common/_footer.jsp" %>
    </div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/professor.js" />"></script>
</body>
</html>
