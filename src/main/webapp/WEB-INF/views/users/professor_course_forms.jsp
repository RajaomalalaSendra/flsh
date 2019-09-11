<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Modal -->
<div class="modal fade" id="ueProfAddModal" tabindex="-1" role="dialog" aria-labelledby="ueProfEditLabel" aria-hidden="true" style="z-index: 10010;">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h5 class="modal-title" id="ueProfAddLabel"></h5>
      </div>
      <div class="modal-body">
      	<div class = "alert alert-danger" style = "display:none" id = "err-save-prof-ue"></div>
		<div class = "alert alert-success" style = "display:none" id = "success-save-prof-ue"></div>
      	<form method = "POST" id = "form-save-prof-ue" action = "<c:url value = '/professor/course/save' />">
      		<input type="hidden" name = "id" id = "idUE">
      		<input type="hidden" name = "id_parcours" id = "idParcoursUE">
			 <div class="tab-content">
		      		<div class = "form-group form-inline ue-margin">
			      	    <label class="col-md-5">Unite d'enseignement:</label>
			      		<select class="form-control" name="type" id="ueIdProfResponsable">
			      			<c:forEach items="${ study_units }" var="study_unit" varStatus="status">
								<option value="${ study_unit.getStudyunit_id()}">${ study_unit.getStudyunit_libelle() }</option>
							</c:forEach>
						</select>
			      		<small class="form-text text-muted error"></small>
			      	</div>
			      	<div class = "form-group form-inline">
			      	    <label class="col-md-5">Responsable:</label>
			      		<select class="form-control" name="type" id="ueProfResponsable">
				      		<c:forEach items="${ professors}" var="professor" varStatus="status">
									<option value="${ professor.getProfessor_id()}">${ professor.getCivilite() } ${ professor.getProfessor_name()} ${ professor.getProfessor_last_name()}</option>
							</c:forEach>
						</select>
			      		<small class="form-text text-muted error"></small>
			      	</div>
	      		</div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
        <button type="button" class="btn btn-primary" id = "save-prof-ue">Enregistrer</button>
      </div>
    </div>
  </div>
</div>

<!-- Modal Delete User -->
<div class="modal fade" id="ueDeleteModal" tabindex="-1" role="dialog" aria-labelledby="ueEditLabel" aria-hidden="true" style="z-index: 10010;">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form method = "POST" id = "form-delete-ue" action = "<c:url value = '/ue/delete' />">
	      <div class="modal-header" style="background-color: #D33;">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	        <h5 class="modal-title" >Supprimer Unite Enseignement ?</h5>
	      </div>
	      <div class="modal-body">
		      	<input type="hidden" name = "id" id = "idUEDelete" value = "">
		      	<div class = "alert alert-danger" style = "display:none" id = "err-delete-ue"></div>
		      	<div class = "alert alert-success" style = "display:none" id = "success-delete-ue"></div>
		      	<p id = "question-delete-ue">
		      	      	Voulez-vous vraiment supprimer cet unite d'enseignement ? Cette action est irréversible
		      	</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
	        <button type="submit" class="btn btn-danger" id = "delete-ue">Supprimer</button>
	      </div>
        </form>
      </div>
  	</div>
  </div>
  