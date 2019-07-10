<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items="${ students }" var="student" varStatus="status">
	<tr id = "stud-${ student.getStudent_id() }">
		<td>${ student.getCivilite() }</td>
		<td>${ student.getStudent_name() }</td>
		<td>${ student.getStudent_lastname() }</td>
		<td>${ student.getStudent_birthdate() }</td>
		<td>${ student.getStudent_nationality() }</td>
		<td>${ student.getStudent_adress() }</td>
		<td>${ student.getStudent_email() }</td>
		<td>${ student.getStudent_lastetab() }</td>
		<td>
			<button class = "btn btn-sm btn-primary edit-student">
				<i class = "glyphicon glyphicon-pencil"></i>
			</button>
			<button class = "btn btn-sm btn-danger delete-student">
				<i class = "glyphicon glyphicon-trash"></i>
			</button>
		</td>
	</tr>
</c:forEach>