$(document).ready(function() {
	$('.detail-user').on('click', function(event) {
		console.log("hereeeeeeeeeeee")
		event.preventDefault()
		var url = getBaseUrl($(this).attr('href'))
		$.ajax({
			url: url,
			type: "GET",
			dataType: "JSON",
			success: function(data) {
				console.log(data)
				$('#detail-username').html(data.username)
				$('#detail-usermail').html(data.email)
				$('#detail-user-role').html(data.typecomputed)
				$('#userDetailModal').modal('show')
			},
			error: function(err) {
				console.log(err)
			}
		})
	})
	$('#add-user').on('click', function() {
		/*$('#userAddModal').html("Ajouter Utilisateur")
		$('#type').val("cycle")
		$('#idCycle').val("")
		$('#idLevel').val("")
		$('#idParcours').val("")
		$('#labelCycle').val("")*/
		$('#userAddModal').modal('show')
	})
	$('#save-user').on('click', function(){
		if (form_validate('#form-save-user')){
			$.ajax({
				url: getBaseUrl('user/save'),
			    type: 'POST',
			    data: $('#form-save-user').serialize(),
			    dataType : 'JSON',
			    success: function(data){
			    	if(data.status == 1) {
						window.location.reload()
					} else {
						$('#err-save-user').html(data.message ? data.message : 'Une erreur interne s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
					}
			    },
			    error: function(err){
			    	$('#err-save-cycle').html('Une erreur s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
			    }
			})
		}
	})
})