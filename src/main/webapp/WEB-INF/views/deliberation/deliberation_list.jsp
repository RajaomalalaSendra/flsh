<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:forEach items = "${ dataEvaluations }" var = "delib">
	<tr class = "ue-row" id = "tr-ue-${ delib.getStudyunit_id() }">
		<td>${ delib.getStudyunit_libelle() }</td>
		<td>-</td>
		<td>-</td><!-- notation -->
		<td></td><!-- coefficient -->
		<td></td><!-- Crédit -->
		<c:forEach items = "${ periodes }" var = "period">
			<td class = "note note-ue-${ period.getPeriod_id() }-1"></td>
			<td></td>
			<c:if test="${ period.isA_ratrappage() }">
				<td class = "note note-ue-${ period.getPeriod_id() }-2"></td>
			</c:if>
		</c:forEach>

       <td><input class = "input-ue-credit credit"  id = "input-ue-${ delib.getStudyunit_id()}" type = "text" value = "${ delib.getCredit_ue()}" disabled></td>
       <td>
           <c:if test="${ delib.getValid_credit_ue() == 0 }">
               <button class = "btn btn-sm btn-danger validate-ue" id = "validue-${ delib.getStudyunit_id() }"><i class= "glyphicon glyphicon-remove"></i></button>
           </c:if>
           <c:if test="${ delib.getValid_credit_ue() == 1 }">
               <button class = "btn btn-sm btn-success validate-ue" id = "validue-${ delib.getStudyunit_id() }"><i class= "glyphicon glyphicon-ok"></i></button>
           </c:if>
       </td>
       <td class = "cumule"></td>
	</tr>	
	<c:forEach items = "${ delib.getCoursesEvaluations() }" var = "ec">
		<tr class = "ec-row ecue-${ delib.getStudyunit_id() }" id = "tr-ec-${ ec.getCourse_id() }">
			<td class = "ec-delib">${ ec.getCourse_libelle() }</td>
			<td>${ ec.getCourse_type() }</td>
			<td class = "notation-ec">${ ec.getCourse_notation() }</td><!-- notation -->
			<td class = "coefficient-ec">${ ec.getCourse_coefficient() }</td><!-- coefficient -->
			<td>${ ec.getCourse_credit() }</td><!-- Crédit -->
			<c:forEach items = "${ periodes }" var = "period">
				<td class = "note note-ec-${ period.getPeriod_id() }-1"> ${ ec.getPeriodNoteBySessionTypeAndId(1, period.getPeriod_id()) }</td>
				<td class = "note"> 
					<span class = "note radio radio-primary">
						<input type="radio" 
								class = "radio-period-ec" 
								id="radio-${ period.getPeriod_id() }-${ ec.getCourse_id() }" 
								value="1" 
								name="radio-${ period.getPeriod_id() }-${ ec.getCourse_id() }" 
								aria-label="Single radio Two"
								${ ec.isNoteOK(period.getPeriod_id()) ? "checked" : "" }>
                    	<label></label>
					</span>
				</td>
				<c:if test="${ period.isA_ratrappage() }">
					<td class = "note note-ec-${ period.getPeriod_id() }-2"> ${ ec.getPeriodNoteBySessionTypeAndId(2, period.getPeriod_id()) }</td>
				</c:if>
			</c:forEach>	
			<td class = "inputCreditEC input-wrapper">
			    <i class="glyphicon glyphicon-ok-sign"></i>
				<i class="glyphicon glyphicon-warning-sign"></i>
				<input class = "input-ec-credit credit" id = "input-ec-${ delib.getStudyunit_id()}-${ ec.getCourse_id() }" max-credit = "${ec.getCourse_credit()}" type = "text" value = "${ec.getCourse_credit_obtenu() == 0  ? '' : ec.getCourse_credit_obtenu()}" />
				<span class = "error-input-ec-credit alert alert-danger"></span>
				<span class = "success-input-ec-credit alert alert-success"></span>
			</td>
			<td></td>
			<td class = "cumule">
				<div class="checkbox checkbox-info" title = "Ajouter/Retirer des EC cumulés">
                    <input  type = "checkbox" class = "styled check-ec-cumule" name = "subs_inscription" id = "checkec-${ ec.getCourse_id() }" ${ ec.isCumule() ? "checked" : "" }>
                    <label></label>
                </div>
			</td>
		</tr>	
	</c:forEach>
</c:forEach>
<tr class = "total-row">
	<td colspan = "5">Total</td>
	<c:forEach items = "${ periodes }" var = "period">
		<td id = "moy-${ period.getPeriod_id() }-1"></td>
		<td></td>
		<c:if test="${ period.isA_ratrappage() }">
			<td id = "moy-${ period.getPeriod_id() }-2"></td>
		</c:if>
	</c:forEach>
	<td id = "total-credit" class = "total-credit">0</td>
	<td><input type = "hidden" name = "delibCurrentUser" id = "delibCurrentUser" value = "${delibCurrentUser}"/></td>
</tr>
<c:forEach items = "${ periodes }" var = "period">
		<th class = "head-period period-exam" id = "period-${ period.getPeriod_id() }-1">${ period.getPeriod_libellecourt() }</th>
		<th class = "head-period head-ok">OK</th>
		<c:if test="${ period.isA_ratrappage() }">
			<th class = "head-period period-exam" id = "period-${ period.getPeriod_id() }-2">Rattr. ${ period.getPeriod_libellecourt() }</th>
		</c:if>
</c:forEach>
<th class = "head-period credit">Créd. obtenu</th>
<th class = "head-period" width = "5%">Validé</th>