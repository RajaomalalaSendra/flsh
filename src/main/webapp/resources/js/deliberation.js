$(document).ready(function() {
	$(function() {
		$('#choixLevelDelib').trigger('change');
		$('#choixParcoursDelib').trigger('change');
		$('#choixElevesDelib').trigger('change');
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
			}, 
			error: function() {
				$("#choixElevesDelib").html("There is error").trigger('change')
			}
		})
		
	})
	
	$('#choixElevesDelib').on('change', function(){
		$('thead').html('loading...')
		var idStudent = $(this).val()
		var idLevel = $('#choixLevelDelib').val()
		var idUnivYear = $("#IdUnivYear").val()
	
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
			url: getBaseUrl("deliberation/periods_list?idLevel=" + idLevel),
			success: function(data) {
				$("#changeFromPeriod").html(data).trigger('change')
			}, 
			error: function() {
				$("#changeFromPeriod").html("There is an error").trigger('change')
			}
		})
		
	})
	
	$('#changeFromPeriod').on('change', function(){
		var valPeriod = $(this).html();
		var idUnivYear = $("#IdUnivYear").val()
		var idStudent = $("#choixElevesDelib").val()
		var valPeriodByQuote = valPeriod.split("\"")
		// To get all the two periods'id
		var idPeriodOne = valPeriodByQuote[3]
		var idPeriodTwo = valPeriodByQuote[7]
		
		$.ajax({
			url: getBaseUrl("/deliberation/deliberation_list?univYear=" + idUnivYear + 
					"&idStudent=" + idStudent + "&idPeriodOne="+ idPeriodOne + "&idPeriodTwo="
					+ idPeriodTwo),
			success: function(data) {
				$("tbody").html(data).trigger('change')
			}, 
			error: function() {
				$("tbody").html("There is an error").trigger('change')
			}
		})
		
	})
	
})
