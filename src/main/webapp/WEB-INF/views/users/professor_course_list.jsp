<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="prof_and_ue-${prof.getProfessor_id()}">
	<c:forEach var="stdUnt" items="${stdUnts}">
		<tr>
			<td>${stdUnt.getLevel_libelle()}</td>
			<td>${stdUnt.getParcours_libelle()}</td>
			<td>${stdUnt.getStudy_unit_libelle()}</td>
			<td>${stdUnt.getNonResponsability()}</td>
			<td>${stdUnt.getResponsability()}</td>
		</tr>
	</c:forEach>
	<c:forEach var="course" items="${courses}">
		<tr>  
		  <td>${course.getLevel_libelle()}</td>
		  <td>${course.getParcours_libelle()}</td>
		  <td>${course.getStudy_unit_libelle()}</td>
		  <td>${course.getCourse_libelle()}</td>
		  <td>${course.getProfessorCourse()}</td>  
		</tr>
	</c:forEach>
</div>