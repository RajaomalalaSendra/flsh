<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html class="no-js" lang="en">
<head>
	<%@include file="../common/_header.jsp" %>
	 <link rel="stylesheet" href="<c:url value="/resources/css/student.css" />">
</head>
<body>
	
    <%@include file="../common/_sidebar.jsp" %>
    <!-- Start Welcome area -->
    <div class="all-content-wrapper">
    	<%@include file="../common/_menu.jsp" %>
    	<br/><br/><br/><br/>
    	<div id = "main-wrapper">
    		<div class = "header-list">
    			<div class = "btn-group pull-right form-inline">
		        	<label for = "choixUY" class =>Année universitaire :</label>
		        	<select class = "form-control" id = "choixUY" name = "subs_univyear" >
		        		<c:forEach items = "${ univYears }" var = "uy" varStatus = "status">
		        			<option value = "${ uy.getUniversity_year_id() }" ${ uy.isActual() ? "selected" : "" }>${ uy.getUniversity_year_libelle() }</option>
		        		</c:forEach>
		        	</select>
		        	<label for = "choixLevel" >Niveau :</label>
		        	<select class = "form-control" id = "choixLevel" name = "subs_levelchoice" >
		        		<option value = "0">Non inscrits</option>
		        		<c:forEach items = "${ levels }" var = "level" varStatus = "status">
		        			<option value = "${ level.getLevelId() }">${ level.getLevelLibelle() }</option>
		        		</c:forEach>
		        	</select>
				</div>
    			<h3>Etudiants</h3>
    			<div class = "form-group" id = "search-container-subscription">
    				<input type = "text" id = "search-student-subscribe" placeholder = "Rechercher étudiant..." class = "form-control" />
    				<label for = "search-student"><i class = "glyphicon glyphicon-search"></i></label>
    			</div>
    		</div>
    		<div class = "table-wrapper">
    			<table class = "table table-bordered" id = "table-subscribed">
					<thead>
						<tr>
							<th>Civ.</th>
							<th>Nom</th>
							<th>Prénom</th>
							<th>Date de naissance</th>
							<th>Nationalité</th>
							<th>Adresse</th>
							<th>Email</th>
							<th>Dernier Etablissement</th>
							<th style="width: 140px;">Actions</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
    		</div>
    		<div class = "pagination" id = "pagination-subscription">
			  <ul class="pagination">
			  </ul>
    		</div>
    	</div>
    	<div id = "modals-wrapper">
    		<%@include file="student_forms.jsp" %>
    	</div>
    	<%@include file="../common/_footer.jsp" %>
    </div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/student.js" />"></script>
</body>