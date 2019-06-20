<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html class="no-js" lang="en">
<head>
	<%@include file="../common/_header.jsp" %>
	 <link rel="stylesheet" href="<c:url value="/resources/css/cycle.css" />">
</head>
<body>
	
    <%@include file="../common/_sidebar.jsp" %>
    <!-- Start Welcome area -->
    <div class="all-content-wrapper">
    	<%@include file="../common/_menu.jsp" %>
    	<br/><br/><br/><br/>
    	<div id = "main-wrapper">
    		<div class = "header-list">
    			<button class = "btn btn-primary pull-right" id = "add-cycle" title = "Ajouter un cycle"><i class = "glyphicon glyphicon-plus"></i> Ajouter</button>
    			<h3>Cycles, niveaux et parcours</h3>
    		</div>
    		<c:forEach items="${ listCycle }" var="cycle" varStatus="status">
    			<div class = "cycle" id-cycle = "<c:out value='${ cycle.getCyclesId() }' />">
	    			<div class="card-header" id="heading-<c:out value='${ status.count }' />">
				      <h2 class="mb-0">
				        <button class="btn btn-link label-cycle" id = "labelCycle-<c:out value='${ cycle.getCyclesId() }' />" type="button" data-toggle="collapse" data-target="#collapse-<c:out value='${ status.count }' />" aria-expanded="true" aria-controls="collapse-<c:out value='${ status.count }' />">
				          	<c:out value="${ cycle.getCycleLibelle() }" />
				        </button>
				        <span class = "btn-group pull-right">
				        	<button class = "btn btn-sm btn-success add-level" title = "Add new level on cycle">
				        		<i class = "glyphicon glyphicon-plus"></i>
				        	</button> 
				        	<button class = "btn btn-sm btn-primary edit-cycle" title = "Edit cycle">
				        		<i class = "glyphicon glyphicon-pencil"></i>
				        	</button> 
				        	<button class = "btn btn-sm btn-danger delete-cycle" title = "Delete cycle">
				        		<i class = "glyphicon glyphicon-trash"></i>
				        	</button>
				        </span>
				      </h2>
				    </div>
				
				    <div id="collapse-<c:out value='${ status.count }' />" class="collapse" aria-labelledby="heading-<c:out value='${ status.count }' />" data-parent="#main-wrapper">
				      <div class="card-body">
				      	<c:forEach items = "${ cycle.getLevels() }" var = "level" varStatus = "levelStatus">
				      		<div class = "level-wrapper row" id-level = "${ level.getLevelId() }">
				      			<div class = "col-xs-6">
				      				<div class = "btn-group pull-right">
				      					<button class = "btn btn-sm btn-info btn-circle add-parcours" title = "Add new parcours on level">
							        		<i class = "glyphicon glyphicon-plus"></i>
							        	</button> 
							        	<button class = "btn btn-sm btn-warning btn-circle edit-level" title = "Edit level">
							        		<i class = "glyphicon glyphicon-pencil"></i>
							        	</button> 
							        	<button class = "btn btn-sm btn-danger btn-circle delete-level" title = "Delete level">
							        		<i class = "glyphicon glyphicon-remove"></i>
							        	</button>
				      				</div>
				      				<h3>${ level.getLevelLibelle() }</h3>
				      			</div>
				      			<div class = "col-xs-6">
				      				<ul class="basic-list">
					      				<c:forEach items = "${ level.getParcours() }" var = "parcours" varStatus = "parcoursStatus">
					      					<li id = "parcours-${ parcours.getParcoursId() }">
					      						<label class = "label-parcours">${ parcours.getParcoursLibelle() }</label>
					      						<span class="pull-right btn-group">
					      							<button class = "btn btn-sm btn-default btn-circle edit-parcours" title = "Edit parcours">
										        		<i class = "glyphicon glyphicon-pencil"></i>
										        	</button> 
										        	<button class = "btn btn-sm btn-default btn-circle delete-parcours" title = "Delete parcours">
										        		<i class = "glyphicon glyphicon-remove-sign"></i>
										        	</button>
					      						</span>
					      					</li>	
					      				</c:forEach>
	                    			</ul>
				      			</div>
				      		</div>
				      	</c:forEach>
				      </div>
				    </div>
  				</div>
			</c:forEach>
    	</div>
    	<div id = "modals-wrapper">
    		<%@include file="cycle_forms.jsp" %>
    	</div>
    	<%@include file="../common/_footer.jsp" %>
    </div>

<%@include file="../common/_script.jsp" %>
<script src="<c:url value="/resources/js/cycle.js" />"></script>
</body>
</html>
