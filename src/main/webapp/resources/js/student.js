var infosSubscription
var inSearch = false
var cropper
var changedImage = false
$(document).ready(function() {
	
	$(function() {
		if($('#choixLevel').html()) {
			$('#choixLevel').trigger('change')
		}
	})
	
	$('#add-student').on('click', function() {
		$('#editStudentLabel').html('Ajouter &eacute;tudiant')
		emptyForm('#form-save-student')
		$('#idStudent').val('0')
		$('#editStudentModal').modal('show')
		$('li a[href="#subscription"]').show()
		$('li a[href="#primary"]').trigger('click')
		$('#subsParcours').trigger('change')
		var $image = $('#image-student')
		
		var options = {
				  zoomable: true,
				  autoCrop: true,
				  crop: function(event) {
				    console.log(event.x)
				    console.log(event.y)
				    console.log(event.width)
				    console.log(event.height)
				    console.log(event.rotate)
				    console.log(event.scaleX)
				    console.log(event.scaleY)
				  }
				}
		
		$image.cropper(options)
		// the image cropper
		cropper  = $image.data('cropper');
		console.log(cropper)
		clickZoomAndRotation(cropper)
		
		/*Create the upload for the image of the student*/
		  var URL = window.URL || window.webkitURL	
		  var $inputImage = $('#inputImage')
		  var originalImageURL = $image.attr('src')
		  var uploadedImageName = 'cropped_image.jpg'
		  var uploadedImageType = 'image/jpeg'
		  var uploadedImageURL
		
		  if (URL) {
		    $inputImage.change(function () {
		      var files = this.files
		      var file
		
		      if (!$image.data('cropper')) {
		        return
		      }
		
			      if (files && files.length) {
			        file = files[0]
			
			        if (/^image\/\w+$/.test(file.type)) {
			          uploadedImageName = file.name
			          uploadedImageType = file.type
			
			          if (uploadedImageURL) {
			            URL.revokeObjectURL(uploadedImageURL)
			          }
			
			          uploadedImageURL = URL.createObjectURL(file)
			          $image.cropper('destroy').attr('src', uploadedImageURL).cropper(options)
			          $inputImage.val('')
			          
			          cropper = $image.data('cropper')
			          
			          clickZoomAndRotation(cropper)
			        } else {
			          window.alert('Please choose an image file.')
			        }
			      }
			    });
			  } else {
			    $inputImage.prop('disabled', true).parent().addClass('disabled')
			  }

	})
	
	$("#save-student").on('click', function(){
		console.log(cropper)
		console.log("getCroppedCanvas: ", $('#image-student').cropper("getCroppedCanvas").toDataURL("image/png"))
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
	
	$(document).on('click', '.delete-student', function() {
		var idStudent = $(this).parent().parent().attr('id').split('-')[1]
		$('#idStudentDelete').val(idStudent)
		$('#studentDeleteModal').modal('show')
	})
	
	$(document).on('click', '.edit-student', function() {
		var idStudent = $(this).parent().parent().attr('id').split('-')[1]
		showDetailsStudent(idStudent);
	})
	
	$('#subsLevel').on('change', function() {
		var idLevel = $(this).val()
		addLevelsParcours(idLevel, '#subsParcours')
	})
	
	$('#subsParcours').on('change', function() {
		var idParcours = $(this).val()
		addUEChoiceData(idParcours, '#ue-choix-wrapper')
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
	
	$('#choixLevel, #choixUY').on('change', function() {
		var idUY = $('#choixUY').val()
		var idLevel = $('#choixLevel').val()
		$.ajax({
			url: getBaseUrl('students/loadStudentsByUnivYearAndLevel'),
			type: 'POST',
			data: {idUY, idLevel},
			success: function(data) {
				$('tbody').html(data)
				var paginationContent = data.indexOf("<div id = \"pagination-search\" style = \"display:none;\">") > -1 ? data.substring(data.indexOf("<div id = \"pagination-search\" style = \"display:none;\">") + 55, data.length - 7) : undefined
				console.log('html pagination', paginationContent)
				$('div.pagination ul').html(paginationContent)
			},
			error: function() {
				$('tbody').html('')
				alert('Echec du chargement')
			}
		})
	})
	
	$(document).on('click', '.show-details-student', function() {
		var idStudent = $(this).parent().parent().attr('id').split('-')[1]
		showDetailsStudent(idStudent, true)
	})
	
	$(document).on('click', '.subscribe-student', function() {
		var selector = $(this).parent().parent()
		var idStudent = selector.attr('id').split('-')[1]
		var nom_prenom = selector.find('td').eq(1).html()+' '+selector.find('td').eq(2).html()
		infosSubscription = undefined
		$('#studentSubsIsPaid').prop('checked', false)
		$('#studentSubsLevel').trigger('change')
		$('#student-name').html(nom_prenom)
		$('#id-student-subscribe').val(idStudent)
		$('#studentSubscribeModal').modal('show')
	})
	
	$(document).on('change', '#studentSubsLevel', function() {
		var idLevel = $(this).val()
		addLevelsParcours(idLevel, '#studentSubsParcours')
	})
	
	$('#studentSubsParcours').on('change', function() {
		var idParcours = $(this).val()
		addUEChoiceData(idParcours, '#subscribe-ue-choix-wrapper')
	})
	
	$(document).on('submit', '#form-subscribe-student', function(e) {
		e.preventDefault()
		if(formValidate('#form-subscribe-student')) {
			var formData = getFormData($(this))
			formData.subs_inscription = $('#studentSubsIsPaid').is(':checked') ? 1 : 0
			formData.subs_iduy = $('#choixUY').val()
			$.ajax({
				url: getBaseUrl('student/saveSubscription'),
				type: 'POST',
				data: formData,
				dataType: 'JSON',
				success: function(data) {
					if(data.status == 1) {
						$('#choixLevel').trigger('change')
						$('#success-subscribe-student').html(data.message).show().delay(3000).fadeOut(600).finally($('#studentSubscribeModal').modal('hide'))
					} else {
						$('#err-subscribe-student').html(data.message ? data.message : "Echec de l'enregistrement de l'inscription. Veuillez réessayer!").show().delay(3000).fadeOut(600)
					}
				}, 
				error: function() {
					$('#err-subscribe-student').html("Erreur lors de l'enregistrement de l'inscription").show().delay(3000).fadeOut(600)
				}
			})
		}
	})
	
	$(document).on('click', '.edit-subs', function() {
		var idStudent = $(this).parent().parent().attr('id').split('-')[1]
		var idUY = $('#choixUY').val();
		var idLevel = $('#choixLevel').val();
		$.ajax({
			url: getBaseUrl('student/getSubscriptionDetails'),
			dataType: 'JSON',
			type: 'POST',
			data: { idStudent, idUY, idLevel},
			success: function(data) {
				if(data.status == 1) {
					infosSubscription = data.infos
					var nom_prenom = $('#stud-'+idStudent).find('td').eq(1)+" "+$('#stud-'+idStudent).find('td').eq(2)
					$('#studentSubsIsPaid').prop('checked', data.infos.inscrit == 1)
					$('#studentSubsLevel').trigger('change')
					$('#dateStudentSubs').val(infosSubscription.date)
					$('#student-name').html(nom_prenom)
					$('#id-student-subscribe').val(idStudent)
					$('#studentSubscribeModal').modal('show')
				} else {
					alert(data.message ? data.message : "Echec lors du traitement")
				}
			},
			error: function() {
				alert("Erreur lors de la récupération des infos. Veuillez actualiser puis réessayer!")
			}
		})
	})
	
	$(document).on('click', '.delete-subs', function() {
		var idStudent = $(this).parent().parent().attr('id').split('-')[1]
		$('#idStudentUnsubscribe').val(idStudent)
		$('#removeSubscriptionStudentModal').modal('show')
	})
	
	$(document).on('submit', '#form-unsubscribe-student', function(e) {
		e.preventDefault()
		$.ajax({
			url: getBaseUrl('student/deleteSubscription?idStudent='+$('#idStudentUnsubscribe').val()+'&idUY='+$('#choixUY').val()),
			dataType: 'JSON',
			success: function(data) {
				if(data.status == 1) {
					$('#stud-'+$('#idStudentUnsubscribe').val()).remove()
					$('#success-unsubscribe-student').html(data.message).show().delay(2000).fadeOut(500).finally($('#removeSubscriptionStudentModal').modal('hide'))
				} else {
					$('#err-unsubscribe-student').html(data.message ? data.message : "Echec de la désincription. Veuillez réessayer!").show().delay(3000).fadeOut(600)
				}
 			},
			error: function() {
				$('#err-unsubscribe-student').html("Erreur lors du traitement de l'op&eactue;ration. Veuillez actualiser puis réessayer.").show().delay(3000).fadeOut(600)
			}
		})
	})
	
	$(document).on('click', '#pagination-students li.page-item a', function(e) {
		e.preventDefault()
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
						url: getBaseUrl('students/bypage?page='+numPage),
						success: function(data) {
							$('#table-students tbody').html(data)
							$('li.page-item').removeClass('active')
							parent.addClass('active')
						},
						error: function() {
							alert('Erreur lors du chargement de la page')
						}
					})
				} else {
					$.ajax({
						url: getBaseUrl('students/search?criteria='+criteria+'&page='+numPage),
						success: function(data) {
							$('#table-students tbody').html(data)
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
	
	$(document).on('keyup', '#search-student', function(e) {
		if(e.keyCode == 13 || e.which == 13) {
			inSearch = true
			console.log('Start search')
			var criteria = $(this).val()
			$.ajax({
				url: getBaseUrl('students/search?criteria='+criteria),
				success: function(data) {
					var paginationContent = data.indexOf("<div id = \"pagination-search\" style = \"display:none;\">") > -1 ? data.substring(data.indexOf("<div id = \"pagination-search\" style = \"display:none;\">") + 55, data.length - 7) : undefined
					$.when($('#table-students tbody').html(data)).then(function() {
						$('div.pagination ul').html(paginationContent)
					})
					if($.trim(criteria) == "") inSearch = false
				},
				error: function() {
					alert('Erreur lors du chargement des étudiants')
				}
			})
		}
	})
	
	$(document).on('keyup', '#search-student-subscribe', function(e) {
		if(e.keyCode == 13 || e.which == 13) {
			inSearch = true
			console.log('Search subscribed student')
			var criteria = $(this).val()
			$.ajax({
				url: getBaseUrl('students/searchSubscribed?criteria='+criteria+'&idUY='+$('#choixUY').val()+'&idLevel='+$('#choixLevel').val()),
				success: function(data) {
					var paginationContent = data.indexOf("<div id = \"pagination-search\" style = \"display:none;\">") > -1 ? data.substring(data.indexOf("<div id = \"pagination-search\" style = \"display:none;\">") + 55, data.length - 7) : undefined
					$.when($('#table-subscribed tbody').html(data)).then(function() {
						$('div.pagination ul').html(paginationContent)
					})
					if($.trim(criteria) == "") inSearch = false
				},
				error: function() {
					alert('Erreur lors du chargement des étudiants')
				}
			})
		}
	})
	
	$(document).on('click', '#pagination-subscription li.page-item a', function(e) {
		e.preventDefault()
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
				var criteria = $('#search-student-subscribe').val()
				var idUY = $('#choixUY').val()
				var idLevel = $('#choixLevel').val()
				if(criteria == "" || !inSearch) {
					$.ajax({
						url: getBaseUrl('students/loadStudentsByUnivYearAndLevel'),
						type: 'POST',
						data: {page: numPage, idUY, idLevel},
						success: function(data) {
							$('#table-subscribed tbody').html(data)
							$('li.page-item').removeClass('active')
							parent.addClass('active')
						},
						error: function() {
							alert('Erreur lors du chargement de la page')
						}
					})
				} else {
					$.ajax({
						url: getBaseUrl('students/searchSubscribed?criteria='+criteria+'&page='+numPage+'&idUY='+$('#choixUY').val()+'&idLevel='+$('#choixLevel').val()),
						success: function(data) {
							$('#table-subscribed tbody').html(data)
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
})

function addLevelsParcours(idLevel, parcoursSelector) {
	$.ajax({
		url: getBaseUrl('student/loadParcoursByLevel?id='+idLevel),
		success: function(data) {
			$(parcoursSelector).html(data)
			if(infosSubscription) {
				$(parcoursSelector).val(infosSubscription.parcours)
			}
			$(parcoursSelector).trigger('change')
		}, 
		error: function() {
			$(parcoursSelector).html("").trigger('change')
		}
	})
}

function addUEChoiceData(idParcours, wrapperSelector, infosChoix) {
	if(idParcours == undefined) {
		$(wrapperSelector).html('')
	} else {
		$.ajax({
			url: getBaseUrl('student/loadUEToChooseByParcours?id='+idParcours),
			success: function(data) {
				$(wrapperSelector).html(data)
				if(infosSubscription) {
					if(infosSubscription.choix != "") {
						var tmpInfos = infosSubscription.choix.split(';')
						for(var i = 0; i < tmpInfos.length; i++) {
							var tmp = tmpInfos[i].split('_')
							console.log(tmp)
							$('input[name='+tmp[0]+'][value='+tmp[1]+']').prop('checked', true).trigger('change')
						}
					}
				}
			}, 
			error: function() {
				$(wrapperSelector).html('')
			}
		})
	}
}

function showDetailsStudent(idStudent, lock = false) {
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
				$('#editStudentLabel').html(lock ? 'Infos sur l\'&eacute;tudiant' : 'Modifier &eacute;tudiant')
				$('#editStudentModal').modal('show')
				$('li a[href="#subscription"]').hide()
				$('li a[href="#primary"]').trigger('click')
				if(lock) {
					$('#form-save-student input, #form-save-student select').attr('disabled',"disabled");
					$('#save-student').hide();
				}
			} else {
				alert(data.message ? data.message : 'Echec de l\'action')
			}
		}, 
		error: function() {
			alert('Une erreur s\'est produite. Veuillez réessayer')
		}
	})
}

function clickZoomAndRotation(cropper){	
	$("#profile-zoom-in").on("click", function(){
		cropper.zoom(0.1)
	})
	
	$("#profile-zoom-out").on("click", function(){
		cropper.zoom(-0.1)
	})
	
	$("#profile-rotate-left").on("click", function(){
		cropper.rotate(-10)
	})
	
	$("#profile-rotate-right").on("click", function(){
		cropper.rotate(10)
	})
}