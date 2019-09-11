<!-- Modal -->
<div class="modal fade" id="univYearModal" tabindex="-1" role="dialog" aria-labelledby="univYearLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h5 class="modal-title" id="univYearLabel">Ajouter un period</h5>
      </div>
      <div class="modal-body">
      	<form method = "POST" id = "form-save-ua" action = "<c:url value = '/education/saveAU' />">
      	
      		<input type="hidden" name = "type" id = "type" value = "period">
	      	<input type="hidden" name = "university_year_id" id = "idAU" value = "">
	      	
	      	<div class = "alert alert-danger" style = "display:none" id = "err-save-au"></div>
		    <div class = "alert alert-success" style = "display:none" id = "success-save-au"></div>
	        <div class = "form-group form-inline">
	        	<label for = "labelUA" class = "col-md-6">Libellé</label>
	        	<input type = "text" class = "form-control" name = "university_year_libelle" id = "labelUA" required />
	        	<small class="form-text text-muted error"></small>
	        </div>
	        <div class = "form-group form-inline">
	        	<label for = "debUA" class = "col-md-6">Début</label>
	        	<input type = "date" class = "form-control" name = "university_year_beginning" id = "debUA" required />
	        	<small class="form-text text-muted error"></small>
	        </div>
	        <div class = "form-group form-inline">
	        	<label for = "finUA" class = "col-md-6">Fin</label>
	        	<input type = "date" class = "form-control" name = "university_year_ending" id = "finUA" required />
	        	<small class="form-text text-muted error"></small>
	        </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
        <button type="button" class="btn btn-primary" id = "save-au">Enregistrer</button>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="periodDeleteModal" tabindex="-1" role="dialog" aria-labelledby="periodDeleteLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form method = "POST" id = "form-delete-period" action = "<c:url value = '/education/deletePeriod' />">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	        <h5 class="modal-title" id="cycleDeleteLabel">Supprimer année universitaire ?</h5>
	      </div>
	      <div class="modal-body">
	      		<input type="hidden" name = "typeDeletionPeriod" id = "typeDeletionPeriod" value = "">
		      	<input type="hidden" name = "idItemDeletePeriod" id = "idItemDeletePeriod" value = "">
		      	<div class = "alert alert-danger" style = "display:none" id = "err-delete-period"></div>
		      	<div class = "alert alert-success" style = "display:none" id = "success-delete-period"></div>
		      	<p id = "question-delete">
		      	La suppression de l'année-universitaire entrainera la suppression des periodes et des examens!<br>
		      	Voulez-vous continuer ? Cette action est irréversible
		      	</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
	        <button type="submit" class="btn btn-primary" id = "delete-period">Supprimer</button>
	      </div>
      </form>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="periodEditModal" tabindex="-1" role="dialog" aria-labelledby="periodEditLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form method = "POST" id = "form-edit-period" action = "<c:url value = '/education/saveLevelPeriod' />">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	        <h5 class="modal-title" id="periodEditLabel">Ajouter une période</h5>
	      </div>
	      <div class="modal-body">
	      	<ul class="nav nav-tabs">
			  <li class="active"><a data-toggle="tab" href="#period-tab">Période</a></li>
			  <li><a data-toggle="tab" href="#exam-tab">Examen</a></li>
			</ul>
			<input type="hidden" name = "period_id" id = "period-id" value = "0">
	      	<div class = "alert alert-danger" style = "display:none" id = "err-save-levelperiod"></div>
	      	<div class = "alert alert-success" style = "display:none" id = "success-save-levelperiod"></div>
			<div class="tab-content">
			  <div id="period-tab" class="tab-pane fade in active">
			    <div class = "form-group form-inline">
		        	<label for = "labelPeriod" class = "col-md-6">Libellé court</label>
		        	<input type = "text" class = "form-control" name = "period_libellecourt" id = "labePeriod" required />
		        	<small class="form-text text-muted error"></small>
		        </div>
		        <div class = "form-group form-inline">
		        	<label for = "labelPeriod2" class = "col-md-6">Libellé long</label>
		        	<input type = "text" class = "form-control" name = "period_libellelong" id = "labePeriod2" required />
		        	<small class="form-text text-muted error"></small>
		        </div>
		        <div class = "form-group form-inline">
		        	<label for = "debPeriod" class = "col-md-6">Début</label>
		        	<input type = "date" class = "form-control" name = "period_debut" id = "debPeriod" required />
		        	<small class="form-text text-muted error"></small>
		        </div>
		        <div class = "form-group form-inline">
		        	<label for = "finPeriod" class = "col-md-6">Fin</label>
		        	<input type = "date" class = "form-control" name = "period_fin" id = "finPeriod" required />
		        	<small class="form-text text-muted error"></small>
		        </div>
		        <div class = "form-group form-inline">
		        	<label for = "withRatrappage" class = "col-md-6">Avec ratrappage</label>
		        	<input type = "checkbox" class = "form-control" name = "has_rattr" id = "withRatrappage" />
		        </div>
			  </div>
			  
			  <div id="exam-tab" class="tab-pane fade">
			  	<div id = "exam-container">
			  		<h4>Examen</h4>
			  		<div class = "form-group form-inline">
			        	<label for = "labelExam" class = "col-md-6">Libellé</label>
			        	<input type = "text" class = "form-control" name = "exam_libelle" id = "labelExam" required />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "debExam" class = "col-md-6">Début</label>
			        	<input type = "date" class = "form-control" name = "exam_debut" id = "debExam" required />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "finExam" class = "col-md-6">Fin</label>
			        	<input type = "date" class = "form-control" name = "exam_fin" id = "finExam" required />
			        	<small class="form-text text-muted error"></small>
			        </div>
			  	</div>
			    <div id = "rattr-container" style = "display:none;">
			    	<h4>Rattrapage</h4>
			    	<div class = "form-group form-inline">
			        	<label for = "labelRattr" class = "col-md-6">Libellé</label>
			        	<input type = "text" class = "form-control" name = "rattr_libelle" id = "labelRattr"  />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "debRattr" class = "col-md-6">Début</label>
			        	<input type = "date" class = "form-control" name = "rattr_debut" id = "debRattr"  />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "finRattr" class = "col-md-6">Fin</label>
			        	<input type = "date" class = "form-control" name = "rattr_fin" id = "finRattr"  />
			        	<small class="form-text text-muted error"></small>
			        </div>
			    </div>
			  </div>
			</div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
	        <button type="submit" class="btn btn-primary" id = "save-levelperiod">Enregistrer</button>
	      </div>
      </form>
    </div>
  </div>
</div>