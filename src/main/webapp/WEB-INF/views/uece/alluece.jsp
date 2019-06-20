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
				<button class = "btn btn-primary pull-right" id = "add-ue" title = "Ajouter un nouveau utilisateur"><i class = "glyphicon glyphicon-plus"></i> Ajouter</button>
				<h3>Ajouter un nouveau unite d'enseignement</h3>
			</div>
    		<h1>Unite D'enseignement</h1>  
			<table class="table table-striped">
			  <thead>
			    <tr>
			      <th scope="col">Id</th>
			      <th scope="col">Parcours</th>
			      <th scope="col">Libelle</th>
			      <th scope="col">Type</th>
			    </tr>
			  </thead>
			  <tbody>  
			   <c:forEach var="unit" items="${units}">   
				   <tr id="study-${unit.getStudyUnitsId()}">  
				   <td scope="row">${unit.getStudyUnitsId()}</td>  
				   <td>${unit.getParcoursId()}</td>
				   <td>${unit.getStudyunitsLibelle()}</td>
				   <td>${unit.getStudyunitsType()}</td>    
				   <td>
				   		<span class = "btn-group pull-right">
				   			<a class = "btn btn-sm btn-info detail-user" title="afficher les details concernant l'utilisateur"><i class = "glyphicon glyphicon-eye-open"></i></a>
				        	<button class = "btn btn-sm btn-primary edit-user" title = "Edit user">
				        		<i class = "glyphicon glyphicon-pencil"></i>
				        	</button> 
				        	<button class = "btn btn-sm btn-danger delete-user" title = "Delete user">
				        		<i class = "glyphicon glyphicon-trash"></i>
				        	</button>
				        </span>
				    </td>  
			   </tr>  
			   </c:forEach>
			  </tbody>  
			</table>  
    	</div>
    	<%@include file="../common/_footer.jsp" %>
    </div>

<%@include file="../common/_script.jsp" %>
</body>
</html>
