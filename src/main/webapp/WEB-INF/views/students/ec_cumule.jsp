<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items = "${ ecs }" var = "ec" varStatus = "status">
	<div class = "list-group-item ec-cumule-etudiant" id = "cumule-${ ec.getCourse_id() }" >
		${ ec.getUeNiveau() }: ${ ec.getCourse_libellelong() }
		<span class = "remove-cumule pull-right">
			<i class = "glyphicon glyphicon-remove-sign"></i>
		</span>
	</div>
</c:forEach>