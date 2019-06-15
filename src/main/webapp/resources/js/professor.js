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
		$('#prof_id').val('0')
		$('#profAddModal').modal('show')
	})
	$('#save-prof').on('click', function(){
		if (form_validate('#form-save-prof')){
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
		$('#prof-id').val(idprof)
		$('#prof-for-user-id').val(idprofforuser)
		$.ajax({
			url: getBaseUrl('professor/details?id='+idprof),
			type: "GET",
			dataType: "JSON",
			success: function(data) {
				console.log(data)
				$('#lastname-prof').val(data.professor_last_name)
				$('#firstname-prof').val(data.professor_name)
				$('#loginname-prof').val(data.professor_login)
				$('#email-prof').val(data.professor_email)
				$('#contact-prof').val(data.professor_contact)
				$('#adresse-prof').val(data.professor_adresse)
				$('#uti-type').val("2")
				$('#prof-for-user-id').val(data.uti_id)
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
})