<!-- Modal -->
<div class="modal fade" id="profAddModal" tabindex="-1" role="dialog" aria-labelledby="profEditLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h5 class="modal-title" id="profAddLabel">Ajouter un professeur</h5>
      </div>
      <div class="modal-body">
      	        <div class = "alert alert-danger" style = "display:none" id = "err-save-prof"></div>
		    <div class = "alert alert-success" style = "display:none" id = "success-save-prof"></div>
      	<form method = "POST" id = "form-save-prof" action = "<c:url value = '/professor/save' />">
      		<input type="hidden" name = "prof_id" id = "prof_id">
      		<div class = "form-group form-inline">
	      	    <label>Nom:</label>
	      		<input class="form-control" type="text" name = "lastname" id = "lastname-prof" required>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label>Prenom:</label>
	      		<input class="form-control" type="text" name = "firstname" id = "firstname-prof">
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      		<label>Email:</label>
		      	<input class="form-control" type="email" name = "email" id = "email-prof" required>
		      	<small class="form-text text-muted error"></small>
		    </div>
	        <div class = "form-group form-inline">
	      		<label>Password:</label>
	      		<input class="form-control" type="password" name = "password" id = "pass-prof" required>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label>Prenom:</label>
	      		<input class="form-control" type="text" name = "contact" id = "contact-prof">
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<input type="hidden" name = "uti_id" id = "uti_id">
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
        <button type="button" class="btn btn-primary" id = "save-professor">Enregistrer</button>
      </div>
    </div>
  </div>
</div>
