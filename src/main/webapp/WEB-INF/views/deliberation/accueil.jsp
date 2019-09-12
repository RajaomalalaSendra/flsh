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
			<h1>Choisissez le mode de saisie</h1>
			<div class = "row row-fluid" id = "choix-delib-wrapper">
				<div class = "form-group form-inline">
					<label for = "select-uy">Ann�e universitaire :</label>
					<select id = "select-uy" class = "form-control">
						<c:forEach items = "${ univYears }" var = "uy" varStatus = "status">
		        			<option value = "${ uy.getUniversity_year_id() }" ${ uy.isActual() ? "selected" : ""}>${ uy.getUniversity_year_libelle() }</option>
		        		</c:forEach>
					</select>
					<a class = "btn btn-info" id = "start-deliberation" target = "_blank">D�marrer la d�lib�ration</a>
				</div>
			</div>
    	</div>
    	<%@include file="../common/_footer.jsp" %>
    </div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/deliberation.js" />"></script>
</body>
</html>
