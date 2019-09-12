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
    		<div class = "form-group form-inline pull-right">
    			<input type = "hidden" id = "uy-id" value = "${ infos_uy.getUniversity_year_id()  }" />
    			<a class = "btn btn-default" title = "Retour au choix saisie" href = "<c:url value='/educations/notes' />"><i class = "glyphicon glyphicon-arrow-left"></i></a>
    			<label for = "select-level-saisie">Niveau : </label>
    			<select name = "select-level-saisie" id = "select-level-saisie" class = "form-control">
    				<c:forEach items="${ levels }" var="level" varStatus="status">
						<option value = "${ level.getLevelId() }" ${ level.getLevelId() == levelSelected ? "selected" : "" }>${ level.getLevelLibelle() }</option>
					</c:forEach>
    			</select>
    			<label for = "select-prc">Parcours : </label>
    			<select name = "select-prc" id = "select-prc" class = "form-control">
    			</select>
    			<label for = "select-ec">Element constitutif : </label>
    			<select name = "select-ec" id = "select-ec" class = "form-control">
    			</select>
    		</div>
			<h1>Note parcours (${ infos_uy.getUniversity_year_libelle() })</h1>
			<div class = "alert alert-success" id = "success-saisie">Success</div>
			<div class = "alert alert-danger" id = "error-saisie">Error</div>
			<div id = "saisie-wrapper">
				
			</div>
    	</div>
    	<%@include file="../common/_footer.jsp" %>
    </div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/notes.js" />"></script>
</body>
</html>
