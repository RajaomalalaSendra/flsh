<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<th>UE/EC</th>
<c:forEach items = "${ periods }" var = "period" varStatus = "status">
	<th class = "allTheChangesFromPeriod" value = "${ period.getPeriod_id() }"> ${ period.getPeriod_libelle() }</th>
	<th>Rattrapage</th>
</c:forEach>
<th>Credit</th>