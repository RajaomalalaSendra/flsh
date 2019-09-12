<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="prof_and_ue-${prof.getProfessor_id()}">
	<c:forEach var="stdUnt" items="${stdUnts}">
		<tr>
			<td>${stdUnt.getLevel_libelle()}</td>
			<td>${stdUnt.getParcours_libelle()}</td>
			<td>${stdUnt.getStudy_unit_libelle()}</td>
			<td>${stdUnt.getEcs_libelle()}</td>
			<td>${stdUnt.getIsResponsable()}</td>
		</tr>
	</c:forEach>
</div>