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
    			<div class = "btn-group pull-right form-inline">
		        	<label for = "choixLevel" class =>Niveau :</label>
		        	<select class = "form-control" id = "choixLevel" >
		        		<c:forEach items = "${ levels }" var = "level" varStatus = "status">
		        			<option value = "${ level.getLevelId() }">${ level.getLevelLibelle() }</option>
		        		</c:forEach>
		        	</select>
		        	<label for = "choixParcours" >Parcours :</label>
		        	<select class = "form-control" id = "choixParcours">
		        	</select>
					<button class = "btn btn-primary pull-right" id = "add-ue" title = "Ajouter un nouveau unité d'enseignement"><i class = "glyphicon glyphicon-plus"></i> Ajouter</button>
				</div>
    			<h1>Unite D'enseignement</h1>  
			</div>
			<table class="table table-striped">
			  <thead>
			  	<tr>
			      <th scope="col" >UE (Type)/EC</th>
			      <th scope="col">Professeur</th>
			      <th scope="col">Crédit</th>
			      <th scope="col">Notation</th>
			      <th scope="col">Coéfficient</th>
			      <th scope="col">V. horaire</th>
			      <th scope="col">T. présenciel</th>
			      <th scope="col">T. personnel</th>
			      <th scope="col"></th>
			    </tr>
			  </thead>
			  <tbody>  
			  
			  </tbody>  
			</table>  
    	</div>
        <%@include file="ec_forms.jsp" %>
    	<%@include file="ue_forms.jsp" %>
    	<%@include file="../common/_footer.jsp" %>
    </div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/ue.js" />"></script>
</body>
</html>
