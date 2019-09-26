<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Modal -->
<div class="modal fade" id="ueAddModal" tabindex="-1" role="dialog" aria-labelledby="ueEditLabel" aria-hidden="true" style="z-index: 10010;">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h5 class="modal-title" id="ueAddLabel">Ajouter un Unite d'enseignement</h5>
      </div>
      <div class="modal-body">
      	<div class = "alert alert-danger" style = "display:none" id = "err-save-ue"></div>
		<div class = "alert alert-success" style = "display:none" id = "success-save-ue"></div>
      	<form method = "POST" id = "form-save-ue" action = "<c:url value = '/ue/save' />">
      		<input type="hidden" name = "id" id = "idUE">
      		<input type="hidden" name = "id_parcours" id = "idParcoursUE">
      		<ul class="nav nav-tabs">
			  <li class="active"><a data-toggle="tab" href="#infos">Infos</a></li>
			  <li><a data-toggle="tab" href="#responsqbles">Responsables</a></li>
			</ul>
			 <div class="tab-content">
		    	<div class = "tab-pane fade in active" id = "infos">
		      		<div class = "form-group form-inline ue-margin">
			      	    <label class="col-sm-3">Libelle court:</label>
			      		<input class="form-control" type="text" name = "libellecourt" id = "ueLibelleCourt" required>
			      		<small class="form-text text-muted error"></small>
			      	</div>
			      	<div class = "form-group form-inline ue-margin">
			      	    <label class="col-sm-3">Libelle long:</label>
			      		<input class="form-control" type="text" name = "libellelong" id = "ueLibelleLong" required>
			      		<small class="form-text text-muted error"></small>
			      	</div>
	      		</div>
	      		<div class = "tab-pane fade in ue-margin" id = "responsqbles">
		      	    <div class="list-group">
                        <c:forEach items="${ profs }" var="prof" varStatus="status">
	                        <div class="list-group-item">
								<label for  = "profResponsable-${ prof.getProfessor_id()}">${ prof.getCivilite() } ${ prof.getProfessor_name()} ${ prof.getProfessor_last_name()}</label>
								<div class="pull-right">
									<div class="checkbox checkbox-success">
										<input type = "checkbox" class = "styled" name = "prof_responsable" id = "profResponsable-${ prof.getProfessor_id()}"/>
										<label></label>
									</div>
								</div>
							</div>
						</c:forEach>
	                </div>
	      		</div>
	      	</div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
        <button type="button" class="btn btn-primary" id = "save-ue">Enregistrer</button>
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
  