<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html class="no-js" lang="en">
<head>
	<%@include file="common/_header.jsp" %>
	<link rel="stylesheet" href="<c:url value="/resources/css/login.css" />">
</head>
<body>
	
    <!-- Start Welcome area -->
    <div class="all-content-wrapper">
    	<div class="wrapper fadeInDown">
			  <div id="formContent">
				    <!-- Tabs Titles -->
				
				    <!-- Icon -->
				    <div class="fadeIn first">
						<img src="/scolarLMD/resources/img/profile/user.png" id="icon" alt="User Icon" />
				    </div>
				
				    <!-- Login Form -->
				    <form method = "POST" action = "loginProcess">
				    	<p class="alert alert-danger" style="${empty message ? 'display: none' : ''}">${message}</p>
						<input type="text" id="login" class="fadeIn second" name="username" placeholder="login">
						<input type="password" id="password" class="fadeIn third" name="password" placeholder="password">
						<input type="submit" class="fadeIn fourth" value="Log In">
				    </form>
				
				    <!-- Remind Passowrd -->
				    <div id="formFooter">
						<a class="underlineHover" href="#">Forgot Password?</a>
				    </div>
			
			  </div>
		</div>
    </div>

<%@include file="common/_script.jsp" %>
</body>
</html>
