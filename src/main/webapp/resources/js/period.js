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
			if(new Date($('#').val()) >= new Date($('#').val())) {
				$('#err-save-au').html('V&eacute;rifier les dates SVP. Début supérieur ou &eacute;gale &agrave; la fin!!').show().delay(3000).fadeOut(600)
			} else {
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
					alert(data.message ? data.message : "Echec du chargement des infos de l'ann&eacute;e universitaire. Veuillez r&eacute;essayer")
				}
			},
			error: function() {
				alert('Une erreur s\'est produite. Veuillez r&eacute;essayer!')
			}
		})
	})
	
	$('.delete-au').on('click', function() {
		var idAU = $(this).parent().parent().attr('id').split('-')[1]
		$('#typeDeletionPeriod').val('AU')
		$('#idItemDeletePeriod').val(idAU)
		$('#cycleDeleteLabel').html('Supprimer ann&eacute;e universitaire')
		$('#question-delete').html(`La suppression de l'ann&eacute;e universitaire entrainera la suppression des &eacute;tudiants, des p&eacute;riodes ainsi que des examens dans l'ann&eacute;e universitaire!<br>
		      	Voulez-vous continuer ? Cette action est irr&eacute;versible`)
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
						case "periode":
							$('#details-univ-year #niveau-period').trigger('change')
							break;
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
				alert('Ne peut pas charger les p&eacute;riodes pour le niveau. Veuillez r&eacute;essayer')
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
				$('#err-save-levelperiod').html('V&eacute;rifiez les dates d&eacute;but et fin SVP').show().delay(3000).fadeOut(600)
			} else if(debExam > finExam) {
				$('#err-save-levelperiod').html('V&eacute;rifiez les dates d\'examen SVP. D&eacute;but > Fin!!').show().delay(3000).fadeOut(600)
			} else if( debExam < debPer || finPer < debExam) {
				$('#err-save-levelperiod').html('V&eacute;rifiez les dates d\'examen SVP. Date d&eacute;but examen pas comprise dans la p&eacute;riode.').show().delay(3000).fadeOut(600)
			} else if( finPer < finExam) {
				$('#err-save-levelperiod').html('V&eacute;rifiez les dates d\'examen SVP. Date fin examen pas comprise dans la p&eacute;riode.').show().delay(3000).fadeOut(600)
			} else if($('#withRatrappage').is(':checked') && debRattr > finRattr) {
				$('#err-save-levelperiod').html('V&eacute;rifiez les dates de rattrapage SVP. D&eacute;but > Fin!!').show().delay(3000).fadeOut(600)
			} else if($('#withRatrappage').is(':checked') && debRattr < debPer) {
				$('#err-save-levelperiod').html('V&eacute;rifiez les dates de rattrapage SVP. D&eacute;but avant le d&eacute;but de la p&eacute;riode!').show().delay(3000).fadeOut(600)
			} else {
				$.ajax({
					url: getBaseUrl('educations/saveLevelPeriod'),
					data: formData,
					type: 'POST',
					dataType: 'JSON',
					success: function(data) {
						if(data.status == 1) {
							$('#details-univ-year #niveau-period').trigger('change')
							$('#periodEditModal').modal('hide')
						} else {
							$('#err-save-levelperiod').html(data.message ? data.message : 'Echec de l\'enregistrement! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
						}
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
	
	$(document).on('click', '.edit-period', function() {
		var idPeriod = $(this).parent().parent().attr('id').split('-')[1]
		$.ajax({
			url: getBaseUrl('educations/detailsPeriod?id='+idPeriod),
			dataType: 'JSON',
			success: function(data) {
				if(data.status == 1) {
					$('#period-id').val(data.infos.per_id)
					$('#labePeriod').val(data.infos.per_libellecourt)
					$('#labePeriod2').val(data.infos.per_libellelong)
					$('#debPeriod').val(data.infos.per_debut)
					$('#finPeriod').val(data.infos.per_fin)
					$('#withRatrappage').prop("checked", data.infos.per_arattr)
					$('#labelExam').val(data.infos.exam_libelle)
					$('#debExam').val(data.infos.exam_debut)
					$('#finExam').val(data.infos.exam_fin)
					$('#labelRattr').val(data.infos.rattr_libelle)
					$('#debRattr').val(data.infos.rattr_debut)
					$('#finRattr').val(data.infos.rattr_fin)
					$('#periodEditModal').modal('show')
				} else {
					alert(data.message ? data.message : "Echec du chargement des infos de l'ann&eacute;e universitaire. Veuillez r&eacute;essayer")
				}
			},
			error: function() {
				alert('Une erreur s\'est produite. Veuillez r&eacute;essayer!')
			}
		})
	})
	
	$(document).on('click', '.delete-period', function() {
		var idPer = $(this).parent().parent().attr('id').split('-')[1]
		$('#typeDeletionPeriod').val('periode')
		$('#idItemDeletePeriod').val(idPer)
		$('#cycleDeleteLabel').html('Supprimer p&eacute;riode')
		$('#question-delete').html(`La suppression de la p&eacute;riode entrainera la suppression des examens dans la p&eacute;riode!<br>
		      	Voulez-vous continuer ? Cette action est irr&eacute;versible`)
		$('#periodDeleteModal').modal('show')
	})
})