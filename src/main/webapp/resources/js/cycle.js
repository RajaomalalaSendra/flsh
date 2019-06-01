$(document).ready(function() {
	
	$(".edit-cycle").on("click", function() {
		var idCycle = $(this).parent().parent().parent().parent().attr("id-cycle")
		$('#cycleEditLabel').html("Editer cycle")
		$('#type').val("cycle")
		$('#idLevel').val("")
		$('#idParcours').val("")
		$('#idCycle').val(idCycle);
		$('#labelCycle').val($.trim($('#labelCycle-'+idCycle).html()))
		$('#cycleEditModal').modal('show')
	})
	
	$('#add-cycle').on('click', function() {
		$('#cycleEditLabel').html("Ajouter cycle")
		$('#type').val("cycle")
		$('#idCycle').val("")
		$('#idLevel').val("")
		$('#idParcours').val("")
		$('#labelCycle').val("")
		$('#cycleEditModal').modal('show')
	})
	
	$('.add-level').on('click', function() {
		var idCycle = $(this).parent().parent().parent().parent().attr('id-cycle')
		$('#cycleEditLabel').html("Ajouter niveau")
		$('#type').val("level")
		$('#idCycle').val(idCycle)
		$('#idLevel').val("")
		$('#idParcours').val("")
		$('#labelCycle').val("")
		$('#cycleEditModal').modal('show')
	})
	
	$('.add-parcours').on('click', function() {
		var idLevel = $(this).parent().parent().parent().attr('id-level')
		$('#cycleEditLabel').html("Ajouter parcours")
		$('#type').val("parcours")
		$('#idCycle').val("")
		$('#idLevel').val(idLevel)
		$('#idParcours').val("")
		$('#labelCycle').val("")
		$('#cycleEditModal').modal('show')
	})
	
	$('.edit-level').on('click', function() {
		var idCycle = $(this).parent().parent().parent().parent().parent().parent().attr('id-cycle')
		var idLevel = $(this).parent().parent().parent().attr('id-level')
		console.log('level '+idLevel+' | cycle '+idCycle)
		$('#cycleEditLabel').html("Editer niveau")
		$('#type').val("level")
		$('#idCycle').val(idCycle)
		$('#idLevel').val(idLevel)
		$('#idParcours').val("")
		$('#labelCycle').val($.trim($('div[id-level='+idLevel+'] h3').html()))
		$('#cycleEditModal').modal('show')
	})
	
	$('.edit-parcours').on('click', function() {
		var idLevel = $(this).parent().parent().parent().parent().parent().attr('id-level')
		var idParcours = $(this).parent().parent().attr('id').split('-')[1]
		$('#cycleEditLabel').html("Editer parcours")
		$('#type').val("parcours")
		$('#idCycle').val("")
		$('#idLevel').val(idLevel)
		$('#idParcours').val(idParcours)
		$('#labelCycle').val($.trim($('#parcours-'+idParcours+' label').html()))
		$('#cycleEditModal').modal('show')
	})
	
	$('#save-cycle').on('click', function() {
		if(form_validate('#form-save-cycle')) {
			$.ajax({
				url: getBaseUrl('education/saveCycle'),
				type: 'POST',
				data: $('#form-save-cycle').serialize(),
				dataType: 'JSON',
				success: function(data) {
					if(data.status == 1) {
						window.location.reload()
					} else {
						$('#err-save-cycle').html(data.message ? data.message : 'Une erreur interne s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
					}
				},
				error: function(err) {
					$('#err-save-cycle').html('Une erreur s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
				}
			})
		}
	})
	
	$(".delete-cycle").on("click", function() {
		var idCycle = $(this).parent().parent().parent().parent().attr("id-cycle")
		$('#cycleDeleteLabel').html('Supprimer cycle ?')
		$('#question-delete').html(`La suppression du cycle engendrera la suppression des cours, des étudiants, des parcours et des niveaux dans le cycle.<br/>
		      	Voulez-vous vraiment supprimer ? Cette action est irréversible`);
		$('#idItemDelete').val(idCycle)
		$('#typeDeletion').val("deleteCycle")
		$('#cycleDeleteModal').modal('show')
	})
	
	$('.delete-level').on('click', function() {
		var idLevel = $(this).parent().parent().parent().attr('id-level')
		$('#cycleDeleteLabel').html('Supprimer niveau ?')
		$('#question-delete').html(`La suppression du niveau engendrera la suppression des cours, des étudiants et des parcours dans le niveau.<br/>
		      	Voulez-vous vraiment supprimer ? Cette action est irréversible`);
		$('#idItemDelete').val(idLevel)
		$('#typeDeletion').val("deleteLevel")
		$('#cycleDeleteModal').modal('show')
	})
	
	$('.delete-parcours').on('click', function() {
		var idParcours = $(this).parent().parent().attr('id').split('-')[1]
		$('#cycleDeleteLabel').html('Supprimer parcours ?')
		$('#question-delete').html(`La suppression du parcours engendrera la suppression des cours et des étudiants dans le parcours.<br/>
		      	Voulez-vous vraiment supprimer ? Cette action est irréversible`);
		$('#idItemDelete').val(idParcours)
		$('#typeDeletion').val("deleteParcours")
		$('#cycleDeleteModal').modal('show')
	})
	
	$('#form-delete-cycle').on('submit', function(e) {
		e.preventDefault()
		var action = $('#typeDeletion').val()
		$.ajax({
			url: getBaseUrl('education/'+action+'?id='+$('#idItemDelete').val()),
			type: 'GET',
			dataType: 'JSON',
			success: function(data) {
				if(data.status == 1) {
					switch(action) {
						case "deleteCycle":
							$('div[id-cycle='+$('#idItemDelete').val()+']').remove()
							break;
						case "deleteLevel":
							$('div[id-level='+$('#idItemDelete').val()+']').remove()
							break;
						case "deleteParcours":
							$('#parcours-'+$('#idItemDelete').val()).remove()
							break;
					}
					$('#success-delete-cycle').html(data.message).show().delay(2000).fadeOut(600).finally($('#cycleDeleteModal').modal('hide'))
				} else {
					$('#err-delete-cycle').html(data.message ? data.message : 'Une erreur interne s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
				}
			},
			error: function(err) {
				$('#err-delete-cycle').html('Une erreur s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
			}
		})
	})
})