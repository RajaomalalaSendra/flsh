var listStudents = []
$(document).ready(function() {
	$(function() {
		$('#choixLevelDelib').trigger('change');
	})
	
	
	$(document).on('click','#start-deliberation', function() {
		$(this).attr('href', getBaseUrl('deliberation?univYear='+$('#select-uy').val()))
	})
	
	$('#choixLevelDelib').on('change', function() {
		var idLevel = $(this).val()
		$.ajax({
			url: getBaseUrl('student/loadParcoursByLevel?id='+idLevel),
			success: function(data) {
				$('#choixParcoursDelib').html(data).trigger('change')
			}, 
			error: function() {
				$('#choixParcoursDelib').html("There is error").trigger('change')
			}
		})
	
	})
	
	$('#choixParcoursDelib').on('change', function(){
		var idParcours = $(this).val()
		var idUnivYear = $("#IdUnivYear").val()
		$.ajax({
			url: getBaseUrl('deliberation/students_list?univYear='+ idUnivYear + '&idParcours=' + idParcours),
			success: function(data) {
				$("#choixElevesDelib").html(data).trigger('change')		
				listStudents = []
				$("#choixElevesDelib option").each(function() {
					listStudents.push($(this).attr('value'))
				})
			}, 
			error: function() {
				$("#choixElevesDelib").html("There is error").trigger('change')
			}
		})
		
	})
	
	$('#choixElevesDelib').on('change', function(){
		var idStudent = $(this).val()
		var idLevel = $('#choixLevelDelib').val()
		var idUnivYear = $("#IdUnivYear").val()
		var idPrc = $('#choixParcoursDelib').val()
	
		$.ajax({
			url: getBaseUrl("deliberation/student_detail?idStudent=" + idStudent),
			success: function(data) {
				$("#Detail-Student-Deliberation").html(data)
			}, 
			error: function() {
				$("#Detail-Student-Deliberation").html("There is an error")
			}
		})
		
		$.ajax({
			url: getBaseUrl("deliberation/getEvaluationData"),
			type: 'POST',
			data: {idUnivYear, idLevel, idStudent, idPrc},
			success: function(data) {
				$('#info-evaluation thead .head-period').remove()
				$("#info-evaluation tbody").html(data)
				$('.head-period').appendTo('#info-evaluation thead tr')
			}, 
			error: function() {
				$("#info-evaluation tbody").html("There is an error")
			}
		})
		
	})
	
	$("#delib-eleve-suivant").on('click', function(e){
		e.preventDefault()
		var i = listStudents.indexOf($("#choixElevesDelib").val())
		if(listStudents[i+1]) {
			$("#choixElevesDelib").val(listStudents[i+1]).trigger('change')
		}
	})
	
	$("#delib-eleve-precedent").on('click', function(e){
		e.preventDefault()
		var i = listStudents.indexOf($("#choixElevesDelib").val())
		if(listStudents[i-1]) {
			$("#choixElevesDelib").val(listStudents[i-1]).trigger('change')
		}
	})
	
	$(document).on('keyup', '.row-delib input', function(e) {
		console.log(e.keyCode)
		var message = validateNote()
		if(message == "") {
			console.log("It is a number conform")
		} else {
			console.log("It is a number not conform")
		}
	})
	
})


function validateNote(val) {

	if(val == "") {
		return "";
	}
	if(isNaN(parseFloat(val))) {
		return "Valeur incorrect";
	} else {
		var nb = parseFloat(val)
		if(val < 0) {
			return "Credit négatif";
		} else if(val > notation) {
			return "Crédit obténu supérieur au Crédit(max)";
		} else {
			return "";
		}
	}
}
