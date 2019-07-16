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
				<button class = "btn btn-default pull-right" id = "add-professor-ue" title = "Ajouter un nouveau professeur d'un unite Enseignement"><i class = "glyphicon glyphicon-plus"></i> Ajouter</button>
			</div>
    		<h1>Listes des Professeurs d'Unite d'enseignement</h1>  
			<table class="table table-striped">
			  <thead>
			    <tr>
			      <th scope="col">Unite d'enseignement</th>
			      <th scope="col">Professeur Responsable</th>
			      <th scope="col"></th>
			    </tr>
			  </thead>
			  <tbody>  
			   <c:forEach var="professor_ue" items="${professors_ue}">   
				   <tr id="prof_ue-${professor_ue.getProfessor_id()}">  
					   <td scope="row">${professor_ue.getStudy_unit_libelle()}</td>
					   <td>${professor_ue.getCivilite()} ${professor_ue.getProfessor_name()} ${professor_ue.getProfessor_last_name()}</td>  
					   <td>
					   <span class = "btn-group pull-right">
					   			<button class = "btn btn-sm btn-default edit-professor-ue" id-prof-ue = "${professor_ue.getProfessor_id()}" id-prof-for-ue = "${professor.getUser_id()}" title = "Editer Professeur Unite d'Enseignement">
					        		<i class = "glyphicon glyphicon-pencil"></i>
					        	</button> 
					        	<button class = "btn btn-sm btn-danger delete-professor"  id-prof-delete-ue = "${professor_ue.getProfessor_id()}" id-profdelete-ue = "${professor.getUser_id()}" title = "Effacer Professeur d'un Unite d'Enseignement">
					        		<i class = "glyphicon glyphicon-trash"></i>
					        	</button>
					        </span>
					    </td>  
			   	  </tr>  
			   </c:forEach>
			  </tbody>  
			</table>  
    	</div>
    	<%@include file="professor_course_forms.jsp" %>
    	<%@include file="../common/_footer.jsp" %>
    </div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/professor.js" />"></script>
</body>
</html>
