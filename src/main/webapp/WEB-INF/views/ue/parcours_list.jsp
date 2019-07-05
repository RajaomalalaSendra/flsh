<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <c:forEach var="unit" items="${units}">   
   <tr id="ue-${unit.getStudyunit_id()}">  
	   <td scope="row">${unit.getStudyunit_libelle()} (${unit.getStudyunit_type()})</td>  
	   <td colspan = "6" align = "right" ></td>
	   <td>
	   		<span class = "btn-group pull-right">
	   			<button class = "btn btn-sm btn-info add-ec" title = "Ajouter un élément constitutif" ec-add-id = "${unit.getStudyunit_id()}">
	        		<i class = "glyphicon glyphicon-plus-sign"></i>
	        	</button>
	        	<button class = "btn btn-sm btn-primary edit-ue" title = "Editer unité d'enseignement" ue-edit-id = "${unit.getStudyunit_id()}">
	        		<i class = "glyphicon glyphicon-pencil"></i>
	        	</button> 
	        	<button class = "btn btn-sm btn-danger delete-ue" title = "Supprimer unité d'enseignement" ue-delete-id = "${unit.getStudyunit_id()}">
	        		<i class = "glyphicon glyphicon-trash"></i>
	        	</button>
	        </span>
	    </td>
	   <c:forEach var="course" items="${unit.getCourses()}">
	   		<tr class = "ec" id = "ec-${course.getCourse_id()}">
	   			<td class = "libelle">${course.getCourse_libelle()}</td>
	   			<td>${course.getCourse_credit()}</td>
	   			<td>${course.getCourse_notation()}</td>
	   			<td>${course.getCourse_coefficient()}</td>
	   			<td>${course.getCourse_volumehoraire()}</td>
	   			<td>${course.getCourse_travailpresenciel()}</td>
	   			<td>${course.getCourse_travailpersonnel()}</td>
	   			<td>
			   		<span class = "btn-group pull-right">
			   			<i class = "glyphicon glyphicon-pencil edit-ec" title="Editer élément constitutif" ec-edit-id="${course.getCourse_id()}"></i>
			        	<i class = "glyphicon glyphicon-trash delete-ec" title="Supprimer élément constitutif" ec-delete-id="${course.getCourse_id()}"></i>
			        </span>
			    </td>
	   		</tr>				   
	   </c:forEach>				         
   </tr>  
</c:forEach>