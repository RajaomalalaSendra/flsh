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
    	<div class="main-wrapper">  
    	<br/><br/><br/>
    		<div class = "btn-group pull-right form-inline">
    			<select class = "btn btn-default" id = "professorsAndUes">
	    			<c:forEach var="professor" items="${professors}">	
	    				<option value = "${professor.getProfessor_id()}"> ${professor.getCivilite()} ${professor.getProfessor_name()} ${professor.getProfessor_last_name()}</option>
	    			</c:forEach>
    			</select>
    		</div>
    		<h1>Courses of professor</h1>  
			<table class="table table-striped">
			  <thead>
			    <tr>
			      <th scope="col">Niveau</th>
			      <th scope="col">Parcours</th>
			      <th scope="col">Unite d'enseignement</th>
				  <th scope="col">Element Constitutif</th>
				  <th scope="col">Responsabilite</th>
			    </tr>
			  </thead>
			  <tbody>  
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
