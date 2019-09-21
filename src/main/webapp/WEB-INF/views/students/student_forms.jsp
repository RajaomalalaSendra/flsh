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
			  <li><a data-toggle="tab" href="#photo">Photo</a></li>
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
			        	<label for = "dateSubsStudent" class = "col-md-6">Date d'inscription</label>
			        	<input type = "date" class = "form-control" name = "subs_date" id = "dateSubsStudent" />
			        	<small class="form-text text-muted error"></small>
			        </div>
			        <div class = "from-group form-inline">
			        	<label for = "subsIsPaid" class = "col-md-6">Frais d'inscription</label>
			        	<div class="checkbox checkbox-primary">
	                        <input  type = "checkbox" class = "styled" name = "subs_inscription" id = "subsIsPaid">
	                        <label></label>
	                    </div>
			        </div>
			        <div id = "ue-choix-wrapper">
			        	
			        </div>
			   </div>
			   <div class = "tab-pane fade in" id = "photo">
				   		<div id = "photo-upload-container" style = "position: relative; padding-left:25%; width: 100%; height: 300px;" >
			         		 <img id="image-student" style = "max-width: 100%" src="<c:url value = "/resources/img/student/4.jpg" />" alt="Picture" class="cropper-hidden">
			         	</div>
				   	<div id = "cropper-control">
				   		<div class="btn-group">
				          <button id = "profile-zoom-in" type="button" class="btn btn-sm btn-primary" data-method="zoom" data-option="0.1" title="Zoom In">
				            <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="cropper.zoom(0.1)">
				              <span class="fa fa-search-plus"></span>
				            </span>
				          </button>
				          <button id = "profile-zoom-out" type="button" class="btn  btn-sm btn-primary" data-method="zoom" data-option="-0.1" title="Zoom Out">
				            <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="cropper.zoom(-0.1)">
				              <span class="fa fa-search-minus"></span>
				            </span>
				          </button>
				        </div>
				   		<div class="btn-group">
				          <button id = "profile-rotate-left" type="button" class="btn btn-sm btn-primary" data-method="rotate" data-option="-45" title="Rotate Left">
				            <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="cropper.rotate(-45)">
				              <span class="fa fa-undo-alt"></span>
				            </span>
				          </button>
				          <button id = "profile-rotate-right" type="button" class="btn btn-sm btn-primary" data-method="rotate" data-option="45" title="Rotate Right">
				            <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="cropper.rotate(45)">
				              <span class="fa fa-redo-alt"></span>
				            </span>
				          </button>
				        </div>
				        <div class="file-field btn-group">
						    <div class="btn btn-primary btn-sm float-left" title = "Change picture">
						      <span class = "fa fa-upload"></span>
						      <input type="file">
						    </div>
						</div>
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
      <form method = "POST" id = "form-delete-student" action = "<c:url value = '/student/delete' />">
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
		      	La suppression de l'étudiant engendrera la suppression des inscriptions et tous les évaluations.<br/>
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

<!-- Modal subscription -->
<div class="modal fade" id="studentSubscribeModal" tabindex="-1" role="dialog" aria-labelledby="studenSubscribeLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form method = "POST" id = "form-subscribe-student" action = "<c:url value = '/student/subscribe' />">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	        <h5 class="modal-title" id="studenSubscribeLabel">Inscrire <span id = "student-name"></span></h5>
	      </div>
	      <div class="modal-body">
	      	<div class = "alert alert-danger" style = "display:none" id = "err-subscribe-student"></div>
		    <div class = "alert alert-success" style = "display:none" id = "success-subscribe-student"></div>
		    <input type = "hidden" id = "id-student-subscribe" name = "subs_idstudent" />
		    <div class = "form-group form-inline">
	        	<label for = "studentSubsLevel" class = "col-md-6">Niveau</label>
	        	<select class = "form-control" id = "studentSubsLevel" name = "subs_level" required>
	        		<c:forEach items = "${ levels }" var = "level" varStatus = "status">
	        			<option value = "${ level.getLevelId() }">${ level.getLevelLibelle() }</option>
	        		</c:forEach>
	        	</select>
	        </div>
	        <div class = "form-group form-inline">
	        	<label for = "studentSubsParcours" class = "col-md-6">Parcours</label>
	        	<select class = "form-control" id = "studentSubsParcours" name = "subs_parcours" required>
	        	</select>
	        </div>
	        <div class = "form-group form-inline">
	        	<label for = "dateStudentSubs" class = "col-md-6">Date d'inscription</label>
	        	<input type = "date" class = "form-control" name = "subs_date" id = "dateStudentSubs" required/>
	        	<small class="form-text text-muted error"></small>
	        </div>
	        <div class = "from-group form-inline">
	        	<label for = "studentSubsIsPaid" class = "col-md-6">Frais d'inscription</label>
	        	<div class="checkbox checkbox-primary">
                       <input  type = "checkbox" class = "styled" name = "subs_inscription" id = "studentSubsIsPaid">
                       <label></label>
                   </div>
	        </div>
	        <div id = "subscribe-ue-choix-wrapper">
	        	
	        </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
	        <button type="submit" class="btn btn-primary" id = "subscribe-student">Inscrire</button>
	      </div>
      </form>
    </div>
  </div>
</div>

<!-- Modal unsubscribe -->
<div class="modal fade" id="removeSubscriptionStudentModal" tabindex="-1" role="dialog" aria-labelledby="removeSubscriptionStudentLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form method = "POST" id = "form-unsubscribe-student" action = "<c:url value = '/student/saveSubscription' />">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	        <h5 class="modal-title" id="cycleDeleteLabel">Désinscrire l'étudiant ?</h5>
	      </div>
	      <div class="modal-body">
		      	<input type="hidden" name = "student_id" id = "idStudentUnsubscribe" value = "">
		      	<div class = "alert alert-danger" style = "display:none" id = "err-unsubscribe-student"></div>
		      	<div class = "alert alert-success" style = "display:none" id = "success-unsubscribe-student"></div>
		      	<p id = "question-delete">
		      	La suppression de l'inscription entraine la suppression des évaluations par rapport à cette année scolaire.<br/>
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