$(document).ready(function() {
	$('#add-univ-year').on('click', function() {
		$('#idAU').val(0)
		$('#labelAU').val("")
		$('#debAU').val("")
		$('#finAU').val("")
		$('#univYearModal').modal('show')
	})
	
	$('#save-au').on('click', function() {
		if(formValidate('#form-save-ua')) {
			$.ajax({
				url: getBaseUrl('educations/saveAU'),
				type: 'POST',
				dataType: 'JSON',
				data: $("#form-save-ua").serialize(),
				success: function(data) {
					if(data.status == 1) {
						location.reload()
					} else {
						$('#err-save-au').html(data.message ? data.message : 'Echec de l\'enregistrement! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
					}
				},
				error: function() {
					$('#err-save-au').html('Une erreur s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
				}
			})
		}
	})
	
	$('.edit-au').on('click', function() {
		var idAU = $(this).parent().parent().attr('id').split('-')[1]
		$.ajax({
			url: getBaseUrl('educations/detailsAU?id='+idAU),
			dataType: 'JSON',
			success: function(data) {
				if(data.status == 1) {
					$('#idAU').val(data.infos.year_id)
					$('#labelUA').val(data.infos.year_libelle)
					$('#debUA').val(data.infos.year_beginning)
					$('#finUA').val(data.infos.year_ending)
					$('#univYearModal').modal('show')
				} else {
					alert(data.message ? data.message : "Echec du chargement des infos de l'année universitaire. Veuillez réessayer")
				}
			},
			error: function() {
				alert('Une erreur s\'est produite. Veuillez réessayer!')
			}
		})
	})
	
	$('.delete-au').on('click', function() {
		var idAU = $(this).parent().parent().attr('id').split('-')[1]
		$('#typeDeletionPeriod').val('AU')
		$('#idItemDeletePeriod').val(idAU)
		$('#periodDeleteModal').modal('show')
	})
	
	$('#form-delete-period').on('submit', function(e) {
		e.preventDefault()
		$.ajax({
			url: getBaseUrl('educations/deletePeriod'),
			type: 'POST',
			data: $('#form-delete-period').serialize(),
			dataType: 'JSON',
			success: function(data) {
				if(data.status == 1) {
					switch($('#typeDeletionPeriod').val()) {
						default:
							$('#au-'+$('#idItemDeletePeriod').val()).remove()
							break;
					}
					$('#periodDeleteModal').modal('hide')
				} else {
					$('#err-delete-period').html(data.message ? data.message :  'Une erreur s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
				}
			},
			error: function() {
				$('#err-delete-period').html('Une erreur s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
			}
		})
	})
	
	$('.manage-periods').on('click', function() {
		var idAU = $(this).parent().parent().attr('id').split('-')[1]
		$('#list-univ-year').hide()
		$('#details-univ-year').html('<img src="'+getBaseUrl('img/loading.gif')+'" alt="">').show()
		$.ajax({
			url: getBaseUrl('educations/managePeriods?id='+idAU),
			success: function(data) {
				$('#details-univ-year').html(data)
				$('#details-univ-year #niveau-period').trigger('change')
			}, 
			error: function() {
				alert("Echec du chargement des informations")
			}
		})
	})
	
	$(document).on('click', '#return-uy', function() {
		$('#details-univ-year').hide()
		$('#list-univ-year').show()
	})
	
	$(document).on('change', '#niveau-period', function() {
		var idNiveau = $(this).val()
		var idAU = $('#au-edit').val()
		$.ajax({
			url: getBaseUrl('educations/loadPeriodsNiveau?id='+idNiveau+'&au='+idAU),
			success: function(data) {
				$('#details-univ-year .table tbody').html(data)
			},
			error: function() {
				alert('Ne peut pas charger les périodes pour le niveau. Veuillez réessayer')
				$('#details-univ-year .table tbody').html("")
			}
		})
	})
	
	$(document).on('click', '#add-levelperiod', function() {
		$('#periodEditLabel').html("Ajouter p&eacute;riode")
		emptyForm('#form-edit-period')
		$('#period-id').val("0")
		$('#withRatrappage').prop('checked', false)
		$('#periodEditModal').modal('show')
	})
	
	$(document).on('change', '#withRatrappage', function() {
		if($(this).is(":checked")) {
			$("#rattr-container").show().find('input').attr('required', "")
		} else {
			$("#rattr-container").hide().find('input').removeAttr('required')
		}
	})
	
	$(document).on('submit', '#form-edit-period', function(e) {
		e.preventDefault()
		var formData = getFormData($(this))
		console.log(formData)
		formData['a_ratrappage'] =  $('#withRatrappage').is(':checked') ? "1" : "0"
		formData['university_year_id'] = $('#au-edit').val()
		formData['level_id'] = $('#niveau-period').val()
//		formData += "&niv="+$('#niveau-period').val()+"&au="+$('#au-edit').val()+'&a_ratrappage='+($('#withRatrappage').is(':checked') ? "1" : "0")
		if(formValidate(this)){
			var debPer = new Date($('#debPeriod').val()),
				finPer = new Date($('#finPeriod').val()),
				debExam = new Date($('#debExam').val()),
				finExam = new Date($('#finExam').val()), debRattr, finRattr;
			if($('#withRatrappage').is(':checked')) {
				debRattr = new Date($('#debRattr').val())
				finRattr = new Date($('#finRattr').val())
			}
			if(debPer > finPer) {
				$('#err-save-levelperiod').html('Vérifiez les dates début et fin SVP').show().delay(3000).fadeOut(600)
			} else if(debExam > finExam) {
				$('#err-save-levelperiod').html('Vérifiez les dates d\'examen SVP. Début > Fin!!').show().delay(3000).fadeOut(600)
			} else if( debExam < debPer || finPer < debExam) {
				$('#err-save-levelperiod').html('Vérifiez les dates d\'examen SVP. Date début examen pas comprise dans la période.').show().delay(3000).fadeOut(600)
			} else if( finPer < finExam) {
				$('#err-save-levelperiod').html('Vérifiez les dates d\'examen SVP. Date fin examen pas comprise dans la période.').show().delay(3000).fadeOut(600)
			} else if($('#withRatrappage').is(':checked') && debRattr > finRattr) {
				$('#err-save-levelperiod').html('Vérifiez les dates de rattrapage SVP. Début > Fin!!').show().delay(3000).fadeOut(600)
			} else if($('#withRatrappage').is(':checked') && debRattr > debPer) {
				$('#err-save-levelperiod').html('Vérifiez les dates de rattrapage SVP. Début avant le début de la période!').show().delay(3000).fadeOut(600)
			} else {
				$.ajax({
					url: getBaseUrl('educations/saveLevelPeriod'),
					data: formData,
					type: 'POST',
					dataType: 'JSON',
					success: function(data) {
						//TODO: treat response
					},
					error: function() {
						$('#err-save-levelperiod').html('Une erreur s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
					}
				})
			}
			
		} else {
			$('#err-save-levelperiod').html('Veuillez remplir correctement tous les champs SVP.<br/> Ouvrez l\'onglet examen si vous ne l\'avez pas encore fait.').show().delay(3000).fadeOut(600)
		}
		
	})
	
	$(document).on('blur', '#labePeriod', function() {
		var period = $(this).val()
		if($.trim(period) != "") {
			if($.trim($('#labelExam').val()) == "") {
				$('#labelExam').val("Examen "+period)
			}
			if($('#withRatrappage').is(':checked') && $.trim($('#labelRattr').val()) == "") {
				$('#labelRattr').val("Rattrapage "+period)
			}
		}
		
	})
})