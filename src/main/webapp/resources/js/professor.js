var inSearch = false

$(document).ready(function() {
	//first loading
	//Trigger change in professor courses
	$(function() {
		$('#professorsAndUes').trigger('change');
	})
	
	$(document).on('click', '.detail-professor', function(event) {
		event.preventDefault()
		var url = getBaseUrl($(this).attr('href'))
		$.ajax({
			url: url,
			type: "GET",
			dataType: "JSON",
			success: function(data) {
				$('#detail-lastname-prof').html(data.professor_last_name)
				$('#detail-firstname-prof').html(data.professor_name)
				$('#detail-email-prof').html(data.professor_email)
				$('#detail-contact-prof').html(data.professor_contact)
				$('#detail-adresse-prof').html(data.professor_adresse)
				$('#profDetailModal').modal('show')
			},
			error: function(err) {
				console.log(err)
			}
		})
	})
	
	$('#add-professor').on('click', function() {
		$('#profAddLabel').html('Ajouter un professeur')
		emptyForm("#profAddModal")
		$('#prof-id').val('0')
		$('#uti-type').val("2")
		$('#prof-for-user-id').val('0')
		$('#profAddModal').modal('show')
	})
	
	$(document).on('click', '#save-prof', function(){
		updatePasswordStatus()
		if (formValidate('#form-save-prof')){
			$.ajax({
				url: getBaseUrl('professor/save'),
			    type: 'POST',
			    data: $('#form-save-prof').serialize(),
			    dataType : 'JSON',
			    success: function(data){
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
	
	$(document).on('click', '.edit-professor', function(){
		console.log('test edit prof')
		var idprof = $(this).attr('id-prof')
		var idprofforuser = $(this).attr('id-prof-for-user')
		$('#profAddLabel').html('Editer un Professeur')
		$.ajax({
			url: getBaseUrl('professor/details?id='+idprof),
			type: "GET",
			dataType: "JSON",
			success: function(data) {
				$('#prof-id').val(data.professor_id)
				$('#lastname-prof').val(data.professor_last_name)
				$('#firstname-prof').val(data.professor_name)
				$('#loginname-prof').val(data.professor_login)
				$('#email-prof').val(data.professor_email)
				$('#pass-prof').val("")
				$('#contact-prof').val(data.professor_contact)
				$('#adresse-prof').val(data.professor_adresse)
				$('#uti-type').val(data.user_type)
				$('#prof-for-user-id').val(data.user_id)
				$('#user-civilite-prof').val(data.user_civilite)
				$('#profIsAdmin').prop('checked', data.user_type == 1)
				$("#profAddModal").modal('show')
			},
			error: function(err) {
				console.log(err)
			}
		})
	})
	
	$(document).on('click', '.delete-professor', function() {
		var idUserProf = $(this).attr('id-profd')
		var idUserProfProf = $(this).attr('id-profdelete')
		$('#idProfDelete').val(idUserProf)
		$('#userIdDelete').val(idUserProfProf)
		$('#profDeleteModal').modal('show')
	})
	
	$('#form-delete-prof').on('submit', function(e) {
		console.log(getBaseUrl('professor/delete?id='+$('#idProfDelete').val()))
		console.log(e)
		e.preventDefault()
		$.ajax({
			url: getBaseUrl('professor/delete?id='+$('#idProfDelete').val()),
			type: 'GET',
			dataType: 'JSON',
			success: function(data) {
				if(data.status == 1) {
					$("#prof-"+$('#idProfDelete').val()).remove()
					$('#success-delete-prof').html('Suppression avec success...').show().delay(3000).fadeOut(600)
					$('#profDeleteModal').modal('hide')
				} else {
					$('#err-delete-prof').html('Une erreur interne s\'est produite! Veuillez ressayer...').show().delay(3000).fadeOut(600)
				}
			},
			error: function(err) {
				$('#err-delete-prof').html('Une erreur s\'est produite! Veuillez ressayer...').show().delay(3000).fadeOut(600)
			}
		})
	})
	
	// edit and add new professor for the study unit
	$('#add-professor-ue').on('click', function() {
		$('#ueProfAddLabel').html('Ajouter un professeur pour un unite d\'enseignement')
		$('#prof-ue-id').val('0')
		$('#').val("2")
		$('#prof-for-user-id').val('0')
		$('#ueProfAddModal').modal('show')
	})
	
	$('#save-prof-ue').on('click', function(){
		if (formValidate('#form-save-prof-ue')){
			$.ajax({
				url: getBaseUrl('professor/course/save'),
			    type: 'POST',
			    data: $('#form-save-prof-ue').serialize(),
			    dataType : 'JSON',
			    success: function(data){
			    	if(data.status == 1) {
						window.location.reload()
					} else {
						$('#err-save-prof-ue').html(data.message ? data.message : 'Une erreur interne s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
					}
			    },
			    error: function(err){
			    	$('#err-save-prof-ue').html('Une erreur s\'est produite! Veuillez r&eacute;essayer...').show().delay(3000).fadeOut(600)
			    }
			})
		}
	})
	
	$('#professorsAndUes').on('change', function() {
		$('tbody').html('loading...');
		var id = $(this).val();
		$.ajax({
			url: getBaseUrl('professor/courses/list?id='+id),
			success: function(data) {
				$('tbody').html(data);
			},
			error: function() {
				alert('Echec du chargement des UEs!!!!')
			}
		})
	})
	
	$(document).on('click', '#pagination-professors li.page-item a', function(e) {
		e.preventDefault()
		console.log('call it')
		var parent = $(this).parent()
		if($(this).attr('id') == "previous-page") {
			var pageNext = parseInt($('li.page-item.active a').attr('page-target')) - 1
			console.log('next page ', pageNext)
			if(!$('li.page-item.active a').attr('page-target') != "1") {
				$('a[page-target='+pageNext+']').trigger('click')
			}
		} else if($(this).attr('id') == "next-page") {
			var pageNext = parseInt($('li.page-item.active a').attr('page-target')) + 1
			console.log('next page ', pageNext)
			if($('a[page-target='+pageNext+']').html()) {
				$('a[page-target='+pageNext+']').trigger('click')
			}
		} else {
			if(!parent.hasClass('active')) {
				var numPage = $.trim( $(this).html())
				var criteria = $('#search-student').val()
				if(criteria == "" || !inSearch) {
					$.ajax({
						url: getBaseUrl('professors/bypage?page='+numPage),
						success: function(data) {
							$('#table-professors tbody').html(data)
							$('li.page-item').removeClass('active')
							parent.addClass('active')
						},
						error: function() {
							alert('Erreur lors du chargement de la page')
						}
					})
				} else {
					$.ajax({
						url: getBaseUrl('professors/search?criteria='+criteria+'&page='+numPage),
						success: function(data) {
							$('#table-professors tbody').html(data)
							$('li.page-item').removeClass('active')
							parent.addClass('active')
						},
						error: function() {
							alert('Erreur lors du chargement de la page')
						}
					})
				}
			}
		}
	})
	
	$(document).on('keyup', '#search-prof', function(e) {
		if(e.keyCode == 13 || e.which == 13) {
			inSearch = true
			console.log('Start search')
			var criteria = $(this).val()
			$.ajax({
				url: getBaseUrl('professors/search?criteria='+criteria),
				success: function(data) {
					var paginationContent = data.indexOf("<div id = \"pagination-search\" style = \"display:none;\">") > -1 ? data.substring(data.indexOf("<div id = \"pagination-search\" style = \"display:none;\">") + 55, data.length - 7) : undefined
					$.when($('#table-professors tbody').html(data)).then(function() {
						$('div.pagination ul').html(paginationContent)
					})
					if($.trim(criteria) == "") inSearch = false
				},
				error: function() {
					alert('Erreur lors du chargement des professeurs')
				}
			})
		}
	})
	
	$(document).on('change','#profIsAdmin', function() {
		if($(this).is(':checked')) {
			$('#uti-type').val(1)
		} else {
			$('#uti-type').val(2)
		}
	})
})

function updatePasswordStatus() {
	if($('#prof_id').val() == 0) {
		$('#pass-prof').attr("required")
	} else {
		if($('#pass-prof').val() == "") {
			$('#pass-prof').removeAttr("required")
		} else {
			$('#pass-prof').attr("required")
		}
	}
}