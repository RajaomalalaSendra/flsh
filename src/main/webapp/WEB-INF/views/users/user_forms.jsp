<!-- Modal -->
<div class="modal fade" id="userAddModal" tabindex="-1" role="dialog" aria-labelledby="cycleEditLabel" aria-hidden="true" style="z-index: 10010;">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h5 class="modal-title" id="userAddLabel">Ajouter un utilisateur</h5>
      </div>
      <div class="modal-body">
      	<div class = "alert alert-danger" style = "display:none" id = "err-save-user"></div>
		<div class = "alert alert-success" style = "display:none" id = "success-save-user"></div>
      	<form method = "POST" id = "form-save-user" action = "<c:url value = '/user/save' />">
      		<input type="hidden" name = "id" id = "id">
      		<div class = "form-group form-inline">
	      	    <label>Nom:</label>
	      		<input class="form-control" type="text" name = "lastname" id = "lastname" required>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label>Prenom:</label>
	      		<input class="form-control" type="text" name = "firstname" id = "firstname">
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	 <div class = "form-group form-inline">
	      	    <label>Login:</label>
	      		<input class="form-control" type="text" name = "username" id = "name" required>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      		<label>Email:</label>
		      	<input class="form-control" type="email" name = "email" id = "email" required>
		      	<small class="form-text text-muted error"></small>
		    </div>
	        <div class = "form-group form-inline">
	      		<label>Password:</label>
	      		<input class="form-control" type="password" name = "password" id = "pass" required>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      		<label>Type:</label>
	      		<select class="form-control" name="type">
	      	    	<option value="3">Secretaire</option>
	      	    	<option value="1">Super Utilisateur</option>
	      		</select>
	      	</div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
        <button type="button" class="btn btn-primary" id = "save-user">Enregistrer</button>
      </div>
    </div>
  </div>
</div>

<!-- Modal Delete User -->
<div class="modal fade" id="userDeleteModal" tabindex="-1" role="dialog" aria-labelledby="cycleEditLabel" aria-hidden="true" style="z-index: 10010;">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form method = "POST" id = "form-delete-user" action = "<c:url value = '/user/delete' />">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	        <h5 class="modal-title" >Supprimer Utilisateur ?</h5>
	      </div>
	      <div class="modal-body">
		      	<input type="hidden" name = "idUserDelete" id = "idUserDelete" value = "">
		      	<div class = "alert alert-danger" style = "display:none" id = "err-delete-user"></div>
		      	<div class = "alert alert-success" style = "display:none" id = "success-delete-user"></div>
		      	<p id = "question-delete-user">
		      	      	Voulez-vous vraiment supprimer cet utilisateur ? Cette action est irréversible
		      	</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
	        <button type="submit" class="btn btn-danger" id = "delete-user">Supprimer</button>
	      </div>
        </form>
      </div>
  	</div>
  </div>