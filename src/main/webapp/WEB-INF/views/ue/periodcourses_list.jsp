<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import ="com.flsh.model.User" %>
<% User user = (User) request.getSession().getAttribute("user"); %>
 <c:forEach var="unit" items="${units}">   
   <tr id="ue-${unit.getStudyunit_id()}">  
	   <td scope="row">${unit.getStudyunit_libelle()} (${unit.getStudyunit_type()})</td>  
	   <td >
	   	<ul>
		   	<c:forEach var="professor_ue" items="${ unit.getResponsables() }">
				 <li>${professor_ue.getCivilite()} ${professor_ue.getProfessor_last_name()} ${professor_ue.getProfessor_name()}</li>
		   	</c:forEach>
		</ul>
	   </td>
	   <td colspan = "6" align = "right" ></td>
	   <c:forEach var="period" items="${periods}"> 
 			<td class = "period-ue">
 				<span class="checkbox checkbox-primary">
                     <input  
                     type = "checkbox" 
                     class = "styled check-ue" 
                     <%= user != null && user.isEnabled() && Integer.parseInt( user.getType()) == 2 ? "disabled" : "" %>
                     id = "checkperiod-${unit.getStudyunit_id()}-${period.getPeriod_id()}">
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
		                     <input  
			                     type = "checkbox" 
			                     <%= user != null && user.isEnabled() && Integer.parseInt( user.getType()) == 2 ? "disabled" : "" %>
			                     ${course.getIdPeriods().contains( String.valueOf(period.getPeriod_id())) ? 'checked' : ''} 
			                     class = "styled check-ec check-${unit.getStudyunit_id()}-${period.getPeriod_id()}" 
			                     id = "checkperiod-${course.getCourse_id()}-${period.getPeriod_id()}-${unit.getStudyunit_id()}">
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