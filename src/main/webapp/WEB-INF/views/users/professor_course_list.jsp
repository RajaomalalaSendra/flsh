<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tr id="prof_and_ue-${prof.getProfessor_id()}">  
  <td scope="row">${prof.getCivilite()}  ${prof.getProfessor_last_name()} ${prof.getProfessor_name()}</td>
  <td>
  	<c:forEach var="stdUnt" items="${stdUnts}">	
	    <p>${stdUnt.getStudy_unit_libelle()}</p>
	</c:forEach>
  </td>
  <td>
    <c:forEach var="course" items="${courses}">	
	  <p>${course.getCourse_libelle()}</p>
    </c:forEach>
  </td>  
  <td>
  <span class = "btn-group pull-right">
  			<button class = "btn btn-sm btn-default edit-professor-ue" id-prof-ue = "${prof.getProfessor_id()}" id-prof-for-ue = "${prof.getUser_id()}" title = "Editer Professeur Unite d'Enseignement">
       		<i class = "glyphicon glyphicon-pencil"></i>
       	</button> 
       	<button class = "btn btn-sm btn-danger delete-professor"  id-prof-delete-ue = "${prof.getProfessor_id()}" id-profdelete-ue = "${prof.getUser_id()}" title = "Effacer Professeur d'un Unite d'Enseignement">
       		<i class = "glyphicon glyphicon-trash"></i>
       	</button>
       </span>
   </td>  
</tr>