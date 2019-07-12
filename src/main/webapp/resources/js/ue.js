$(document).ready(function() {
	//first loading
	//Trigger change level
	$(function() {
		$('#choixLevel').trigger('change');
		$('#choixPeriodLevel').trigger('change');
	})
	
	// all the crud of ue
	$('#add-ue').on('click', function() {
		$('#ueAddLabel').html('Ajouter un Unite d\'enseignement')
		emptyForm("#ueAddModal")
		idParcours = $("#choixParcours").val()
		$('#idUE').val('0')
		$("#idParcoursUE").val(idParcours)
		$('#ueAddModal').modal('show')
	})
	
	$('#save-ue').on('click', function(){
		console.log("here I am to worship")
		if (formValidate('#form-save-ue')){
			$.ajax({
				url: getBaseUrl('ue/save'),
			    type: 'POST',
			    data: $('#form-save-ue').serialize(),
			    dataType : 'JSON',
			    success: function(data){
			    	console.log(data)
			    	if(data.status == 1) {
			    		$('#success-save-ue').html(data.message ? data.message : 'Enregistre avec succes.').show().delay(3000).fadeOut(600)
						window.location.reload()
					} else {
						$('#err-save-ue').html(data.message ? data.message : 'Une erreur interne s\'est produite! Veuillez reessayer...').show().delay(3000).fadeOut(600)
					}
			    },
			    error: function(err){
			    	$('#err-save-user').html('Une erreur s\'est produite! Veuillez reessayer...').show().delay(3000).fadeOut(600)
			    }
			})
		}
	})
	
	$(document).on('click','.edit-ue', function(){
		console.log('test edit user')
		var idUE = $(this).attr('ue-edit-id')
		$('#ueAddLabel').html('Editer unite d\'enseignement')
		$('#idUE').val(idUE)
		$.ajax({
			url: getBaseUrl('ue/details?id='+idUE),
			type: "GET",
			dataType: "JSON",
			success: function(data) {
				console.log(data)
				$('#idParcoursUE').val(data.id_parcours)
				$('#ueLibelle').val(data.libelle)
				$('#ueType').val(data.type)
				$("#ueAddModal").modal('show')
			},
			error: function(err) {
				console.log(err)
			}
		})
	})
	
	$(document).on('click','.delete-ue', function() {
		var idUE = $(this).attr('ue-delete-id')
		$('#idUEDelete').val(idUE)
		$('#ueDeleteModal').modal('show')
	})
	
	$('#form-delete-ue').on('submit', function(e) {
			e.preventDefault()
			$.ajax({
				url: getBaseUrl('ue/delete?id='+$('#idUEDelete').val()),
				type: 'GET',
				dataType: 'JSON',
				success: function(data) {
					if(data.status == 1) {
						$("#ue-"+$('#idUEDelete').val()).remove()
						$('#success-delete-ue').html('Suppression avec success...').show().delay(3000).fadeOut(600)
						$('#ueDeleteModal').modal('hide')
					} else {
						$('#err-delete-ue').html('Une erreur interne s\'est produite! Veuillez ressayer...').show().delay(3000).fadeOut(600)
					}
				},
				error: function(err) {
					$('#err-delete-ue').html('Une erreur s\'est produite! Veuillez ressayer...').show().delay(3000).fadeOut(600)
				}
			})
		})
	// all the crud of ec
		$(document).on('click', '.add-ec', function() {
			$('#ecAddLabel').html('Ajouter un Element constitutif')
			emptyForm("#ecAddModal")
			var idUeEc = $(this).attr('ec-add-id')
			var idProfEc = $("#get-prof-id-ec").attr('prof-ec-id')
			$('#idEC').val('0')
			$('#idUeEc').val(idUeEc)
			$('#idProfessorEc').val(idProfEc)
			$('#ecAddModal').modal('show')
			console.log("hello")
			console.log(idProfEc)
		})
		
		$('#save-ec').on('click', function(){
			console.log("here I am ....... III")
			console.log($("#form-save-ec").serialize())
			if (formValidate('#form-save-ec')){
				$.ajax({
					url: getBaseUrl('ec/save'),
				    type: 'POST',
				    data: $('#form-save-ec').serialize(),
				    dataType : 'JSON',
				    success: function(data){
				    	console.log(data)
				    	if(data.status == 1) {
				    		$('#success-save-ec').html(data.message ? data.message : 'Enregistre avec succes.').show().delay(3000).fadeOut(600)
							window.location.reload()
						} else {
							$('#err-save-ec').html(data.message ? data.message : 'Une erreur interne s\'est produite! Veuillez reessayer...').show().delay(3000).fadeOut(600)
						}
				    },
				    error: function(err){
				    	$('#err-save-user').html('Une erreur s\'est produite! Veuillez reessayer...').show().delay(3000).fadeOut(600)
				    }
				})
			}
		})
		
	$(document).on('click', '.edit-ec', function(){
		var idEC = $(this).attr('ec-edit-id')
		$('#ecAddLabel').html('Editer enseignement constitutif')
		$('#idEC').val(idEC)
		$.ajax({
			url: getBaseUrl('ec/details?id='+idEC),
			type: "GET",
			dataType: "JSON",
			success: function(data) {
				console.log(data)
				$('#idUeEc').val(data.id_ue)
				$('#idProfessorEc').val(data.id_prof)
				$('#ecLibelle').val(data.libelle)
				$('#ecCredit').val(data.credit)
				$('#ecNotation').val(data.notation)
				$('#ecCoefficient').val(data.coefficient)
				$('#ecHoraire').val(data.horaire)
				$('#ecPresenciel').val(data.presenciel)
				$('#ecPersonnel').val(data.personnel)
				$("#ecAddModal").modal('show')
			},
			error: function(err) {
				console.log(err)
			}
		})
	})
	
	$(document).on('click', '.delete-ec', function() {
		var idEC = $(this).attr('ec-delete-id')
		$('#idECDelete').val(idEC)
		$('#ecDeleteModal').modal('show')
	})
	
	$('#form-delete-ec').on('submit', function(e) {
		e.preventDefault()
		$.ajax({
			url: getBaseUrl('ec/delete?id='+$('#idECDelete').val()),
			type: 'GET',
			dataType: 'JSON',
			success: function(data) {
				console.log(data)
				if(data.status == 1) {
					$("#ec-"+$('#idECDelete').val()).remove()
					$('#success-delete-ue').html('Suppression avec success...').show().delay(3000).fadeOut(600)
					$('#ecDeleteModal').modal('hide')
				} else {
					$('#err-delete-ec').html('Une erreur interne s\'est produite! Veuillez ressayer...').show().delay(3000).fadeOut(600)
				}
			},
			error: function(err) {
				$('#err-delete-ec').html('Une erreur s\'est produite! Veuillez ressayer...').show().delay(3000).fadeOut(600)
			}
		})
	})
	
	$('#choixLevel').on('change', function() {
		var idLevel = $(this).val()
		addParcoursData(idLevel, '#choixParcours')
	})
	
	$('#choixParcours').on('change', function() {
		$('tbody').html('loading...');
		var id = $(this).val();
		$.ajax({
			url: getBaseUrl('ue/list?idParcours='+id),
			success: function(data) {
				$('tbody').html(data);
			},
			error: function() {
				alert('Echec du chargement des UEs!!!!')
			}
		})
	})
	
	$('#choixPeriodLevel').on('change', function() {
		var idLevel = $(this).val()
		addParcoursData(idLevel, '#choixPeriodParcours')
	})
	
	$('#choixPeriodParcours, #choixPeriodUY').on('change', function() {
		$('tbody').html('loading...')
		var id = $('#choixPeriodParcours').val()
		var idLevel = $('#choixPeriodLevel').val()
		var idUY = $('#choixPeriodUY').val()
		$.ajax({
			url: getBaseUrl('ue/listCoursePeriod?idParcours='+id+'&idLevel='+idLevel+'&idUY='+idUY),
			success: function(data) {
				$('thead th.period').remove()
				$('tbody').html(data);
				$('th.period').appendTo('#table-period-courses thead tr')
				$('.check-ue').each(function() {
					var idUE = $(this).attr('id').split('-')[1]
					var idPer = $(this).attr('id').split('-')[2]
					if(checkAllChecked(idUE, idPer)) {
						$(this).prop('checked', true)
					} else {
						$(this).prop('checked', false)
					}
				})
			},
			error: function() {
				alert('Echec du chargement des UEs!!!!')
			}
		})
	})
	
	$(document).on('change', '.check-ue', function() {
		var idUE = $(this).attr('id').split('-')[1]
		var idPer = $(this).attr('id').split('-')[2]
		if($(this).is(':checked')) {
			$('.check-'+idUE+'-'+idPer).prop('checked', true).trigger('change')
		} else {
			$('.check-'+idUE+'-'+idPer).prop('checked', false).trigger('change')
		}
	})
	
	$(document).on('change', '.check-ec', function() {
		var idEC = $(this).attr('id').split('-')[1]
		var idPer = $(this).attr('id').split('-')[2]
		var idUE = $(this).attr('id').split('-')[3]
		var add = $(this).is(':checked')
		$.ajax({
			url: getBaseUrl('ec/saveCoursePeriod'),
			type: 'POST',
			dataType: 'JSON',
			data: {idEC, idPer, idUE, add},
			success: function(data) {
				if(data.status == 1) {
					if(checkAllChecked(idUE, idPer)) {
						$('#checkperiod-'+idUE+'-'+idPer).prop('checked', true)
					} else {
						$('#checkperiod-'+idUE+'-'+idPer).prop('checked', false)
					}
					$('#success-save-periodcourse').html(data.message).show().delay(3000).fadeOut(500)
				} else {
					$('#error-save-periodcourse').html(data.message ? data.message : "Echec de l'op&eacute;ration. Veuillez r&eacute;essayer").show().delay(3000).fadeOut(500)
				}
			},
			error: function() {
				$('#error-save-periodcourse').html("Une erreur s'est produite lors du traitement;. Veuillez actualiser puis r&eacute;essayer").show().delay(3000).fadeOut(500)
			}
		})
	})
})

function checkAllChecked(idUE, idPer) {
	var allChecked = true
	$('.check-'+idUE+'-'+idPer).each(function() {
		if(!$(this).is(':checked')) {
			allChecked = false
		}
	})
	console.log("all checked ", allChecked)
	return allChecked
}

function addParcoursData(idLevel, selectorParcours) {
	$.ajax({
		url: getBaseUrl('student/loadParcoursByLevel?id='+idLevel),
		success: function(data) {
			$(selectorParcours).html(data).trigger('change')
		}, 
		error: function() {
			$(selectorParcours).html("").trigger('change')
		}
	})
}