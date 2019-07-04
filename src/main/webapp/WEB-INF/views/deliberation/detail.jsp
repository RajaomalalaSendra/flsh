<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js" lang="en">
<head>
	<%@include file="../common/_header.jsp" %>
	<link rel="stylesheet" href="<c:url value="/resources/css/deliberation.css" />">
</head>
<body>
	
    <%@include file="../common/_sidebar.jsp" %>
    <!-- Start Welcome area -->
    <div class="all-content-wrapper">
    	<%@include file="../common/_menu.jsp" %>
    	<br/><br/><br/><br/>
    	<div class="main-wrapper">
			<div class = "header-list" >
				<input type = "hidden" name = "IdUnivYear" id = "IdUnivYear" value = "${univ_year.getUniversity_year_id()}"/>
					Deliberation  ${univ_year.getUniversity_year_libelle() }
					<div class = "btn-group form-inline push-notfar">
			        	<label for = "choixLevelDelib">Niveau :</label>
			        	<select class = "form-control" id = "choixLevelDelib" >
			        		<c:forEach items = "${ levels }" var = "level" varStatus = "status">
			        			<option value = "${ level.getLevelId() }">${ level.getLevelLibelle() }</option>
			        		</c:forEach>
			        	</select>
			        	<label for = "choixParcoursDelib" >Parcours :</label>
			        	<select class = "form-control" id = "choixParcoursDelib">
			        	</select>
			        	<div class = "pull-right">
			        		<a class = "btn btn-default" title = "Retour au choix saisie" href = "<c:url value='/educations/notes' />"><i class = "glyphicon glyphicon-arrow-left"></i></a>
			        		<label for = "choixElevesDelib">Eleves :</label>
			        		<select class = "form-control"  id = "choixElevesDelib" >
				        	</select> 
			        		<a class = "btn btn-default" title = "Retour au choix saisie" href = "<c:url value='/educations/notes' />"><i class = "glyphicon glyphicon-arrow-right"></i></a>
			        	</div>
					</div>
				</div>
				<div class = "row">
					<div class = "col-md-8">
						<table class="table table-striped">
							<thead id = "changeFromPeriod"></thead>
							<tbody></tbody>
						</table>
					</div>
					<div class = "col-md-4">
						<div id = "student-delib-marg">
							<div id = "Detail-Student-Deliberation"></div>
						</div>
					</div>
				</div>
				<div class = "pull-right-buttom">
					<button type="button" class="btn btn-danger" id = "renvoi-deliberation">Renvoi</button>
					<button type="button" class="btn btn-warning" id = "redouble-deliberation">Redouble</button>
					<button type="button" class="btn btn-success" id = "passe-deliberation">Passe</button>
				</div>
    	</div>
    	<%@include file="../common/_footer.jsp" %>
    </div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/deliberation.js" />"></script>
</body>
</html>
