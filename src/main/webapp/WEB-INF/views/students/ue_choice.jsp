<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items = "${ ueList }" var = "ue" varStatus = "status">
	<div class = "card">
		<h5 class = "card-title">Choix ${ ue.getStudyunit_libelle() }</h5>
		<div class = "card-body">
			<c:forEach items = "${ ue.getCourses() }" var = "ec" varStatus = "ec_status">
				<div class = "form-group form-inline">
					<label class = "col-md-6" for = "${ ue.getStudyunit_libelle() }-${ ec.getCourse_id() }">${ ec.getCourse_libelle() }</label>
					<div class="radio radio-success">
                        <input type="radio" ${ ec_status.index == 0 ? 'checked' : '' } id="${ ue.getStudyunit_libelle() }-${ ec.getCourse_id() }" value="${ ec.getCourse_id() }" name="${ ue.getStudyunit_id() }" aria-label="Single radio Two">
                        <label></label>
                    </div>
				</div>
			</c:forEach>
		</div>
	</div>
</c:forEach>