<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <c:forEach var="unit" items="${units}">   
   <tr id="ue-${unit.getStudyunit_id()}">  
	   <td scope="row">${unit.getStudyunit_libelle()} (${unit.getStudyunit_type()})</td>  
	   <td colspan = "7" align = "right" ></td>
	   <c:forEach var="period" items="${periods}"> 
 			<td class = "period-ue">
 				<span class="checkbox checkbox-primary">
                     <input  type = "checkbox" class = "styled check-ue" id = "checkperiod-${unit.getStudyunit_id()}-${period.getPeriod_id()}">
                     <label></label>
                 </span>
 			</td>
	   </c:forEach>
	   <c:forEach var="course" items="${unit.getCourses()}">
	   		<tr class = "ec" id = "ec-${course.getCourse_id()}">
	   			<td class = "libelle">${course.getCourse_libelle()}</td>
	   			<td>${course.getProfessor()}</td>
	   			<td>${course.getCourse_credit()}</td>
	   			<td>${course.getCourse_notation()}</td>
	   			<td>${course.getCourse_coefficient()}</td>
	   			<td>${course.getCourse_volumehoraire()}</td>
	   			<td>${course.getCourse_travailpresenciel()}</td>
	   			<td>${course.getCourse_travailpersonnel()}</td>
	   			<c:forEach var="period" items="${periods}"> 
		   			<td class = "period-ec">
		   				<span class="checkbox checkbox-primary">
		                     <input  type = "checkbox" ${course.getIdPeriods().contains( String.valueOf(period.getPeriod_id())) ? 'checked' : ''} class = "styled check-ec check-${unit.getStudyunit_id()}-${period.getPeriod_id()}" id = "checkperiod-${course.getCourse_id()}-${period.getPeriod_id()}-${unit.getStudyunit_id()}">
		                     <label></label>
		                 </span>
		   			</td>
	 			</c:forEach>
	   		</tr>				   
	   </c:forEach>				         
   </tr>  
</c:forEach>
	 <c:forEach var="period" items="${periods}"> 
	 	<th class = "period">
			${ period.getPeriod_libellecourt() }
		</th>
	 </c:forEach>