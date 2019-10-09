<div class="section-etudiant res-mg-t-30 table-mg-t-pro-n tb-sm-res-d-n dk-res-t-d-n">
    <div class="section-title">
		<h2>Detail Etudiant</h2>
    </div>
    <div class="etudiant-desc">
    	<div class='img-etudiant'><img class = "squared-image" src = "${ student.getCroppedImageURL(sc) }"/></div>
    	<div class='info-etudiant'>
	        <span><label class = 'col-md-6'>Nom:</label> <span class = 'col-md-6'> ${ student.getStudent_name() }</span> </span>        	
	        <span><label class = 'col-md-7'>Prenom:</label> <span class = 'col-md-5 col-five-move'> ${ student.getStudent_lastname() }</span></span>
			<span><label class = 'col-md-6'>Date de Naissance:</label><span class = 'col-md-6'> ${ student.getStudent_birthdate()}</span></span>
			<span><label class = 'col-md-7'>Nationalite:</label><span class = 'col-md-5 col-five-move'> ${ student.getStudent_nationality()}</span></span>
			<span><label class = 'col-md-7'>Passeport:</label> <span class = 'col-md-5 col-five-move'>${ student.getStudent_passport()  == null || student.getStudent_passport().equals("") ? "Aucun" : student.getStudent_passport()}</span></span>
			<span><label class = 'col-md-6'>CIN:</label> <span class = 'col-md-6'>${ student.getStudent_cin()}</span></span>
			<span><label class = 'col-md-7'>Lieu CIN:</label> <span class = 'col-md-5 col-five-move'>${ student.getStudent_cinlocation()}</span></span>
			<span><label class = 'col-md-7'>Date CIN: </label><span class = 'col-md-5 col-five-move'>${ student.getStudent_cindate()}</span></span>
			<span><label class = 'col-md-6'>Adress: </label><span class = 'col-md-6'>${ student.getStudent_adress()}</span></span>
			<span><label class = 'col-md-7'>Email: </label><span class = 'col-md-5 col-five-move'>${ student.getStudent_email()}</span></span>
			<span><label class = 'col-md-7'>Etablissement:</label><span class = 'col-md-5 col-five-move'> ${ student.getStudent_lastetab() == null || student.getStudent_lastetab().equals("") ? "Aucun" : student.getStudent_lastetab()}</span></span>
			<span><label class = 'col-md-6'>Conjoint(e): </label><span class = 'col-md-6'>${ student.getStudent_nameconjoint() == null || student.getStudent_nameconjoint().equals("") ? "Aucun" : student.getStudent_nameconjoint()}</span></span>
			<span><label class = 'col-md-7'>Pere: </label><span class = 'col-md-5 col-five-move'>${ student.getStudent_namefather()}</span></span>
			<span><label class = 'col-md-7'>Profession: </label><span class = 'col-md-5 col-five-move'>${ student.getStudent_jobfather()}</span></span>
			<span><label class = 'col-md-6'>Mere: </label><span class = 'col-md-6'>${ student.getStudent_namemother()}</span></span>
			<span><label class = 'col-md-7'>Profession: </label><span class = 'col-md-5 col-five-move'>${ student.getStudent_jobmother()}</span></span>
		</div>
    </div>
</div>
      
                