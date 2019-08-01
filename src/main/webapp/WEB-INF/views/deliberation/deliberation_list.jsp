<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:forEach items = "${ dataEvaluations }" var = "delib">
	<tr>
		<td>${ delib.getUe_libelle() }/${ delib.getEc_libelle() }</td>
		<td>${ delib.getEvaluation() } / ${ delib.getEc_notation() }</td>
		<td>${ delib.getPeriod_rattrapage() }</td>
		<td> --- </td>
		<td> --- </td>
		<td>${ delib.getCredit() }</td>
	</tr>	
</c:forEach>
<c:forEach items = "${ periodes }" var = "period">
		<th class = "head-period">${ period.getPeriod_libellecourt() }</th>
		<c:if test="${ period.isA_ratrappage() }">
			<th class = "head-period">Rattrapage ${ period.getPeriod_libellecourt() }</th>
		</c:if>
</c:forEach>