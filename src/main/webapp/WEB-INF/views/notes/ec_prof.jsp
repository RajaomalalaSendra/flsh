<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items = "${ ecs }" var = "ec" >
	<option value = "${ ec.getCourse_id() }">${ ec.getCourse_libelle() } ( ${ ec.getUeNiveau() } )</option>
</c:forEach>