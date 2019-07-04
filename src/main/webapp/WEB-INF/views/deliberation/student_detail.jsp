<div class="courses-inner res-mg-t-30 table-mg-t-pro-n tb-sm-res-d-n dk-res-t-d-n">
    <div class="courses-title">
		<h2>Detail Etudiant</h2>
    </div>
    <div class="course-des">
        <p><b>Nom:</b> ${ student.getStudent_name() }</p>        	
        <p><b>Prenom:</b> ${ student.getStudent_lastname() } </p>
		<p><b>Date de Naissance:</b> ${ student.getStudent_birthdate()}</p>
		<p><b>Nationalite:</b> ${ student.getStudent_nationality()}</p>
		<p><b>Passeport:</b> ${ student.getStudent_passport()}</p>
		<p><b>CIN:</b> ${ student.getStudent_cin()}</p>
		<p><b>Lieu CIN:</b> ${ student.getStudent_cinlocation()}</p>
		<p><b>Date CIN: </b>${ student.getStudent_cindate()}</p>
		<p><b>Adress: </b>${ student.getStudent_adress()}</p>
		<p><b>Email: </b>${ student.getStudent_email()}</p>
		<p><b>Etablissement:</b> ${ student.getStudent_lastetab()}</p>
		<p><b>Conjoint(e): </b>${ student.getStudent_nameconjoint()}</p>
		<p><b>Pere: </b>${ student.getStudent_namefather()}</p>
		<p><b>Profession: </b>${ student.getStudent_jobfather()}</p>
		<p><b>Mere: </b>${ student.getStudent_namemother()}</p>
		<p><b>Profession: </b>${ student.getStudent_jobmother()}</p>
    </div>
</div>
      
                