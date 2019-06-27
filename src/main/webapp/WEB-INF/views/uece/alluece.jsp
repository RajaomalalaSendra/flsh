<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html class="no-js" lang="en">
<head>
	<%@include file="../common/_header.jsp" %>
	<link rel="stylesheet" href="<c:url value="/resources/css/ue.css" />">
</head>
<body>
	
    <%@include file="../common/_sidebar.jsp" %>
    <!-- Start Welcome area -->
    <div class="all-content-wrapper">
    	<%@include file="../common/_menu.jsp" %>
    	<br/><br/><br/><br/>
    	<div class="main-wrapper">
	    	<div class = "header-list">
				<button class = "btn btn-primary pull-right" id = "add-ue" title = "Ajouter un nouveau utilisateur"><i class = "glyphicon glyphicon-plus"></i> Ajouter</button>
			</div>
    		<h1>Unite D'enseignement</h1>  
			<table class="table table-striped">
			  <thead>
			  	<tr>
			      <th scope="col" >UE (Type)/EC</th>
			      <th scope="col">Crédit</th>
			      <th scope="col">Notation</th>
			      <th scope="col">Coéfficient</th>
			      <th scope="col">V. horaire</th>
			      <th scope="col">T. présenciel</th>
			      <th scope="col">T. personnel</th>
			      <th scope="col" width="15%">Actions</th>
			    </tr>
			  </thead>
			  <tbody>  
			   <c:forEach var="unit" items="${units}">   
				   <tr id="study-${unit.getStudyunits_id()}">  
					   <td scope="row">${unit.getStudyunits_libelle()} (${unit.getStudyunits_type()})</td>  
					   <td colspan = "6" align = "right" ></td>
					   <td>
					   		<span class = "btn-group pull-right">
					   			<button class = "btn btn-sm btn-info add-ec" title = "Ajouter un élément constitutif">
					        		<i class = "glyphicon glyphicon-plus-sign"></i>
					        	</button>
					        	<button class = "btn btn-sm btn-primary edit-ue" title = "Editer unité d'enseignement">
					        		<i class = "glyphicon glyphicon-pencil"></i>
					        	</button> 
					        	<button class = "btn btn-sm btn-danger delete-ue" title = "Supprimer unité d'enseignement">
					        		<i class = "glyphicon glyphicon-trash"></i>
					        	</button>
					        </span>
					    </td>
					   <c:forEach var="course" items="${unit.getCourses()}">
					   		<tr class = "ec" id = "ec-${course.getCourse_id()}">
					   			<td class = "libelle">${course.getCourse_libelle()}</td>
					   			<td>${course.getCourse_credit()}</td>
					   			<td>${course.getCourse_notation()}</td>
					   			<td>${course.getCourse_coefficient()}</td>
					   			<td>${course.getCourse_volumehoraire()}</td>
					   			<td>${course.getCourse_travailpresenciel()}</td>
					   			<td>${course.getCourse_travailpersonnel()}</td>
					   			<td>
							   		<span class = "btn-group pull-right">
							   			<i class = "glyphicon glyphicon-pencil edit-ec" title="Editer élément constitutif"></i>
							        	<i class = "glyphicon glyphicon-trash delete-ec" title="Supprimer élément constitutif"></i>
							        </span>
							    </td>
					   		</tr>				   
					   </c:forEach>				         
				   </tr>  
			   </c:forEach>
			  </tbody>  
			</table>  
    	</div>
    	<%@include file="../common/_footer.jsp" %>
    </div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/uece.js" />"></script>
</body>
</html>
