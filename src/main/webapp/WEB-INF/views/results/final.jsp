<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
	<%@include file="../common/_header.jsp" %>
	<link rel="stylesheet" href="<c:url value="/resources/css/final.css" />">
</head>
<body>
		<div id = "university-name">
			<h5>UNIVERSITE D'ANTANANANARIVO</h5>
			<h5>DOMAINE ARTS,LETTRES</h5>
			<h5>SCIENCES HUMAINES</h5>
	  	</div>
	  	<div style = "margin-left: 550px; margin-top: -75px;">
			<h5 id = "university-year-libelle">${ UnivYear.getUniversity_year_libelle().toUpperCase() }</h5>
			<h5>MENTION ETUDE ANGLOPHONE</h5>
			<h5>CYCLE ${ Cycle.getCycleLibelle().toUpperCase() == "LICENSE"  ? "LICENCE" : Cycle.getCycleLibelle().toUpperCase()}</h5>
		</div>
		<div  id = "moving-right">
			<h1>Niveau: <span id = "level-name">${ Level.getLevelLibelle() }</span></h1>
			<h3>Listes des ${ Category }</h3>
			<table class = "table table-bordered" style = "width: 90%;">
				<thead>
					<tr><th>Nom et prénom</th></tr>
				</thead>
				<c:forEach items="${ Students }" var="student" varStatus="status">
					<tr id = "students-list-${ student.getStudent_id() }">
						<td>${ student.getCivilite() }
						${ student.getStudent_name() }
						${ student.getStudent_lastname() }</td>
					</tr>
				</c:forEach>
			</table>	
		</div>
</body>
</html>