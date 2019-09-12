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
    			<button class = "btn btn-primary pull-right" id = "add-student" title = "Ajouter un �tudiant"><i class = "glyphicon glyphicon-plus"></i> Ajouter</button>
    			<h3>Etudiants</h3>
    			<div class = "form-group" id = "search-container">
    				<input type = "text" id = "search-student" placeholder = "Rechercher �tudiant..." class = "form-control" />
    				<label for = "search-student"><i class = "glyphicon glyphicon-search"></i></label>
    			</div>
    		</div>
    		<div class = "table-wrapper">
    			<table class = "table table-bordered" id = "table-students">
					<thead>
						<tr>
							<th>Civ.</th>
							<th>Nom</th>
							<th>Pr�nom</th>
							<th>Date de naissance</th>
							<th>Nationalit�</th>
							<th>Adresse</th>
							<th>Email</th>
							<th>Dernier Etablissement</th>
							<th style="width: 140px;">Actions</th>
						</tr>
					</thead>
					<tbody>
						<%@include file="list_students.jsp" %>
					</tbody>
				</table>
    		</div>
    		<div class = "pagination" id = "pagination-students">
			  <ul class="pagination">
			    <li class="page-item">
			      <a class="page-link" href="#" id = "previous-page" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
			    
			    <c:forEach var="i" begin="1" end="${ Math.ceil(number/100) }" step="1">
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
    	<div id = "modals-wrapper">
    		<%@include file="student_forms.jsp" %>
    	</div>
    	<%@include file="../common/_footer.jsp" %>
    </div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/student.js" />"></script>
</body>