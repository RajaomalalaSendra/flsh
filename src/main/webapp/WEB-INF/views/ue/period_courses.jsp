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
    				<label for = "choixPeriodUY" class =>Année universitaire :</label>
		        	<select class = "form-control" id = "choixPeriodUY" >
		        		<c:forEach items = "${ univYears }" var = "uy" varStatus = "status">
		        			<option value = "${ uy.getUniversity_year_id() }" ${ uy.isActual() ? "selected" : "" }>${ uy.getUniversity_year_libelle() }</option>
		        		</c:forEach>
		        	</select>
		        	<label for = "choixPeriodLevel" class =>Niveau :</label>
		        	<select class = "form-control" id = "choixPeriodLevel" >
		        		<c:forEach items = "${ levels }" var = "level" varStatus = "status">
		        			<option value = "${ level.getLevelId() }">${ level.getLevelLibelle() }</option>
		        		</c:forEach>
		        	</select>
		        	<label for = "choixPeriodParcours" >Parcours :</label>
		        	<select class = "form-control" id = "choixPeriodParcours">
		        	</select>
				</div>
			</div>
    		<h1>Unite D'enseignement</h1>  
    		<div class = "alert alert-danger alert-course-period" id = "error-save-periodcourse">Erreur</div>
    		<div class = "alert alert-success alert-course-period" id = "success-save-periodcourse">Saved</div>
			<table class="table table-striped" id = "table-period-courses">
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
