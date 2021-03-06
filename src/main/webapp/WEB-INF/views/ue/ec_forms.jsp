<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Modal -->
<div class="modal fade" id="ecAddModal" tabindex="-1" role="dialog" aria-labelledby="ecEditLabel" aria-hidden="true" style="z-index: 10010;">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h5 class="modal-title" id="ecAddLabel">Ajouter un Element constitutif</h5>
      </div>
      <div class="modal-body">
      	<div class = "alert alert-danger" style = "display:none" id = "err-save-ec"></div>
		<div class = "alert alert-success" style = "display:none" id = "success-save-ec"></div>
      	<form method = "POST" id = "form-save-ec" action = "<c:url value = '/ec/save' />">
      		<input type="hidden" name = "id" id = "idEC">
      		<input type="hidden" name = "id_ue" id = "idUeEc">
      		<div class = "form-group form-inline">
	      	    <label class="col-md-4">Professor:</label>
	      		<select class="form-control" name="id_prof" style="width: 50%;">
	      		    	<c:forEach items="${ profs }" var="prof" varStatus="status">
							<option value = "${ prof.getProfessor_id() }">${ prof.getCivilite() } ${ prof.getProfessor_name()} ${ prof.getProfessor_last_name()}</option>
						</c:forEach>
	      		</select>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<input type="hidden" id="idProfessorEc">
      		<div class = "form-group form-inline">
	      	    <label class="col-md-4" for="ecLibelleCourt">Libelle court:</label>
	      		<input class="form-control" type="text" name = "libellecourt" id = "ecLibelleCourt" required>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label class="col-md-4" for="ecLibelleLong">Libelle long:</label>
	      		<input class="form-control" type="text" name = "libellelong" id = "ecLibelleLong" required>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label class="col-md-4">Credit:</label>
	      	    <input class="form-control" type="number" name = "credit" min = "0" id = "ecCredit" required>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label class="col-md-4">Notation:</label>
	      		<input class="form-control" type="number" name = "notation" min = "0" id = "ecNotation" required>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label class="col-md-4">Coefficient:</label>
	      		<input class="form-control" type="number" name = "coefficient" min = "0" id = "ecCoefficient" required>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label class="col-md-4">Volume Horaire:</label>
	      		<input class="form-control" type="number" min = "0" step = ".1" name = "horaire" id = "ecHoraire" required>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label class="col-md-4">Travail Presentiel:</label>
	      		<input class="form-control" type="number" min = "0" step = ".1" name = "presenciel" id = "ecPresenciel" required>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label class="col-md-4">Travail Personnel:</label>
	      		<input class="form-control" type="number" min = "0" step = ".1" name = "personnel" id = "ecPersonnel" required>
	      		<small class="form-text text-muted error"></small>
	      	</div>
	      	<div class = "form-group form-inline">
	      	    <label class="col-md-4">Type:</label>
	      		<select class="form-control" name="type" id="ecType">
	      	    	<option value="O">Obligatoire</option>
	      	    	<option value="F">Facultatif</option>
	      	    	<option value="C">Au choix</option>
	      		</select>
	      	</div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
        <button type="button" class="btn btn-primary" id = "save-ec">Enregistrer</button>
      </div>
    </div>
  </div>
</div>

<!-- Modal Delete User -->
<div class="modal fade" id="ecDeleteModal" tabindex="-1" role="dialog" aria-labelledby="ecEditLabel" aria-hidden="true" style="z-index: 10010;">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form method = "POST" id = "form-delete-ec" action = "<c:url value = '/ec/delete' />">
	      <div class="modal-header" style="background-color: #D33;">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	        <h5 class="modal-title" >Supprimer Un Element Constitutif ?</h5>
	      </div>
	      <div class="modal-body">
		      	<input type="hidden" name = "id" id = "idECDelete" value = "">
		      	<div class = "alert alert-danger" style = "display:none" id = "err-delete-ec"></div>
		      	<div class = "alert alert-success" style = "display:none" id = "success-delete-ec"></div>
		      	<p id = "question-delete-ec">
		      	      	Voulez-vous vraiment supprimer cet element constitutif ? Cette action est irréversible
		      	</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
	        <button type="submit" class="btn btn-danger" id = "delete-ec">Supprimer</button>
	      </div>
        </form>
      </div>
  	</div>
  </div>