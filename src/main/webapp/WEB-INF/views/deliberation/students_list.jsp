<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:forEach items = "${ students }" var = "student" varStatus = "status">
	<option value = "${ student.getStudent_id() }" ${ student.getStudent_id() == idStudent ? "selected" : "" }>${ student.getCivilite() } ${ student.getStudent_name() } ${ student.getStudent_lastname() }</option>
</c:forEach>