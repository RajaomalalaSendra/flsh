<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js" lang="en">
<head>
	<%@include file="../common/_header.jsp" %>
	<link rel="stylesheet" href="<c:url value="/resources/css/notes.css" />">
</head>
<body>
	
    <%@include file="../common/_sidebar.jsp" %>
    <!-- Start Welcome area -->
    <div class="all-content-wrapper">
    	<%@include file="../common/_menu.jsp" %>
    	<br/><br/><br/><br/>
    	<div class="main-wrapper">
			<h1>Choisissez le mode de saisie</h1>
			<div class = "row row-fluid" id = "choix-saisie-wrapper">
				<div id = "saisie-prof" class = "col-md-5 box-starter">
					<div class = "form-group form-inline">
						<label for = "select-uy-prof" class = "col-md-6">Année universitaire :</label>
						<select id = "select-uy-prof" class = "form-control">
							<c:forEach items = "${ univYears }" var = "uy" varStatus = "status">
			        			<option value = "${ uy.getUniversity_year_id() }" ${ uy.isActual() ? "selected" : ""}>${ uy.getUniversity_year_libelle() }</option>
			        		</c:forEach>
						</select>
					</div>
					<div class = "form-group form-inline">
						<label for = "select-prof" class = "col-md-6">Professeur :</label>
						<select id = "select-prof" class = "form-control">
							<c:if test="${ !isProf }">
								<c:forEach items="${ professors }" var="prof" varStatus="status">
									<option value = "${ prof.getProfessor_id() }">${ prof.getProfessor_name()} ${ prof.getProfessor_last_name()}</option>
								</c:forEach>
							</c:if>
							<c:if test="${ isProf }">
								<option value = "${ prof.getProfessor_id() }">${ prof.getProfessor_name()} ${ prof.getProfessor_last_name()}</option>
							</c:if>
						</select>
					</div>
					<button class = "pull-right btn btn-info" id = "start-saisie-prof">Saisir les notes</button>
				</div>
				<c:if test="${ !isProf }">
					<div id = "saisie-niveau" class = "col-md-5 box-starter">
						<div class = "form-group form-inline">
							<label for = "select-uy-level" class = "col-md-6">Année universitaire :</label>
							<select id = "select-uy-level" class = "form-control">
								<c:forEach items = "${ univYears }" var = "uy" varStatus = "status">
				        			<option value = "${ uy.getUniversity_year_id() }" ${ uy.isActual() ? "selected" : ""}>${ uy.getUniversity_year_libelle() }</option>
				        		</c:forEach>
							</select>
						</div>
						<div class = "form-group form-inline">
							<label for = "select-level" class = "col-md-6">Niveau :</label>
							<select id = "select-level" class = "form-control">
								<c:forEach items = "${ levels }" var = "level" varStatus = "status">
				        			<option value = "${ level.getLevelId() }">${ level.getLevelLibelle() }</option>
				        		</c:forEach>
							</select>
						</div>
						<button class = "pull-right btn btn-info" id = "start-saisie-niveau">Saisir les notes</button>
					</div>
				</c:if>
			</div>
    	</div>
    	<%@include file="../common/_footer.jsp" %>
    </div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/notes.js" />"></script>
</body>
</html>
