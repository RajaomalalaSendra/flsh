$(document).ready(function() {
	// all the crud of ue
	$('#add-ue').on('click', function() {
		$('#ueAddLabel').html('Ajouter un Unite d\'enseignement')
		emptyForm("#ueAddModal")
		idParcours = $("#parcours-ue-id").val()
		$('#idUE').val('0')
		$("#idParcoursUE").val(idParcours)
		$('#ueAddModal').modal('show')
	})
	$('#save-ue').on('click', function(){
		console.log("here I am")
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
	
	$('.edit-ue').on('click', function(){
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
	
	$('.delete-ue').on('click', function() {
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
		$('.add-ec').on('click', function() {
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
		$('.edit-ec').on('click', function(){
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
	
	$('.delete-ec').on('click', function() {
		console.log("We are deleting ec....")
		var idEC = $(this).attr('ec-delete-id')
		console.log(idEC)
		$('#idECDelete').val(idEC)
		$('#ecDeleteModal').modal('show')
	})
})