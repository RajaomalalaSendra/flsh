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
    			<c:if test="${ !isProf }">
    				<label for = "select-prof-saisie">Professeur : </label>
	    			<select name = "select-prof-saisie" id = "select-prof-saisie" class = "form-control">
	    				<c:forEach items="${ professors }" var="prof" varStatus="status">
							<option value = "${ prof.getProfessor_id() }" ${ prof.getProfessor_id() == profSelected ? "selected" : "" }>${ prof.getProfessor_name()} ${ prof.getProfessor_last_name()}</option>
						</c:forEach>
	    			</select>
    			</c:if>
    			<label for = "select-ec-saisie">Element constitutif : </label>
    			<select name = "select-ec" id = "select-ec" class = "form-control">
    				<c:forEach items = "${ ecs }" var = "ec" >
    					<option value = "${ ec.getCourse_id() }">${ ec.getCourse_libellelong() } ( ${ ec.getUeNiveau() } )</option>
    				</c:forEach>
    			</select>
    		</div>
			<h1>Note professeur (${ infos_uy.getUniversity_year_libelle() })</h1>
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
