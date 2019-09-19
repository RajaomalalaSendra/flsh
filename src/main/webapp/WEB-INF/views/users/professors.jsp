<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html class="no-js" lang="en">
<head>
	<%@include file="../common/_header.jsp" %>
	<link rel="stylesheet" href="<c:url value="/resources/css/professor.css" />">
</head>
<body>
	
    <%@include file="../common/_sidebar.jsp" %>
    <!-- Start Welcome area -->
    <div class="all-content-wrapper">
    	<%@include file="../common/_menu.jsp" %>
    	<br/><br/><br/><br/>
    	<div class="main-wrapper">
	    	<div class = "header-list">
	    		<% if( onlineUser != null && onlineUser.isEnabled() && Integer.parseInt( onlineUser.getType()) == 1) { %>
					<button class = "btn btn-primary pull-right" id = "add-professor" title = "Ajouter un nouveau professeur"><i class = "glyphicon glyphicon-plus"></i> Ajouter</button>
				<% } %>
    			<h1>Professors List</h1>  
    			<div class = "form-group" id = "search-container">
    				<input type = "text" id = "search-prof" placeholder = "Rechercher prof..." class = "form-control" />
    				<label for = "search-prof"><i class = "glyphicon glyphicon-search"></i></label>
    			</div>
			</div>
			<div class = "table-wrapper">
				<table class="table table-striped" id = "table-professors">
				  <thead>
				    <tr>
				      <th scope="col">Id</th>
				      <th scope="col">Civ.</th>
				      <th scope="col">Name</th>
				      <th scope="col">Last Name</th>
				      <th scope="col">Mail</th>
				      <th scope="col">Adresse</th>
				      <th scope="col">Contact</th>
				    </tr>
				  </thead>
				  <tbody>  
				   <c:forEach var="professor" items="${professors}">   
					   <tr id="prof-${professor.getProfessor_id()}">  
						   <td scope="row">${professor.getProfessor_id()}</td>
						   <td>${professor.getCivilite()}</td>  
						   <td>${professor.getProfessor_name()}</td>
						   <td>${professor.getProfessor_last_name()}</td>  
						   <td>${professor.getProfessor_email()}</td>
						   <td>${professor.getProfessor_adresse()}</td>
						   <td>${professor.getProfessor_contact()}</td>    
						   <td>
						   <span class = "btn-group pull-right">
						   			<a class = "btn btn-sm btn-info detail-professor" href="professor/details?id=${professor.getProfessor_id()}" title="afficher les details concernant le professeur"><i class = "glyphicon glyphicon-eye-open"></i></a>
						        	<% if( onlineUser != null && onlineUser.isEnabled() && Integer.parseInt( onlineUser.getType()) == 1) { %>
							        	<button class = "btn btn-sm btn-primary edit-professor" id-prof = "${professor.getProfessor_id()}" id-prof-for-user = "${professor.getUser_id()}" title = "Edit Professor">
							        		<i class = "glyphicon glyphicon-pencil"></i>
							        	</button> 
							        	<button class = "btn btn-sm btn-danger delete-professor"  id-profd = "${professor.getProfessor_id()}" id-profdelete = "${professor.getUser_id()}" title = "Delete Professor">
							        		<i class = "glyphicon glyphicon-trash"></i>
							        	</button>
						        	<% } %>
						        </span>
						    </td>  
				   	  </tr>  
				   </c:forEach>
				  </tbody>  
				</table>  
			</div>
			<div class = "pagination" id = "pagination-professors">
			  <ul class="pagination pull-right">
			    <li class="page-item">
			      <a class="page-link" href="#" id = "previous-page" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
			    
			    <c:forEach var="i" begin="1" end="${ Math.ceil(number/50) }" step="1">
				    <li class="page-item ${ i == 1 ? 'active' : ''}"><a class="page-link" page-target = "${ i }" href="#"><c:out value="${ i }" /></a></li>
				</c:forEach>
			    <li class="page-item">
			      <a class="page-link" href="#" id = "next-page" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>
			  </ul>
    		</div>
    	</div>
    	<%@include file="professordetails.jsp" %>
    	<%@include file="professor_forms.jsp" %>
    	<%@include file="../common/_footer.jsp" %>
    </div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/professor.js" />"></script>
</body>
</html>
