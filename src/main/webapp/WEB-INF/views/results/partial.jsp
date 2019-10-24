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
		</style>
	</head>
	<body>
		<h1>Résultats partiel</h1>
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