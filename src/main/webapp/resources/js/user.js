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
				$('#user-civilite-user').val(data.civilite)
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
	
	$('#edit-myaccount').on('click', function(e) {
		e.preventDefault()
		$('#form-my-account input:not(#pass-account)').removeAttr('disabled');
		$('.edit-account').show();
		$(this).hide();
	})
	
	$('#cancel-editaccount').on('click', function(e) {
		e.preventDefault()
		$('#form-my-account input').attr('disabled','');
		$('.edit-account').hide();
		$('#edit-myaccount').show();
	})
	
//	$('#show-password').on('click', function() {
//		if($(this).hasClass('glyphicon-eye-close')) {
//			$(this).parent().find('input').attr('type', 'text');
//			$(this).removeClass('glyphicon-eye-close').addClass('glyphicon-eye-open');
//		} else {
//			$(this).parent().find('input').attr('type', 'password');
//			$(this).addClass('glyphicon-eye-close').removeClass('glyphicon-eye-open');
//		}
//	})
	
	$('#newpass-account').on('keyup', function() {
		if($(this).val() == "") {
			$('#pass-account').attr('disabled','')
		} else {
			$('#pass-account').removeAttr('disabled')
		}
	})
	
	$('#form-my-account').on('submit', function(e) {
		e.preventDefault();
		if(formValidate('#form-my-account')) {
			var proceed = true;
			if($('#newpass-account').val() != "" && $('#newpass-account').val() != $('#confirmpass-account').val()) {
				proceed = false;
				$('#err-save-account').html('Le mot de passe de confirmation ne correspond pas au nouveau mot de passe!!').show().delay(4000).fadeOut(600)
			} 
			if(proceed) {
				$('#type-id').removeAttr('disabled');
				$.ajax({
					url: getBaseUrl('saveMyAccount'),
					type: 'POST',
					data: $('#form-my-account').serialize(),
					dataType: 'JSON',
					success: function(data) {
						if(data.status == 1) {
							$('#success-save-account').html(data.message).show().delay(2000).fadeOut(600)
							$('#form-my-account input').attr('disabled','');
							$('.edit-account').hide();
							$('#edit-myaccount').show();
						} else {
							$('#err-save-account').html(data.message ? data.message : 'Echec de l\'enregistrement des modifications! Veuillez réessayer').show().delay(4000).fadeOut(600)
						}
						$('#type-id').attr('disabled', '');
					},
					error: function() {
						$('#type-id').attr('disabled', '');
						$('#err-save-account').html('Une erreur s\'est produite! Veuillez réessayer').show().delay(4000).fadeOut(600)
					}
				})
			}
		}
	})
})