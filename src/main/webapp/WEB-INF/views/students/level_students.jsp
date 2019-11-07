<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import ="com.flsh.model.User" %>
<% User user = (User) request.getSession().getAttribute("user"); %>
<c:set var="req" value="${pageContext.request}" />
<c:forEach items="${ students }" var="student" varStatus="status">
	<tr id = "stud-${ student.getStudent_id() }">
		<td><img  class = "rounded-image" src="${ student.getCroppedImageURL(sc)}"/></td>
		<td>${ student.getCivilite() }</td>
		<td>${ student.getStudent_name() }</td>
		<td>${ student.getStudent_lastname() }</td>
		<td>${ student.getStudent_birthdate() }</td>
		<td>${ student.getStudent_nationality() }</td>
		<td>${ student.getStudent_adress() }</td>
		<td align = "center">
			<c:if test = "${ student.getNet_delib() == 1 }">
				<a class = "btn btn-sm btn-success print-result-exam" title = "Imprimer le résultats final" id = "student-${ student.getStudent_id() }-${idUY}">
	        		 <i class="glyphicon glyphicon-print"></i>
	        	</a>
	        	<iframe id="iframe-print-final-result-student-${ student.getStudent_id() }-${idUY}" href = "${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" style = "display: none;"></iframe>
			</c:if>	
         	<button class = "btn btn-sm btn-info show-details-student" title = "Afficher les détails sur l'étudiants">
				<i class = "glyphicon glyphicon-eye-open"></i>
			</button>
			<% if( user != null && user.isEnabled() && Integer.parseInt( user.getType()) != 2) { %>
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
	      	<% } %>
		</td>
	</tr>
</c:forEach>	
<c:if test="${ showPage != null && students.size() > 0 }">
	<c:set var="pagenumber" value="${ students.get(0).getNumber() != null ? Math.ceil(students.get(0).getNumber()/100) : 1 }" scope="page" />
	
	<!-- Do not touch to the next line pls -->
	<div id = "pagination-search" style = "display:none;">
		<li class="page-item">
	      <a class="page-link" href="#" id = "previous-page" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	      </a>
	    </li>
		<c:forEach var="i" begin="1" end="${ pagenumber }" step="1">
		    <li class="page-item ${ i == 1 ? 'active' : ''}"><a class="page-link" page-target = "${ i }" href="#"><c:out value="${ i }" /></a></li>
		</c:forEach>
		<li class="page-item">
	      <a class="page-link" href="#" id = "next-page" aria-label="Next">
	        <span aria-hidden="true">&raquo;</span>
	      </a>
	    </li>
	</div>
</c:if>				