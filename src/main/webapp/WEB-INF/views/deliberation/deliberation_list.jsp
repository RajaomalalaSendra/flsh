<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:forEach items = "${ dataEvaluations }" var = "delib">
	<tr>
		<td>${ delib.getStudyunit_libelle() }</td>
		<td>${ delib.getStudyunit_type() }</td>
		<td>-</td><!-- notation -->
		<td></td><!-- coefficient -->
		<td></td><!-- Crédit -->
		<c:forEach items = "${ periodes }" var = "period">
			<td> --- </td>
			<c:if test="${ period.isA_ratrappage() }">
				<td>--</td>
			</c:if>
		</c:forEach>
		<td><input type = "text" class = "input-ue-credit"></td>
		<td><button class = "btn btn-sm btn-danger"><i class= "glyphicon glyphicon-remove"></i></button></td>
	</tr>	
	<!-- TODO: add ec evaluations -->
</c:forEach>
<c:forEach items = "${ periodes }" var = "period">
		<th class = "head-period">${ period.getPeriod_libellecourt() }</th>
		<c:if test="${ period.isA_ratrappage() }">
			<th class = "head-period">Rattrapage ${ period.getPeriod_libellecourt() }</th>
		</c:if>
</c:forEach>
<th class = "head-period">Créd. obtenu</th>
<th class = "head-period">Validé</th>