$(document).ready(function() {
	$('.detail-user').on('click', function(event) {
		event.preventDefault()
		var url = getBaseUrl($(this).attr('href'))
		$.ajax({
			url: url,
			type: "GET",
			dataType: "JSON",
			success: function(data) {
				console.log(data)
				$('#detail-lastname').html(data.lastname)
				$('#detail-firstname').html(data.firstname)
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
		$('#userAddLabel').html('Ajouter un utilisateur')
		emptyForm("#userAddModal")
		$('#id').val('0')
		$('#userAddModal').modal('show')
	})
	$('#save-user').on('click', function(){
		if (formValidate('#form-save-user')){
			$.ajax({
				url: getBaseUrl('user/save'),
			    type: 'POST',
			    data: $('#form-save-user').serialize(),
			    dataType : 'JSON',
			    success: function(data){
			    	if(data.status == 1) {
			    		$('#success-save-user').html(data.message ? data.message : 'Enregistre avec succes.').show().delay(3000).fadeOut(600)
						window.location.reload()
					} else {
						$('#err-save-user').html(data.message ? data.message : 'Une erreur interne s\'est produite! Veuillez reessayer...').show().delay(3000).fadeOut(600)
					}
			    },
			    error: function(err){
			    	$('#err-save-user').html('Une erreur s\'est produite! Veuillez reessayer...').show().delay(3000).fadeOut(600)
			    }
			})
		}
	})
	
	$('.edit-user').on('click', function(){
		console.log('test edit user')
		var iduser = $(this).attr('id-user')
		$('#userAddLabel').html('Editer un utilisateur')
		$('#id').val(iduser)
		$.ajax({
			url: getBaseUrl('user/details?id='+iduser),
			type: "GET",
			dataType: "JSON",
			success: function(data) {
				console.log(data)
				$('#lastname').val(data.lastname)
				$('#firstname').val(data.firstname)
				$('#name').val(data.username)
				$('#email').val(data.email)
				$('#pass').val("")
				$('#type-id').val(data.type)
				$("#userAddModal").modal('show')
			},
			error: function(err) {
				console.log(err)
			}
		})
	})
	$('.delete-user').on('click', function() {
		var idUser = $(this).attr('id-user-delete')
		$('#idUserDelete').val(idUser)
		$('#userDeleteModal').modal('show')
	})
	$('#form-delete-user').on('submit', function(e) {
		e.preventDefault()
		$.ajax({
			url: getBaseUrl('user/delete?id='+$('#idUserDelete').val()),
			type: 'GET',
			dataType: 'JSON',
			success: function(data) {
				if(data.status == 1) {
					$("#user-"+$('#idUserDelete').val()).remove()
					$('#success-delete-user').html('Suppression avec success...').show().delay(3000).fadeOut(600)
					$('#userDeleteModal').modal('hide')
				} else {
					$('#err-delete-user').html('Une erreur interne s\'est produite! Veuillez ressayer...').show().delay(3000).fadeOut(600)
				}
			},
			error: function(err) {
				$('#err-delete-user').html('Une erreur s\'est produite! Veuillez ressayer...').show().delay(3000).fadeOut(600)
			}
		})
	})
})