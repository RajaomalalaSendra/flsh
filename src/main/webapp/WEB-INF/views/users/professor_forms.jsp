<!-- Modal Add new Professor-->
<div class="modal fade" id="profAddModal" tabindex="-1" role="dialog" aria-labelledby="profEditLabel" aria-hidden="true" style="z-index: 10010;">
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
      		<input type="hidden" name = "professor_id" id = "prof-id">
      		<input type="hidden" name = "user_id" id = "prof-for-user-id">
      		<div class = "form-group form-inline">
	      	    <label class="col-sm-3">Nom:</label>
	      		<input class="form-control" type="text" name = "professor_last_name" id = "lastname-prof" required>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label class="col-sm-3">Prenom:</label>
	      		<input class="form-control" type="text" name = "professor_name" id = "firstname-prof">
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label class="col-sm-3">Login:</label>
	      		<input class="form-control" type="text" name = "professor_login" id = "loginname-prof">
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      		<label class="col-sm-3">Email:</label>
		      	<input class="form-control" type="email" name = "professor_email" id = "email-prof" required>
		      	<small class="form-text text-muted error"></small>
		    </div>
	        <div class = "form-group form-inline">
	      		<label class="col-sm-3">Password:</label>
	      		<input class="form-control" type="password" name = "professor_password" id = "pass-prof">
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label class="col-sm-3">Contact:</label>
	      		<input class="form-control" type="text" name = "professor_contact" id = "contact-prof">
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label class="col-sm-3">Adresse:</label>
	      		<input class="form-control" type="text" name = "professor_adresse" id = "adresse-prof">
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<input type="hidden" name = "user_type" id = "uti-type" value = "2">
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
        <button type="button" class="btn btn-primary" id = "save-prof">Enregistrer</button>
      </div>
    </div>
  </div>
</div>
<!-- Modal Delete Professor -->
<div class="modal fade" id="profDeleteModal" tabindex="-1" role="dialog" aria-labelledby="profDeleteLabel" aria-hidden="true" style="z-index: 10010;">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form method = "POST" id = "form-delete-prof" action = "<c:url value = '/professor/delete' />">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	        <h5 class="modal-title" >Supprimer Professeur ?</h5>
	      </div>
	      <div class="modal-body">
		      	<input type="hidden" name = "idProfDelete" id = "idProfDelete" value = "">
		      	<input type="hidden" name = "user_id_delete" id = "userIdDelete" value = "">
		      	<div class = "alert alert-danger" style = "display:none" id = "err-delete-prof"></div>
		      	<div class = "alert alert-success" style = "display:none" id = "success-delete-prof"></div>
		      	<p id = "question-delete-prof">
		      	      	Voulez-vous vraiment supprimer ce professeur ? Cette action est irréversible.
		      	</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
	        <button type="submit" class="btn btn-danger" id = "delete-prof">Supprimer</button>
	      </div>
        </form>
      </div>
  	</div>
  </div>