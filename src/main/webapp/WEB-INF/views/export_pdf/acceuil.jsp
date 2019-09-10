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
	 	<h1>UNIVERSITE D'ANTANANANARIVO</h1>
	 	<h1>DOMAINE ARTS,LETTRES</h1>
	 	<h1>SCIENCES HUMAINES</h1>
     </div>
     
     <div id = "level-student">
	 	<h1>${ levelStudent.getLevelLibelle() }</h1>
     </div>
     
     <div id = "result">
	 	<h1>FICHE INDIVIDUELLE DE RESULTATS</h1>
     </div>
     
     <div id = "univ-year-and-cycle">
	 	<h1>${ universityYearStudent.getUniversity_year_libelle().toUpperCase() }</h1>
	 	<h1>${ cycleStudent.getCycleLibelle().toUpperCase() } MENTION ETUDE ANGLOPHONE</h1>
	 	<h1>PARCOURS ${ parcoursStudent.getParcoursLibelle().toUpperCase() }</h1>
     </div>
     
     <div id="about-student">
        <p><b>NOM:</b> ${ student.getStudent_name().toUpperCase()  }</p>        	
        <p><b>PRENOM:</b> ${ student.getStudent_lastname().toUpperCase() } </p>
		<p><b>DATE DE NAISSANCE:</b> ${ student.getStudent_birthdate()}</p>
		<p><b>NATIONALITE:</b> ${ student.getStudent_nationality().toUpperCase()}</p>
    </div>
    
    <table class="table table-striped" id = "info-evaluation">
		<thead>
			<tr>
				<th>U.E / E.C</th>
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
			       <td>${delib.getCredit_ue()}</td>
				</tr>	
				<c:forEach items = "${ delib.getCoursesEvaluations() }" var = "ec">
					<tr class = "ec-row ecue-${ delib.getStudyunit_id() }" id = "tr-ec-${ ec.getCourse_id() }">
						<td class = "ec-delib">${ ec.getCourse_libelle() }</td>
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
		</tbody>
	</table>
<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/export_pdf.js" />"></script>
</body>