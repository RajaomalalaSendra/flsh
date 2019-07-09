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
		<td align = "center">
         	<button class = "btn btn-sm btn-info show-details-student" title = "Afficher les détails sur l'étudiants">
				<i class = "glyphicon glyphicon-eye-open"></i>
			</button>
			<c:if test = "${ idLevel > 0 }">
				<button class = "btn btn-sm btn-primary edit-subs" title = "Modifier les informations d'inscription">
					<i class = "glyphicon glyphicon-pencil"></i>
				</button>
				<button class = "btn btn-sm btn-danger delete-subs" title = "Retirer l'étudiant de cette liste">
					<i class = "glyphicon glyphicon-remove"></i>
				</button>
	      	</c:if>
	      	<c:if test = "${ idLevel == 0}">
	         	<button class = "btn btn-sm btn-success subscribe-student" title = "Inscrire l'étudiant">
					<i class = "glyphicon glyphicon-log-in"></i>
				</button>
	      	</c:if>
		</td>
	</tr>
</c:forEach>					