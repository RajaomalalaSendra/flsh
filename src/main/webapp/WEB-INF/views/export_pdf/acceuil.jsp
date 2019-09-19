<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html class="no-js" lang="en">
<head>
	<%@include file="../common/_header.jsp" %>
	 <link rel="stylesheet" href="<c:url value="/resources/css/export_pdf.css" />">
</head>
<body>
	 <div id = "university-name">
	 	<h5>UNIVERSITE D'ANTANANANARIVO</h5>
	 	<h5>DOMAINE ARTS,LETTRES</h5>
	 	<h5>SCIENCES HUMAINES</h5>
     </div>
     
     <div id = "level-student">
	 	<h2>${ levelStudent.getLevelLibelle() }</h2>
     </div>
     
     <div id = "result">
	 	<h2>FICHE INDIVIDUELLE DE RESULTATS</h2>
     </div>
     
     <div id = "univ-year-and-cycle">
	 	<h5>${ universityYearStudent.getUniversity_year_libelle().toUpperCase() }</h5>
	 	<h5>${ cycleStudent.getCycleLibelle().toUpperCase() } MENTION ETUDE ANGLOPHONE</h5>
	 	<h5>PARCOURS ${ parcoursStudent.getParcoursLibelle().toUpperCase() }</h5>
     </div>
     
     <div id="about-student">
        <p><b>NOM:</b> ${ student.getStudent_name().toUpperCase()  }</p>        	
        <p><b>PRENOM:</b> ${ student.getStudent_lastname().toUpperCase() } </p>
    </div>
    
    <table class="table table-bordered" id = "info-evaluation">
		<thead>
			<tr>
				<th>U.E / E.C</th>
				<th>Vol. Hor.</th>
				<th>Type</th>
				<th>Notation</th>
				<th>Coef</th>
				<th>Crédit (max)</th>				
				<c:forEach items = "${ periodesStudent }" var = "period">
						<th class = "head-period period-exam" id = "period-${ period.getPeriod_id() }-1">${ period.getPeriod_libellecourt() }</th>
						<c:if test="${ period.isA_ratrappage() }">
							<th class = "head-period period-exam" id = "period-${ period.getPeriod_id() }-2">Rattr. ${ period.getPeriod_libellecourt() }</th>
						</c:if>
				</c:forEach>
				<th>Crédit Obtenu</th>
			</tr>
		</thead>
		<tbody class = "row-delib">
			<c:forEach items = "${ dataEvaluationsStudent }" var = "delib">
				<tr class = "ue-row" id = "tr-ue-${ delib.getStudyunit_id() }">
					<td>${ delib.getStudyunit_libelle() }</td>
					<td>-</td>
					<td>${ delib.getStudyunit_type() }</td>
					<td>-</td><!-- notation -->
					<td></td><!-- coefficient -->
					<td></td><!-- Crédit -->
					<c:forEach items = "${ periodesStudent }" var = "period">
						<td class = "note-ue-${ period.getPeriod_id() }-1"></td>
						<c:if test="${ period.isA_ratrappage() }">
							<td class = "note-ue-${ period.getPeriod_id() }-2"></td>
						</c:if>
					</c:forEach>
			       <td class = "input-ue-credit">${delib.getCredit_ue()}</td>
				</tr>	
				<c:forEach items = "${ delib.getCoursesEvaluations() }" var = "ec">
					<tr class = "ec-row ecue-${ delib.getStudyunit_id() }" id = "tr-ec-${ ec.getCourse_id() }">
						<td class = "ec-delib">${ ec.getCourse_libelle() }</td>
						<td>${ ec.getCourse_volumehoraire() }</td>
						<td>-</td>
						<td class = "notation-ec">${ ec.getCourse_notation() }</td><!-- notation -->
						<td class = "coefficient-ec">${ ec.getCourse_coefficient() }</td><!-- coefficient -->
						<td>${ ec.getCourse_credit() }</td><!-- Crédit -->
						<c:forEach items = "${ periodesStudent }" var = "period">
							<td class = "note-ec-${ period.getPeriod_id() }-1"> ${ ec.getPeriodNoteBySessionTypeAndId(1, period.getPeriod_id()) }</td>
							<c:if test="${ period.isA_ratrappage() }">
								<td class = "note-ec-${ period.getPeriod_id() }-2"> ${ ec.getPeriodNoteBySessionTypeAndId(2, period.getPeriod_id()) }</td>
							</c:if>
						</c:forEach>	
						<td class = "inputCreditEC input-wrapper">
						    <c:if test="${ ec.getCourse_credit_obtenu() == 0 }">
								-
							</c:if>
							<c:if test="${ ec.getCourse_credit_obtenu() != 0 }">
								${ec.getCourse_credit_obtenu()}
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</c:forEach>
			<tr class = "total-row">
					<td colspan = "6">Total</td>
					<c:forEach items = "${ periodesStudent }" var = "period">
						<td id = "moy-${ period.getPeriod_id() }-1"></td>
						<c:if test="${ period.isA_ratrappage() }">
							<td id = "moy-${ period.getPeriod_id() }-2"></td>
						</c:if>
					</c:forEach>
					<td id = "total-credit" class = "total-credit">0</td>
				</tr>
		</tbody>
	</table>
	<div id = "result-current-student">
		<p>Credits valides: </p>
		<p>Resultat: ${delibCurrentUser}</p>
		<p>Fait à Antananarivo le ${dateNow}</p>
	</div>
	<div id = "jury-member" ><p>Les membres de Jury,</p></div>
	<div id = "jury-president" ><p>Le president de Jury,</p></div>
<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/export_pdf.js" />"></script>
</body>