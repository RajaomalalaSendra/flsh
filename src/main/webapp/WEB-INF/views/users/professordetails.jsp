<!-- Modal See the detail of the user-->
<div class="modal fade" id="profDetailModal" tabindex="-1" role="dialog" aria-labelledby="userDetailLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form method = "GET" >
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	        <h5 class="modal-title" id="profDetailLabel">Détails Professeur</h5>
	      </div>
	      <div class="modal-body">
		      	<div class = "form-group form-inline">
		      		<label>Nom de Professeur</label>
		      		<span id = "detail-lastname-prof"></span>
		      		<br/>
		      		<label>Prenom de Professeur</label>
		      		<span id = "detail-firstname-prof"></span>
		      		<br/>
		      		<label>Email de Professeur</label>
		      		<span id = "detail-email-prof"></span>
		      		<br/>
		      		<label>Contact de Professeur</label>
		      		<span id = "detail-contact-prof"></span>
		      	</div>
		      	
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
	      </div>
      </form>
    </div>
  </div>
</div>