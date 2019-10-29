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
		<iframe id="iframe-print-result"></iframe>
    	<div id = "main-wrapper">
    		<div class = "header-list">
    			<div class = "btn-group pull-right form-inline">
		        	<label for = "choixUY">Ann�e universitaire :</label>
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
		        	<button class = "btn btn-primary pull-right" id = "show-print-dialog">
		        		<i class="glyphicon glyphicon-print"></i> R�sultats
		        	</button>
				</div>
    			<h3>Etudiants</h3>
    			<div class = "form-group" id = "search-container-subscription">
    				<input type = "text" id = "search-student-subscribe" placeholder = "Rechercher �tudiant..." class = "form-control" />
    				<label for = "search-student"><i class = "glyphicon glyphicon-search"></i></label>
    			</div>
    		</div>
    		<div class = "table-wrapper">
    			<table class = "table table-bordered" id = "table-subscribed">
					<thead>
						<tr>
							<th></th>
							<th>Civ.</th>
							<th>Nom</th>
							<th>Pr�nom</th>
							<th>Naissance</th>
							<th>Nationalit�</th>
							<th>Adresse</th>
							<th style="width: 170px;">Actions</th>
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