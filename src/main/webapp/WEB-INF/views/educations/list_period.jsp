<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items="${ periods }" var="period" varStatus="status">
	<tr id = "per-${ period.getPeriod_id() }">
		<td>${ period.getPeriod_libellecourt() }</td>
		<td>${ period.getPeriod_libellelong() }</td>
		<td>${ period.getPerDebut() }</td>
		<td>${ period.getPerFin() }</td>
		<td>${ period.isA_ratrappage() ? "Oui" : "Non" }</td>
		<td>${ period.getExam_libelle() }</td>
		<td>${ period.getExamDebut() }</td>
		<td>${ period.getExamFin() }</td>
		<td>${ period.getRattr_libelle() }</td>
		<td>${ period.getRattrDebut() }</td>
		<td>${ period.getRattrFin() }</td>
		<td>
			<button class = "btn btn-sm btn-primary edit-period">
				<i class = "glyphicon glyphicon-pencil"></i>
			</button>
			<button class = "btn btn-sm btn-danger delete-period">
				<i class = "glyphicon glyphicon-trash"></i>
			</button>
		</td>
	</tr>
</c:forEach>