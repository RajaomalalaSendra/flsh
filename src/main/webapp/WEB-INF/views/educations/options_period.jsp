<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items="${ periods }" var="period" varStatus="status">
	<option value = "${ period.getPeriod_id() }">
		${ period.getPeriod_libellecourt() } - ${ period.getPeriod_libellelong() }
	</option>
</c:forEach>