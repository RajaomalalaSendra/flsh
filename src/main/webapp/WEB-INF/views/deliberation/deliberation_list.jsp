<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:forEach items = "${ delibs_period_one }" var = "delib">
	<tr>
		<td>${ delib.getUe_libelle() }/${ delib.getEc_libelle() }</td>
		<td>${ delib.getEvaluation() } / ${ delib.getEc_notation() }</td>
		<td>${ delib.getPeriod_rattrapage() }</td>
		<td> --- </td>
		<td> --- </td>
		<td>${ delib.getCredit() }</td>
	</tr>	
</c:forEach>
<c:forEach items = "${ delibs_period_two }" var = "delib">
	<tr>
		<td>${ delib.getUe_libelle() }/${ delib.getEc_libelle() }</td>
		<td> --- </td>
		<td> --- </td>
		<td>${ delib.getEvaluation() } / ${ delib.getEc_notation() }</td>
		<td>${ delib.getPeriod_rattrapage() }</td>
		<td>${ delib.getCredit() }</td>
	</tr>	
</c:forEach>
<tr>
	<td>Total </td>
	<td></td>
	<td></td>
	<td></td>
	<td></td>
	<td>${ total.getTotalCredit() }</td>
</tr>	