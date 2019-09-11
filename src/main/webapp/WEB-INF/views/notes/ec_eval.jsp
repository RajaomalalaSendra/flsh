<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<input type = "hidden" id = "notation-ec" value = "${ infosEC.getCourse_notation() }" />
<table class = "table table-bordered" id = "table-saisie">
	<thead>
		<tr>
			<th class = "nom_prenom">Nom et prénom</th>
			<c:forEach items = "${ exams }" var = "exam" varStatus = "status">
				<th class = "exam" id = "${ exam.getExamId() }-${ exam.getPeriodId() }">
				${ exam.getExamLibelle() }<br/>
				/${ infosEC.getCourse_notation() } (coef. ${ infosEC.getCourse_coefficient() })<br/>
				crédit: ${ infosEC.getCourse_credit() }
				</th>
			</c:forEach>
		</tr>
	</thead>
	<tbody>
		<c:forEach items = "${ students }" var = "student" varStatus = "status">
			<tr id = "studentsaisie-${ student.getStudent_id() }" class = "row-student">
				<td>${ student.getStudent_name() } ${ student.getStudent_lastname() }</td>
				<c:forEach items = "${ exams }" var = "exam" varStatus = "status">
					<td class = "input-wrapper">
						<i class = "glyphicon glyphicon-ok-sign"></i>
						<i class = "glyphicon glyphicon-warning-sign"></i>
						<input class = "input-note-student" value = '${ student.getEvaluation("".concat(exam.getExamId()).concat("-").concat(exam.getPeriodId())) }' id = "notexam-${ exam.getExamId() }-${ exam.getPeriodId() }-${ student.getStudent_id() }" />
					</td>
				</c:forEach>
			</tr>
		</c:forEach>
	</tbody>
</table>