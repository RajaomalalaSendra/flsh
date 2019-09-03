<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:forEach items = "${ dataEvaluations }" var = "delib">
	<tr class = "ue-row" id = "tr-ue-${ delib.getStudyunit_id() }">
		<td>${ delib.getStudyunit_libelle() }</td>
		<td>${ delib.getStudyunit_type() }</td>
		<td>-</td><!-- notation -->
		<td></td><!-- coefficient -->
		<td></td><!-- Crédit -->
		<c:forEach items = "${ periodes }" var = "period">
			<td class = "note-ue-${ period.getPeriod_id() }-1"></td>
			<c:if test="${ period.isA_ratrappage() }">
				<td class = "note-ue-${ period.getPeriod_id() }-2"></td>
			</c:if>
		</c:forEach>

       <td><input class = "input-ue-credit"  id = "input-ue-${ delib.getStudyunit_id()}" type = "text" value = "${ delib.getCredit_ue()}" disabled></td>
       <td>
           <c:if test="${ delib.getValid_credit_ue() == 0 }">
               <button class = "btn btn-sm btn-danger" id = "danger-ue-${ delib.getStudyunit_id() }"><i class= "glyphicon glyphicon-remove"></i></button>
               <button class = "btn btn-sm btn-success succ-none" id = "success-ue-${ delib.getStudyunit_id() }"><i class= "glyphicon glyphicon-ok"></i></button>
           </c:if>
           <c:if test="${ delib.getValid_credit_ue() == 1 }">
               <button class = "btn btn-sm btn-success" id = "success-ue-${ delib.getStudyunit_id() }"><i class= "glyphicon glyphicon-ok"></i></button>
               <button class = "btn btn-sm btn-danger dang-none" id = "danger-ue-${ delib.getStudyunit_id() }"><i class= "glyphicon glyphicon-remove"></i></button>
           </c:if>
       </td>
	</tr>	
	<c:forEach items = "${ delib.getCoursesEvaluations() }" var = "ec">
		<tr class = "ec-row ecue-${ delib.getStudyunit_id() }" id = "tr-ec-${ ec.getCourse_id() }">
			<td class = "ec-delib">${ ec.getCourse_libelle() }</td>
			<td>-</td>
			<td class = "notation-ec">${ ec.getCourse_notation() }</td><!-- notation -->
			<td class = "coefficient-ec">${ ec.getCourse_coefficient() }</td><!-- coefficient -->
			<td>${ ec.getCourse_credit() }</td><!-- Crédit -->
			<c:forEach items = "${ periodes }" var = "period">
				<td class = "note-ec-${ period.getPeriod_id() }-1"> ${ ec.getPeriodNoteBySessionTypeAndId(1, period.getPeriod_id()) }</td>
				<c:if test="${ period.isA_ratrappage() }">
					<td class = "note-ec-${ period.getPeriod_id() }-2"> ${ ec.getPeriodNoteBySessionTypeAndId(2, period.getPeriod_id()) }</td>
				</c:if>
			</c:forEach>	
			<td class = "inputCreditEC input-wrapper">
			    <i class="glyphicon glyphicon-ok-sign"></i>
				<i class="glyphicon glyphicon-warning-sign"></i>
				<c:if test="${ ec.getCourse_credit_obtenu() == 0 }">
					<input class = "input-ec-credit" id = "input-ec-${ delib.getStudyunit_id()}-${ ec.getCourse_id() }" max-credit = "${ec.getCourse_credit()}" type = "text">
				</c:if>
				<c:if test="${ ec.getCourse_credit_obtenu() != 0 }">
					<input class = "input-ec-credit" id = "input-ec-${ delib.getStudyunit_id()}-${ ec.getCourse_id() }" max-credit = "${ec.getCourse_credit()}" type = "text" value = "${ec.getCourse_credit_obtenu()}">
				</c:if>
				<div class = "error-input-ec-credit alert alert-danger"></div>
				<div class = "success-input-ec-credit alert alert-success"></div>
			</td>
			<td></td>
		</tr>	
	</c:forEach>
</c:forEach>
<tr class = "total-row">
	<td colspan = "5">Total</td>
	<c:forEach items = "${ periodes }" var = "period">
		<td id = "moy-${ period.getPeriod_id() }-1"></td>
		<c:if test="${ period.isA_ratrappage() }">
			<td id = "moy-${ period.getPeriod_id() }-2"></td>
		</c:if>
	</c:forEach>
	<td id = "total-credit" class = "total-credit">0</td>
	<td><input type = "hidden" name = "delibCurrentUser" id = "delibCurrentUser" value = "${delibCurrentUser}"/></td>
</tr>
<c:forEach items = "${ periodes }" var = "period">
		<th class = "head-period period-exam" id = "period-${ period.getPeriod_id() }-1">${ period.getPeriod_libellecourt() }</th>
		<c:if test="${ period.isA_ratrappage() }">
			<th class = "head-period period-exam" id = "period-${ period.getPeriod_id() }-2">Rattr. ${ period.getPeriod_libellecourt() }</th>
		</c:if>
</c:forEach>
<th class = "head-period">Créd. obtenu</th>
<th class = "head-period">Validé</th>