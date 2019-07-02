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
      		<div class = "form-group form-inline">
	      	    <label class="col-sm-3">Libelle:</label>
	      		<input class="form-control" type="text" name = "libelle" id = "ueLibelle" required>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label class="col-sm-3">Type:</label>
	      		<select class="form-control" name="type" id="ueType">
	      	    	<option value="OBLIGATOIRE">Obligatoire</option>
	      	    	<option value="FACULTATIF">Facultatif</option>
	      	    	<option value="AU CHOIX">Au choix</option>
	      		</select>
	      		<small class="form-text text-muted error"></small>
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
		      	<input type="hidden" name = "idUEDelete" id = "idUEDelete" value = "">
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
  