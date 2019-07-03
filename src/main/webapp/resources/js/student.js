$(document).ready(function() {
	
	$('#add-student').on('click', function() {
		$('#editStudentLabel').html('Ajouter &eacute;tudiant')
		emptyForm('#form-save-student')
		$('#idStudent').val('0')
		$('#editStudentModal').modal('show')
		$('li a[href="#subscription"]').show()
		$('li a[href="#primary"]').trigger('click')
		$('#subsParcours').trigger('change')
	})
	
	$('#form-save-student').on('submit', function(e) {
		e.preventDefault()
		if($.trim( $('#passportStudent').val()) == "" && $.trim( $('#cinStudent').val()) == "") {
			$('#cinStudent').attr('required', '');
		} else if($.trim( $('#cinStudent').val()) != "") {
			$('#dateCinStudent, cinLocationStudent').attr('required', '');
		}
		if(formValidate('#form-save-student')) {
			var formData = getFormData($(this))
			formData.subs_inscription = $('#subsIsPaid').is(':checked') ? 1 : 0
			$.ajax({
				url: getBaseUrl('student/save'),
				data: formData,
				type: 'POST',
				dataType: 'JSON',
				success: function(data) {
					if(data.status == 1) {
						location.reload()
					} else {
						$('#err-save-student').html(data.message ? data.message : 'Erreur lors du traitement! Veuillez réessayer').show().delay(3000).fadeOut(600)
					}
				},
				error: function() {
					$('#err-save-student').html('Une erreur s\'est produite! Veuillez réessayer').show().delay(3000).fadeOut(600)
				}
			})
		} 
	})
	
	$('.delete-student').on('click', function() {
		var idStudent = $(this).parent().parent().attr('id').split('-')[1]
		$('#idStudentDelete').val(idStudent)
		$('#studentDeleteModal').modal('show')
	})
	
	$('.edit-student').on('click', function() {
		var idStudent = $(this).parent().parent().attr('id').split('-')[1]
		$.ajax({
			url: getBaseUrl('student/details?id='+idStudent),
			dataType: 'JSON',
			success: function(data) {
				if(data.status == 1) {
					$('#civStudent').val(data.infos.civilite);
					$('#nameStudent').val(data.infos.nom);
					$('#lastNameStudent').val(data.infos.prenom);
					$('#birthDateStudent').val(data.infos.datenaissance);
					$('#nationalityStudent').val(data.infos.nationalite);
					$('#passportStudent').val(data.infos.passeport);
					$('#cinStudent').val(data.infos.cin);
					$('#dateCinStudent').val(data.infos.datecin);
					$('#cinLocationStudent').val(data.infos.lieucin);
					$('#adressStudent').val(data.infos.adresse);
					$('#emailStudent').val(data.infos.email);
					$('#lastEtabStudent').val(data.infos.dernieretab);
					$('#conjointStudent').val(data.infos.conjoint);
					$('#fatherNameStudent').val(data.infos.pere);
					$('#fatherJobStudent').val(data.infos.professionpere);
					$('#motherNameStudent').val(data.infos.mere);
					$('#motherJobStudent').val(data.infos.professionmere);
					$('#idStudent').val(data.infos.id);
					$('#editStudentLabel').html('Modifier &eacute;tudiant')
					$('#editStudentModal').modal('show')
					$('li a[href="#subscription"]').hide()
					$('li a[href="#primary"]').trigger('click')
					$('#subsParcours').trigger('change')
				} else {
					alert(data.message ? data.message : 'Echec de l\'action')
				}
			}, 
			error: function() {
				alert('Une erreur s\'est produite. Veuillez réessayer')
			}
		})
	})
	
	$('#subsLevel').on('change', function() {
		var idLevel = $(this).val()
		$.ajax({
			url: getBaseUrl('student/loadParcoursByLevel?id='+idLevel),
			success: function(data) {
				$('#subsParcours').html(data).trigger('change')
			}, 
			error: function() {
				$('#subsParcours').html("").trigger('change')
			}
		})
	})
	
	$('#subsParcours').on('change', function() {
		var idParcours = $(this).val()
		if(idParcours == undefined) {
			$('#ue-choix-wrapper').html('')
		} else {
			$.ajax({
				url: getBaseUrl('student/loadUEToChooseByParcours?id='+idParcours),
				success: function(data) {
					$('#ue-choix-wrapper').html(data)
				}, 
				error: function() {
					$('#ue-choix-wrapper').html('')
				}
			})
		}
	})
	
	$('#form-delete-student').on('submit', function(e) {
		e.preventDefault()
		var idStudent = $('#idStudentDelete').val()
		$.ajax({
			url: getBaseUrl('student/delete?id='+idStudent),
			dataType: 'JSON',
			success: function(data) {
				if(data.status == 1) {
					$('#stud-'+idStudent).remove();
					$('#studentDeleteModal').modal('hide')
				} else {
					$('#err-delete-student').html(data.message ? data.message : 'Erreur de suppression. Veuillez réessayer').show().delay(3000).fadeOut(600)
				}
			},
			error: function() {
				$('#err-delete-student').html('Erreur survenue lors du traitement. Veuillez réessayer').show().delay(3000).fadeOut(600)
			}
		})
	})
	
	$('li a[href="#subscription"]').on('click', function() {
		$('#subsLevel').trigger('change')
	})
})