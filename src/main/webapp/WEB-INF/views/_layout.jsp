<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html class="no-js" lang="en">
<head>
	<%@include file="common/_header.jsp" %>
	<link rel="stylesheet" href="<c:url value="/resources/css/dashboard.css" />">
</head>
<body>
	
    <%@include file="common/_sidebar.jsp" %>
    <!-- Start Welcome area -->
    <div class="all-content-wrapper">
    	<%@include file="common/_menu.jsp" %>
    	<div id = "go-down-dashboard" class = "form-inline">
	    	<%@include file="dashboard.jsp" %>
	    </div>
    	<%@include file="common/_footer.jsp" %>
    </div>
    
<%@include file="common/_script.jsp" %>
<script src="<c:url value="/resources/js/dashboard.js" />"></script>
</body>
</html>