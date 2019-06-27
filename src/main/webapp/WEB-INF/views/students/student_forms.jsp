<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Modal -->
<div class="modal fade" id="editStudentModal" tabindex="-1" role="dialog" aria-labelledby="editStudentLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h5 class="modal-title" id="editStudentLabel">Ajouter un étudiant</h5>
      </div>
      <form method = "POST" id = "form-save-student" action = "<c:url value = '/student/save' />">
      	<div class="modal-body">
      	
      		<ul class="nav nav-tabs">
			  <li class="active"><a data-toggle="tab" href="#primary">Infos primaire</a></li>
			  <li><a data-toggle="tab" href="#secondary">Infos secondaire</a></li>
			  <li><a data-toggle="tab" href="#subscription">Inscription</a></li>
			</ul>
			
	      	<input type="hidden" name = "student_id" id = "idStudent" value = "">
	      	
	      	<div class = "alert alert-danger" style = "display:none" id = "err-save-student"></div>
		    <div class = "alert alert-success" style = "display:none" id = "success-save-student"></div>
		    <div class="tab-content">
		    	<div class = "tab-pane fade in active" id = "primary">
				    <div class = "form-group form-inline">
			        	<label for = "civStudent" class = "col-md-6">Civilité</label>
			        	<select class = "form-control" name = "student_civ" id = "civStudent">
			        		<option value = "1">Mr</option>
			        		<option value = "2">Mlle</option>
			        		<option value = "3">Mme</option>
			        	</select>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "nameStudent" class = "col-md-6">Nom</label>
			        	<input type = "text" class = "form-control" name = "student_name" id = "nameStudent" required />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "lastNameStudent" class = "col-md-6">Prénom</label>
			        	<input type = "text" class = "form-control" name = "student_lastname" id = "lastNameStudent" required />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "birthDateStudent" class = "col-md-6">Date de naissance</label>
			        	<input type = "date" dateformat="d M y" class = "form-control" name = "student_birthdate" id = "birthDateStudent" required />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "nationalityStudent" class = "col-md-6">Nationalité</label>
			        	<input type = "text" class = "form-control" name = "student_nationality" id = "nationalityStudent" />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "passportStudent" class = "col-md-6">N° Passeport</label>
			        	<input type = "text" class = "form-control" name = "student_passport" id = "passportStudent" />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "cinStudent" class = "col-md-6">CIN</label>
			        	<input type = "text" validation-type = "cin" class = "form-control" name = "student_cin" id = "cinStudent" />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "dateCinStudent" class = "col-md-6">Date CIN</label>
			        	<input type = "date" dateformat="d M y" class = "form-control" name = "student_cindate" id = "dateCinStudent" />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "cinLocationStudent" class = "col-md-6">Lieu CIN</label>
			        	<input type = "text" class = "form-control" name = "student_cinlocation" id = "cinLocationStudent" />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "adressStudent" class = "col-md-6">Adresse</label>
			        	<input type = "text" class = "form-control" name = "student_adress" id = "adressStudent" required />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "emailStudent" class = "col-md-6">Email</label>
			        	<input type = "email" class = "form-control" name = "student_email" id = "emailStudent" required />
			        	<small class="form-text text-muted error"></small>
			        </div>
	        	</div>
	        	<div class = "tab-pane fade in" id = "secondary">
			        <div class = "form-group form-inline">
			        	<label for = "lastEtabStudent" class = "col-md-6">Dernier établissement</label>
			        	<input type = "text" class = "form-control" name = "student_lastetab" id = "lastEtabStudent" />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "ConjointNameStudent" class = "col-md-6">Nom du conjoint</label>
			        	<input type = "text" class = "form-control" name = "student_nameconjoint" id = "ConjointStudent" />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "fatherNameStudent" class = "col-md-6">Nom du père</label>
			        	<input type = "text" class = "form-control" name = "student_namefather" id = "fatherNameStudent" required />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "fatherJobStudent" class = "col-md-6">Profession du père</label>
			        	<input type = "text" class = "form-control" name = "student_jobfather" id = "fatherJobStudent" />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "MotherNameStudent" class = "col-md-6">Nom de la mère</label>
			        	<input type = "text" class = "form-control" name = "student_namemother" id = "motherNameStudent" required />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "motherJobStudent" class = "col-md-6">Profession de la mère</label>
			        	<input type = "text" class = "form-control" name = "student_jobmother" id = "motherJobStudent" />
			        	<small class="form-text text-muted error"></small>
			        </div>
			   </div>
			   <div class = "tab-pane fade in" id = "subscription">
			   		<div class = "form-group form-inline">
			        	<label for = "subsUY" class = "col-md-6">Année scolaire</label>
			        	<select class = "form-control" id = "subsUY" name = "subs_iduy">
			        		<c:forEach items = "${ univYears }" var = "uy" varStatus = "status">
			        			<option value = "${ uy.getUniversity_year_id() }">${ uy.getUniversity_year_libelle() }</option>
			        		</c:forEach>
			        	</select>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "subsLevel" class = "col-md-6">Niveau</label>
			        	<select class = "form-control" id = "subsLevel" name = "subs_level">
			        		<c:forEach items = "${ levels }" var = "level" varStatus = "status">
			        			<option value = "${ level.getLevelId() }">${ level.getLevelLibelle() }</option>
			        		</c:forEach>
			        	</select>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "subsParcours" class = "col-md-6">Parcours</label>
			        	<select class = "form-control" id = "subsParcours" name = "subs_parcours">
			        	</select>
			        </div>
			        <div class = "form-group form-inline">
			        	<label for = "subsIsPaid" class = "col-md-6">Frais d'inscription</label>
			        	<input type = "checkbox" name = "subs_inscription" id = "subsIsPaid" />
			        </div>
			        <div id = "ue-choix-wrapper">
			        	
			        </div>
			   </div>
			</div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
	        <button type="submit" class="btn btn-primary" id = "save-student">Enregistrer</button>
	      </div>
       </form>
    </div>
  </div>
</div>

<!-- Modal delete -->
<div class="modal fade" id="studentDeleteModal" tabindex="-1" role="dialog" aria-labelledby="studentDeleteLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form method = "POST" id = "form-delete-student" action = "<c:url value = '/education/saveCycle' />">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	        <h5 class="modal-title" id="cycleDeleteLabel">Supprimer étudiant ?</h5>
	      </div>
	      <div class="modal-body">
		      	<input type="hidden" name = "student_id" id = "idStudentDelete" value = "">
		      	<div class = "alert alert-danger" style = "display:none" id = "err-delete-student"></div>
		      	<div class = "alert alert-success" style = "display:none" id = "success-delete-student"></div>
		      	<p id = "question-delete">
		      	La suppression du cycle engendrera la suppression des inscriptions et tous les évaluations.<br/>
		      	Voulez-vous vraiment supprimer ? Cette action est irréversible
		      	</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
	        <button type="submit" class="btn btn-primary" id = "delete-student">Supprimer</button>
	      </div>
      </form>
    </div>
  </div>
</div>