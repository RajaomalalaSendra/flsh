<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js" lang="en">
<head>
	<%@include file="../common/_header.jsp" %>
	<link rel="stylesheet" href="<c:url value="/resources/css/deliberation.css" />">
</head>
<body>
    <c:set var="req" value="${pageContext.request}" />
    <input type = "hidden" id = "base-url" value = "${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}/" />
   	<div id="deliberation-wrapper">
		<div class = "header-list" >
			<input type = "hidden" name = "IdUnivYear" id = "IdUnivYear" value = "${currentUnivYear.getUniversity_year_id()}"/>
				<div class = "btn-group pull-right form-inline push-notfar">
		        	<label for = "choixLevelDelib">Niveau :</label>
		        	<select class = "form-control" id = "choixLevelDelib" >
		        		<c:forEach items = "${ levels }" var = "level" varStatus = "status">
		        			<option value = "${ level.getLevelId() }" ${ level.getLevelId() == idLevel ? "selected" : "" }>${ level.getLevelLibelle() }</option>
		        		</c:forEach>
		        	</select>
		        	<label for = "choixParcoursDelib" >Parcours :</label>
		        	<select class = "form-control" id = "choixParcoursDelib">
		        	</select>
		        	<div class = "pull-right">
		        		<a class = "btn btn-default" id = "delib-eleve-precedent" title = "eleve precedent"><i class = "glyphicon glyphicon-step-backward"></i></a>
		        		<label for = "choixElevesDelib">Eleves :</label>
		        		<select class = "form-control"  id = "choixElevesDelib" >
			        	</select> 
		        		<a class = "btn btn-default" id = "delib-eleve-suivant" title = "eleve suivant"><i class = "glyphicon glyphicon-step-forward"></i></a>
		        	</div>
				</div>
				<h4>Deliberation  ${currentUnivYear.getUniversity_year_libelle() }</h4>
			</div>
			<div class = "row">
				<div class = "col-md-8">
				    
				    <div class = "alert alert-danger" id = "error-note-ue"></div>
					<div class = "alert alert-danger" id = "error-save-valid-credit"></div>
					
					<table class="table table-striped" id = "info-evaluation">
						<thead>
							<tr>
								<th>U.E / E.C</th>
								<th>Type</th>
								<th>Notation</th>
								<th>Coef</th>
								<th>Crédit (max)</th>
							</tr>
						</thead>
						<tbody class = "row-delib">
						
						</tbody>
					</table>
					
					<div class = "pull-right-buttom pull-right">
						<button type="button" class="btn deliberation-decision" id = "renvoi-deliberation">Renvoi</button>
						<button type="button" class="btn deliberation-decision" id = "redouble-deliberation">Redouble</button>
						<button type="button" class="btn deliberation-decision" id = "passe-deliberation">Passe</button>
					</div>
				</div>
				<div class = "col-md-4">
					<div id = "student-delib-marg">
						<div id = "Detail-Student-Deliberation"></div>
					</div>
				</div>
			</div>
   	</div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/deliberation.js" />"></script>
</body>
</html>
