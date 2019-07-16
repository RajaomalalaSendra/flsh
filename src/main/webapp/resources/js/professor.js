$(document).ready(function() {
	$('.detail-professor').on('click', function(event) {
		event.preventDefault()
		var url = getBaseUrl($(this).attr('href'))
		$.ajax({
			url: url,
			type: "GET",
			dataType: "JSON",
			success: function(data) {
				console.log(data)
				$('#detail-lastname-prof').html(data.professor_last_name)
				$('#detail-firstname-prof').html(data.professor_name)
				$('#detail-email-prof').html(data.professor_email)
				$('#detail-contact-prof').html(data.professor_contact)
				$('#detail-adresse-prof').html(data.professor_adresse)
				$('#profDetailModal').modal('show')
			},
			error: function(err) {
				console.log(err)
			}
		})
	})
	$('#add-professor').on('click', function() {
		$('#profAddLabel').html('Ajouter un professeur')
		emptyForm("#profAddModal")
		$('#prof-id').val('0')
		$('#uti-type').val("2")
		$('#prof-for-user-id').val('0')
		$('#profAddModal').modal('show')
	})
	$('#save-prof').on('click', function(){
		updatePasswordStatus()
		if (formValidate('#form-save-prof')){
			console.log("test.....")
			$.ajax({
				url: getBaseUrl('professor/save'),
			    type: 'POST',
			    data: $('#form-save-prof').serialize(),
			    dataType : 'JSON',
			    success: function(data){
			    	console.log("test inside.....")
			    	console.log(data)
			    	if(data.status == 1) {
						window.location.reload()
					} else {
						$('#err-save-prof').html(data.message ? data.message : 'Une erreur interne s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
					}
			    },
			    error: function(err){
			    	$('#err-save-prof').html('Une erreur s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
			    }
			})
		}
	})
	
	$('.edit-professor').on('click', function(){
		console.log('test edit prof')
		var idprof = $(this).attr('id-prof')
		var idprofforuser = $(this).attr('id-prof-for-user')
		$('#profAddLabel').html('Editer un Professeur')
		$.ajax({
			url: getBaseUrl('professor/details?id='+idprof),
			type: "GET",
			dataType: "JSON",
			success: function(data) {
				console.log(data)
				$('#prof-id').val(data.professor_id)
				$('#lastname-prof').val(data.professor_last_name)
				$('#firstname-prof').val(data.professor_name)
				$('#loginname-prof').val(data.professor_login)
				$('#email-prof').val(data.professor_email)
				$('#pass-prof').val("")
				$('#contact-prof').val(data.professor_contact)
				$('#adresse-prof').val(data.professor_adresse)
				$('#uti-type').val("2")
				$('#prof-for-user-id').val(data.user_id)
				$('#user-civilite-prof').val(data.user_civilite)
				$("#profAddModal").modal('show')
			},
			error: function(err) {
				console.log(err)
			}
		})
	})
	$('.delete-professor').on('click', function() {
		var idUserProf = $(this).attr('id-profd')
		var idUserProfProf = $(this).attr('id-profdelete')
		$('#idProfDelete').val(idUserProf)
		$('#userIdDelete').val(idUserProfProf)
		$('#profDeleteModal').modal('show')
	})
	$('#form-delete-prof').on('submit', function(e) {
		console.log(getBaseUrl('professor/delete?id='+$('#idProfDelete').val()))
		console.log(e)
		e.preventDefault()
		$.ajax({
			url: getBaseUrl('professor/delete?id='+$('#idProfDelete').val()),
			type: 'GET',
			dataType: 'JSON',
			success: function(data) {
				console.log("OKOKOKOKOKO")
				if(data.status == 1) {
					$("#prof-"+$('#idProfDelete').val()).remove()
					$('#success-delete-prof').html('Suppression avec success...').show().delay(3000).fadeOut(600)
					$('#profDeleteModal').modal('hide')
				} else {
					$('#err-delete-prof').html('Une erreur interne s\'est produite! Veuillez ressayer...').show().delay(3000).fadeOut(600)
				}
			},
			error: function(err) {
				$('#err-delete-prof').html('Une erreur s\'est produite! Veuillez ressayer...').show().delay(3000).fadeOut(600)
			}
		})
	})
	
	// edit and add new professor for the study unit
	$('#add-professor-ue').on('click', function() {
		$('#ueProfAddLabel').html('Ajouter un professeur pour un unite d\'enseignement')
		$('#prof-ue-id').val('0')
		$('#').val("2")
		$('#prof-for-user-id').val('0')
		$('#ueProfAddModal').modal('show')
	})
	
	$('#save-prof-ue').on('click', function(){
		if (formValidate('#form-save-prof-ue')){
			console.log("test.....")
			$.ajax({
				url: getBaseUrl('professor/course/save'),
			    type: 'POST',
			    data: $('#form-save-prof-ue').serialize(),
			    dataType : 'JSON',
			    success: function(data){
			    	console.log("test inside.....")
			    	console.log(data)
			    	if(data.status == 1) {
						window.location.reload()
					} else {
						$('#err-save-prof-ue').html(data.message ? data.message : 'Une erreur interne s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
					}
			    },
			    error: function(err){
			    	$('#err-save-prof-ue').html('Une erreur s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
			    }
			})
		}
	})
})

function updatePasswordStatus() {
	if($('#prof_id').val() == 0) {
		$('#pass-prof').attr("required")
	} else {
		if($('#pass-prof').val() == "") {
			$('#pass-prof').removeAttr("required")
		} else {
			$('#pass-prof').attr("required")
		}
	}
}