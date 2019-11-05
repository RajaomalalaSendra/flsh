<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items="${ students }" var="student" varStatus="status">
	<tr id = "stud-${ student.getStudent_id() }">
		<td><img  class = "rounded-image" src="${ student.getCroppedImageURL(sc)}"/></td>
		<td>${ student.getCivilite() }</td>
		<td>${ student.getStudent_name() }</td>
		<td>${ student.getStudent_lastname() }</td>
		<td>${ student.getStudent_birthdate() }</td>
		<td>${ student.getStudent_nationality() }</td>
		<td>${ student.getStudent_adress() }</td>
		<td>
			<button class = "btn btn-sm btn-primary edit-student" title = "&eacute;diter un &eacute;tudiant">
				<i class = "glyphicon glyphicon-pencil"></i>
			</button>
			<button class = "btn btn-sm btn-danger delete-student" title = "supprimer un &eacute;tudiant">
				<i class = "glyphicon glyphicon-trash"></i>
			</button>
		</td>
	</tr>
</c:forEach>
<c:if test="${ isSearch != null && students.size() > 0 }">
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