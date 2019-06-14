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
				$('#detail-lastname-prof').html(data.lastname)
				$('#detail-firstname-prof').html(data.firstname)
				$('#detail-email-prof').html(data.email)
				$('#detail-contact-prof').html(data.contact)
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
			$.ajax({
				url: getBaseUrl('professor/save'),
			    type: 'POST',
			    data: $('#form-save-prof').serialize(),
			    dataType : 'JSON',
			    success: function(data){
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
		$('#profAddLabel').html('Editer un utilisateur')
		$('#prof_id').val(idprof)
		$.ajax({
			url: getBaseUrl('professor/details?id='+idprof),
			type: "GET",
			dataType: "JSON",
			success: function(data) {
				console.log(data)
				$('#lastname-prof').val(data.lastname)
				$('#firstname-prof').val(data.firstname)
				$('#email-prof').val(data.email)
				$('#contact-prof').val(data.email)
				$("#profAddModal").modal('show')
			},
			error: function(err) {
				console.log(err)
			}
		})
	})
})