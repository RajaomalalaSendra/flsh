var notation = 20
var studentList = []
var examList = []
var lastValue = ""
var nbAfterComa = 2
$(document).ready(function() {
	
	$(function() {
		if($('#select-level-saisie').is(':visible')) {
			$('#select-level-saisie').trigger('change')
		} else if($('#select-ec').is(':visible')) {
			$('#select-ec').trigger('change')
		}
	})
	
	$('#start-saisie-prof').on('click', function() {
		var idUY = $('#select-uy-prof').val()
		var idProf = $('#select-prof').val()
		window.location.href = getBaseUrl('educations/notes?saisie_mode=prof&univYear='+idUY+'&prof='+idProf)
	})
	
	$('#start-saisie-niveau').on('click', function() {
		var idUY = $('#select-uy-level').val()
		var idLevel = $('#select-level').val()
		window.location.href = getBaseUrl('educations/notes?saisie_mode=level&univYear='+idUY+'&level='+idLevel)
	})
	
	$(document).on('change', '#select-level-saisie', function() {
		var idLevel = $(this).val()
		$.ajax({
			url: getBaseUrl('student/loadParcoursByLevel?id='+idLevel),
			success: function(data) {
				$("#select-prc").html(data).trigger('change')
			}, 
			error: function() {
				$("#select-prc").html("").trigger('change')
				$('#error-saisie').html('Echec du chargement des parcours. Veuillez actualiser puis r&eacute;essayer.').show().delay(3000).fadeOut(600)
			}
		})
	})
	
	$(document).on('change', '#select-prc', function() {
		var idPrc = $(this).val()
		$.ajax({
			url: getBaseUrl('educations/getEcParcours?idPrc='+idPrc),
			success: function(data) {
				$('#select-ec').html(data).trigger('change')
			},
			error: function() {
				$('#select-ec').html("").trigger('change')
				$('#error-saisie').html('Echec du chargement des ECs. Veuillez actualiser puis r&eacute;essayer.').show().delay(3000).fadeOut(600)
			}
		})
	})
	
	$(document).on('change', '#select-prof-saisie', function() {
		var idProf = $(this).val()
		$.ajax({
			url: getBaseUrl('educations/getEcProf?idProf='+idProf),
			success: function(data) {
				$('#select-ec').html(data).trigger('change')
			},
			error: function() {
				$('#error-saisie').html('Echec du chargement des ECs. Veuillez actualiser puis r&eacute;essayer.').show().delay(3000).fadeOut(600)
			}
		})
	})
	
	$(document).on('change', '#select-ec', function() {
		var idEc = $(this).val()
		var idUY = $('#uy-id').val()
		$.ajax({
			url: getBaseUrl('educations/getEcEvaluations?idEC='+idEc+'&idUY='+idUY),
			success: function(data) {
				$('#saisie-wrapper').html(data)
				notation = parseFloat($('#notation-ec').val())
				initVarList()
			},
			error: function() {
				$('#error-saisie').html('Echec du chargement des donn&eacute;es de saisie. Veuillez actualiser puis r&eacute;essayer.').show().delay(3000).fadeOut(600)
			}
		})
	})
	
	$(document).on('click','.row-student', function() {
		$('.row-student').removeClass('active')
		$(this).addClass('active')
	})
	
	$(document).on('keyup', '.row-student input', function(e) {
		console.log(e.keyCode)
		var message = validateNote($(this).val())
		var idExam = $(this).attr('id').split('-')[1]
		var idPer = $(this).attr('id').split('-')[2]
		var idStudent = $(this).attr('id').split('-')[3]
		switch(e.keyCode) {
			case 13: //Entrer
				if(message == "") {
					forward(idStudent, idPer, idExam)
				}
				break;
			case 38: //Haut
				if(message == "") {
					forward(idStudent, idPer, idExam, "prev")
				}
				break;
			case 40: //Bas
				if(message == "") {
					forward(idStudent, idPer, idExam)
				}
				break;
			default:
				if(message == "") {
					$(this).removeClass('hasError')
					$(this).parent().find('.glyphicon').hide()
					$('#error-saisie').hide()
				} else {
					$(this).addClass('hasError')
					$(this).parent().find('.glyphicon-warning-sign').show()
					$('#error-saisie').html(message).show()
				}
				break;
		}
	})
	
	$(document).on('focus', '.row-student input', function(e) {
		lastValue = $(this).val()
	})
	
	$(document).on('blur', '.row-student input', function(e) {
		var message = validateNote($(this).val())
		if(message != "") {
			$(this).val(lastValue)
			$(this).removeClass('hasError')
			$(this).parent().find('.glyphicon').hide()
			$('#error-saisie').hide()
		} else {
			$(this).val(formatValue($(this).val()))
			if(lastValue != $(this).val()) {//Save data to db
				var idExam = $(this).attr('id').split('-')[1]
				var idPer = $(this).attr('id').split('-')[2]
				var idStudent = $(this).attr('id').split('-')[3]
				var idEC = $('#select-ec').val()
				saveMoyStudent(idStudent, idExam, idPer, idEC, $(this).val())
			}
		}
	})
})
function saveMoyStudent(idStudent, idExam, idPer, idEC, noteVal) {
	$.when(
		$.ajax({
			url: getBaseUrl('educations/saveMoyStudent'),
			type: 'POST',
			data: {idStudent, idExam, idPer, idEC, noteVal},
			dataType: 'JSON'
		})
	).then(
		function(data) {
			console.log('success treatmnt', data)
			if(data.status == 1) {
				$("#notexam-"+idExam+"-"+idPer+"-"+idStudent).parent().find('.glyphicon-ok-sign').show().delay(3000).fadeOut(600)
			} else {
				$("#notexam-"+idExam+"-"+idPer+"-"+idStudent).parent().find('.glyphicon-warning-sign').show().delay(5000).fadeOut(600)
				$('#error-saisie').html(data.message ? data.message : "Echec de l'enregistrement").show().delay(5000).fadeOut(600)
			}
		},
		function() {
			console.log('there was error')
			$("#notexam-"+idExam+"-"+idPer+"-"+idStudent).parent().find('.glyphicon-warning-sign').show().delay(5000).fadeOut(600)
			$('#error-saisie').html("une erreur s'est produite lors du traitement").show().delay(5000).fadeOut(600)
		}
	)
}
function forward(idStudent, idPer, idExam, action = "next") {
	if(action == "prev") {
		if(studentList.indexOf(idStudent) != 0) {
			$('#notexam-'+idExam+'-'+idPer+'-'+studentList[studentList.indexOf(idStudent) - 1]).select()
			$("#studentsaisie-"+studentList[studentList.indexOf(idStudent) - 1]).trigger('click')
		} else {
			if(examList.indexOf(idExam+"-"+idPer) != 0) {
				var indexExam = examList.indexOf(idExam+"-"+idPer) - 1
				var indexStud = studentList.length - 1
				$('#notexam-'+examList[indexExam]+'-'+studentList[indexStud]).select()
				$("#studentsaisie-"+studentList[indexStud]).trigger('click')
			}
		}
	} else {
		if(studentList.indexOf(idStudent) != studentList.length - 1) {
			$('#notexam-'+idExam+'-'+idPer+'-'+studentList[studentList.indexOf(idStudent) + 1]).select()
			$("#studentsaisie-"+studentList[studentList.indexOf(idStudent) + 1]).trigger('click')
		} else {
			if(examList.indexOf(idExam+"-"+idPer) != examList.length -1 ) {
				var indexExam = examList.indexOf(idExam+"-"+idPer) + 1
				$('#notexam-'+examList[indexExam]+'-'+studentList[0]).select()
				$("#studentsaisie-"+studentList[0]).trigger('click')
			}
		}
	}
}
function formatValue(val) {
	if(val != "") {
		if(val.toUpperCase() == "ABS") {
			return val.toUpperCase();
		} else {
			return parseFloat(val).toFixed(nbAfterComa)
		}
	} else {
		return val
	}
}
function initVarList() {
	examList = []
	studentList = []
	$('th.exam').each(function() {
		examList.push($(this).attr('id'))
	})
	$('.row-student').each(function() {
		var idStudent = $(this).attr('id').split('-')[1]
		studentList.push(idStudent)
	})
}
function validateNote(val) {
	if(val.toUpperCase() == "ABS") {
		return "";
	} 
	if(val == "") {
		return "";
	}
	if(isNaN(parseFloat(val))) {
		return "Valeur incorrect";
	} else {
		var nb = parseFloat(val)
		if(val < 0) {
			return "Note négatif";
		} else if(val > notation) {
			return "Note supérieur à la notation";
		} else {
			return "";
		}
	}
}