<!-- Modal -->
<div class="modal fade" id="userAddModal" tabindex="-1" role="dialog" aria-labelledby="cycleEditLabel" aria-hidden="true">
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
