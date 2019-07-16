$(document).ready(function() {
	
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
})