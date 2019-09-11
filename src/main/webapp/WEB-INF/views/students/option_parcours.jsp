<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items = "${ parcours }" var = "prc" varStatus = "status">
	<option value = "${ prc.getParcoursId() }">${ prc.getParcoursLibelle() }</option>
</c:forEach>