<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js" lang="en">
	<head>
		<meta charset="utf-8">
	    <meta http-equiv="x-ua-compatible" content="ie=edge">
	    <title>Résultats partiel ${ period } des ${ level }</title>
		<link rel="stylesheet" href="<c:url value="/resources/css/partial_result.css" />">
		<style type="text/css">
			table th {
				font-weight: bolder;
				border: 1px solid;
				padding: 10px;
			}
			table td {
				border: 1px solid;
				padding: 5px;
			}
			h1 {
				text-align: center;
			}
			#university-name {
				margin-top: 20px;
			}
			#university-name, #result-current-student, #jury-member, #jury-president {
				margin-left: 20px;
			}
			#moving-right {
				margin-left: 20px;
			}
			#entete {
				width: 100% !important;
			}
			#entete th, #entete td {
				border: none;
			}
			#right {
				text-align: right;
			}
		</style>
	</head>
	<body>
		<table id = "entete">
			<tr>
				<td id= "left" width="65%">
					<h5>UNIVERSITE D'ANTANANANARIVO</h5>
					<h5>DOMAINE ARTS,LETTRES</h5>
					<h5>SCIENCES HUMAINES</h5>
				</td>
				<td id= "right"  width="35%">
					<h5 id = "university-year-libelle">${ UnivYear.getUniversity_year_libelle().toUpperCase() }</h5>
					<h5>MENTION ETUDE ANGLOPHONE</h5>
					<h5>CYCLE ${ Cycle.getCycleLibelle().toUpperCase() == "LICENSE"  ? "LICENCE" : Cycle.getCycleLibelle().toUpperCase()}</h5>
				</td>
			</tr>
		</table>
		<h2 style = "text-align:center">Résultats ${ period } des ${ level } - ${ parcours }</h2>
		<table>
			<thead>
				<tr>
					<th rowspan="2"></th>
					<c:forEach items="${ studyUnits }" var="unit">
						<th colspan="${ unit.getCourses().size() }">
							${ unit.getStudyunit_libellelong() }
						</th>
					</c:forEach>
				</tr>
				<tr>
					<c:forEach items="${ studyUnits }" var="unit">
						<c:forEach items="${ unit.getCourses() }" var="course">
							<th>
								${ course.getCourse_libellecourt() }
							</th>
						</c:forEach>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ Students }" var="student">
					<tr>
						<td>${ student.getStudent_name() } ${ student.getStudent_lastname() }</td>
						<c:forEach items="${ studyUnits }" var="unit">
							<c:forEach items="${ unit.getCourses() }" var="course">
								<td>
									${ student.getECOk( course.getCourse_id(), course.getCourse_type() ) }
								</td>
							</c:forEach>
						</c:forEach>
					</tr>
				</c:forEach> 
			</tbody>
		</table>
	</body>
</html>