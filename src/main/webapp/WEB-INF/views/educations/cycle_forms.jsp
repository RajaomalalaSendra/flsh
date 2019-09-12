<!-- Modal -->
<div class="modal fade" id="cycleEditModal" tabindex="-1" role="dialog" aria-labelledby="cycleEditLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h5 class="modal-title" id="cycleEditLabel">Ajouter un cycle</h5>
      </div>
      <div class="modal-body">
      	<form method = "POST" id = "form-save-cycle" action = "<c:url value = '/education/saveCycle' />">
      	
      		<input type="hidden" name = "type" id = "type" value = "cycle">
	      	<input type="hidden" name = "idCycle" id = "idCycle" value = "">
	      	<input type="hidden" name = "idLevel" id = "idLevel" value = "">
	      	<input type="hidden" name = "idParcours" id = "idParcours" value = "">
	      	
	      	<div class = "alert alert-danger" style = "display:none" id = "err-save-cycle"></div>
		    <div class = "alert alert-success" style = "display:none" id = "success-save-cycle"></div>
	        <div class = "form-group form-inline">
	        	<label for = "labelCycle" class = "col-md-6">Libellé</label>
	        	<input type = "text" class = "form-control" name = "labelCycle" id = "labelCycle" required />
	        	<small class="form-text text-muted error"></small>
	        </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
        <button type="button" class="btn btn-primary" id = "save-cycle">Enregistrer</button>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="cycleDeleteModal" tabindex="-1" role="dialog" aria-labelledby="cycleDeleteLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form method = "POST" id = "form-delete-cycle" action = "<c:url value = '/education/saveCycle' />">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	        <h5 class="modal-title" id="cycleDeleteLabel">Supprimer cycle ?</h5>
	      </div>
	      <div class="modal-body">
	      		<input type="hidden" name = "typeDeletion" id = "typeDeletion" value = "">
		      	<input type="hidden" name = "idItemDelete" id = "idItemDelete" value = "">
		      	<div class = "alert alert-danger" style = "display:none" id = "err-delete-cycle"></div>
		      	<div class = "alert alert-success" style = "display:none" id = "success-delete-cycle"></div>
		      	<p id = "question-delete">
		      	La suppression du cycle engendrera la suppression des parcours et des niveaux dans le cycle.<br/>
		      	Voulez-vous vraiment supprimer ? Cette action est irréversible
		      	</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
	        <button type="submit" class="btn btn-primary" id = "save-cycle">Supprimer</button>
	      </div>
      </form>
    </div>
  </div>
</div>