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
    		</div>
    		<div class = "table-wrapper">
    			<table class = "table table-bordered">
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
						<c:forEach items="${ students }" var="student" varStatus="status">
							<tr id = "stud-${ student.getStudent_id() }">
								<td>${ student.getCivilite() }</td>
								<td>${ student.getStudent_name() }</td>
								<td>${ student.getStudent_lastname() }</td>
								<td>${ student.getStudent_birthdate() }</td>
								<td>${ student.getStudent_nationality() }</td>
								<td>${ student.getStudent_adress() }</td>
								<td>${ student.getStudent_email() }</td>
								<td>${ student.getStudent_lastetab() }</td>
								<td>
									<button class = "btn btn-sm btn-primary edit-student">
										<i class = "glyphicon glyphicon-pencil"></i>
									</button>
									<button class = "btn btn-sm btn-danger delete-student">
										<i class = "glyphicon glyphicon-trash"></i>
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
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