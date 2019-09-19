<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import ="com.flsh.model.User" %>
<% User onlineUser = (User) request.getSession().getAttribute("user"); %>

<c:forEach var="professor" items="${professors}">   
   <tr id="prof-${professor.getProfessor_id()}">  
	   <td scope="row">${professor.getProfessor_id()}</td>
	   <td>${professor.getCivilite()}</td>  
	   <td>${professor.getProfessor_name()}</td>
	   <td>${professor.getProfessor_last_name()}</td>  
	   <td>${professor.getProfessor_email()}</td>
	   <td>${professor.getProfessor_adresse()}</td>
	   <td>${professor.getProfessor_contact()}</td>    
	   <td>
	   <span class = "btn-group pull-right">
	   			<a class = "btn btn-sm btn-info detail-professor" href="professor/details?id=${professor.getProfessor_id()}" title="afficher les details concernant le professeur"><i class = "glyphicon glyphicon-eye-open"></i></a>
	        	<% if( onlineUser != null && onlineUser.isEnabled() && Integer.parseInt( onlineUser.getType()) == 1) { %>
		        	<button class = "btn btn-sm btn-primary edit-professor" id-prof = "${professor.getProfessor_id()}" id-prof-for-user = "${professor.getUser_id()}" title = "Edit Professor">
		        		<i class = "glyphicon glyphicon-pencil"></i>
		        	</button> 
		        	<button class = "btn btn-sm btn-danger delete-professor"  id-profd = "${professor.getProfessor_id()}" id-profdelete = "${professor.getUser_id()}" title = "Delete Professor">
		        		<i class = "glyphicon glyphicon-trash"></i>
		        	</button>
	        	<% } %>
	        </span>
	    </td>  
  </tr>  
</c:forEach>
<c:if test="${ addPagination != null && professors.size() > 0 }">
	<c:set var="pagenumber" value="${ professors.get(0).getNumber() != null ? Math.ceil(professors.get(0).getNumber()/50) : 1 }" scope="page" />
	
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