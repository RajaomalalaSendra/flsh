<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach var="user" items="${users}">   
	<tr id="user-${user.getId()}">  
	   <td scope="row">${user.getId()}</td>
	   <td>${user.getCivilite()}</td>  
	   <td>${user.getLastname()}</td>
	   <td>${user.getFirstname()}</td>
	   <td>${user.getUsername()}</td>  
	   <td>${user.getEmail()}</td>  
	   <td>${user.getTypeComputed()}</td>  
	   <td>
	   		<span class = "btn-group pull-right">
	   			<a class = "btn btn-sm btn-info detail-user" href="user/details?id=${user.getId()}" title="afficher les details concernant l'utilisateur"><i class = "glyphicon glyphicon-eye-open"></i></a>
	        	<button class = "btn btn-sm btn-primary edit-user" id-user = "${user.getId()}" title = "Edit user">
	        		<i class = "glyphicon glyphicon-pencil"></i>
	        	</button> 
	        	<button class = "btn btn-sm btn-danger delete-user" id-user-delete = "${user.getId()}" title = "Delete user">
	        		<i class = "glyphicon glyphicon-trash"></i>
	        	</button>
	        </span>
	    </td>  
	</tr>  
</c:forEach>

<c:if test="${ showPage != null && users.size() > 0 }">
	<c:set var="pagenumber" value="${ users.get(0).getMaxnumber() != null ? Math.ceil(users.get(0).getMaxnumber()/50) : 1 }" scope="page" />
	
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