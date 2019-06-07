<!-- Modal See the detail of the user-->
<div class="modal fade" id="userDetailModal" tabindex="-1" role="dialog" aria-labelledby="userDetailLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form method = "GET" >
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	        <h5 class="modal-title" id="userDetailLabel">Détails utilisateur</h5>
	      </div>
	      <div class="modal-body">
		      	<div class = "form-group form-inline">
		      		<label>Nom d'utilisateur</label>
		      		<span id = "detail-username"></span>
		      		<br/>
		      		<label>Email de l'utilisateur</label>
		      		<span id = "detail-usermail"></span>
		      	    <br/>
		      		<label>Role</label>
		      		<span id = "detail-user-role"></span>
		      	</div>
		      	
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
	      </div>
      </form>
    </div>
  </div>
</div>