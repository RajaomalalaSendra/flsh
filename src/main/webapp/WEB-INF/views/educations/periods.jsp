<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html class="no-js" lang="en">
<head>
	<%@include file="../common/_header.jsp" %>
	<link rel="stylesheet" href="<c:url value="/resources/css/cycle.css" />">
</head>
<body>
	
    <%@include file="../common/_sidebar.jsp" %>
    <!-- Start Welcome area -->
    <div class="all-content-wrapper">
    	<%@include file="../common/_menu.jsp" %>
    	<div class = "main-wrapper">
	    	<div id = "list-univ-year">
	    		<div class = "header-list">
	    			<button class = "btn btn-primary pull-right" id = "add-univ-year" title = "Ajouter une année universitaire"><i class = "glyphicon glyphicon-plus"></i> Ajouter</button>
	    			<h1>Année universitaire</h1>
	    		</div>
	    		<div class = "table-container">
	    			<table class = "table table-bordered">
	    				<thead>
	    					<tr>
	    						<th>Libellé</th>
	    						<th>Début</th>
	    						<th>Fin</th>
	    						<th style="width: 140px;">Actions</th>
	    					</tr>
	    				</thead>
	    				<tbody>
	    					<c:forEach items="${ listAnneeU }" var="au" varStatus="status">
	    						<tr id = "au-${ au.getUniversity_year_id() }">
	    							<td>${ au.getUniversity_year_libelle() }</td>
	    							<td>${ au.getStart() }</td>
	    							<td>${ au.getEnd() }</td>
	    							<td> 
	    								<button class = "btn btn-sm btn-success manage-periods" title = "Gérer les périodes">
	    									<i class = "glyphicon glyphicon-chevron-right"></i>
	    								</button>
	    								<button class = "btn btn-sm btn-primary edit-au" title = "Modifier l'année scolaire">
	    									<i class = "glyphicon glyphicon-pencil"></i>
	    								</button>
	    								<button class = "btn btn-sm btn-danger delete-au" title = "Supprimer l'année scolaire">
	    									<i class = "glyphicon glyphicon-trash"></i>
	    								</button>
	    							</td>
	    						</tr>
	    					</c:forEach>
	    				</tbody>
	    			</table>
	    		</div>
    		</div>
    		<div id = "details-univ-year" style = "display:none;">
    			
    		</div>
    	</div>
    	<%@include file="../common/_footer.jsp" %>
    	<%@include file="period_forms.jsp" %>
    </div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/period.js" />"></script>
</body>
</html>