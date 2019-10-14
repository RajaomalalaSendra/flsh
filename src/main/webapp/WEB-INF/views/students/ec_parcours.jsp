<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items = "${ ecs }" var = "ec" varStatus = "status">
	<option value = "${ ec.getCourse_id() }" >${ ec.getCourse_libellelong() }</option>
</c:forEach>