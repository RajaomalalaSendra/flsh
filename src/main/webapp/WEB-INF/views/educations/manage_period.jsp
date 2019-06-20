<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class = "header-list">
	<div class = "btn-group pull-right form-inline">
		<input type = "hidden" value = "${ au.getUniversity_year_id() }" id = "au-edit" />
		<button id = "return-uy" class = "btn btn-default">
			<i class = "glyphicon glyphicon-arrow-left"></i>
		</button>
		<select id = "niveau-period" class = "btn">
			<c:forEach items="${ levels }" var="lvl" varStatus="status">
				<option value = "${ lvl.getLevelId() }">${ lvl.getLevelLibelle() }</option>
			</c:forEach>
		</select>
		<button id = "add-levelperiod" class = "btn btn-default">
			<i class = "glyphicon glyphicon-plus"></i> Nouveau
		</button>
	</div>
	<h1>${ au.getUniversity_year_libelle() }</h1>
</div>
<div class = "table-container">
	<table class = "table table-bordered">
		<thead>
			<tr>
				<th>Libell� court</th>
				<th>Libell� Long</th>
				<th>D�but</th>
				<th>Fin</th>
				<th>Avec rattrapage</th>
				<th>Examen</th>
				<th>D�but exam</th>
				<th>Fin exam</th>
				<th>Rattrapage</th>
				<th>D�but rattr.</th>
				<th>Fin rattr.</th>
				<th style="width: 140px;">Actions</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
</div>