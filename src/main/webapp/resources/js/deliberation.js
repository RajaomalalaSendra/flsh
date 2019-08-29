var listStudents = []
$(document).ready(function() {
	$(function() {
		$('#choixLevelDelib').trigger('change')
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
		
		var j = listStudents.indexOf(idStudent)
		
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
				$('.ue-row').each(function(){
					var idUE = $(this).attr("id").split("-")[2]
					computeMoyenneUE(idUE, idStudent)
				})
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
	
	$(document).on('keyup', '.row-delib .input-ec-credit', function(e) {
		var maxCredit = parseInt($(this).attr("max-credit"))
		var credit = $(this).val()
		
		if(credit != "" ){
			if(parseInt(credit) > maxCredit){
				$(this).parent().find('.error-input-ec-credit').html('Credit est superieur au credit max.').show()
				$(this).addClass("hasError")
			} else if(parseInt(credit) < 0 || credit == "-"){
				$(this).parent().find('.error-input-ec-credit').html('Credit ne devrait pas etre negatif.').show()
				$(this).addClass("hasError")
			} else if(!parseInt(credit) && credit != 0){
				$(this).parent().find('.error-input-ec-credit').html('Credit ne devrait pas etre un alphabet.').show()
				$(this).addClass("hasError")
			} else if(isNaN(credit)){
				$(this).parent().find('.error-input-ec-credit').html('Credit ne devrait pas etre un alphabet.').show()
				$(this).addClass("hasError")
			} else {
				var idUE = $(this).attr("id").split("-")[2]
				computeSumCredit(idUE)
				
				$(this).parent().find('.error-input-ec-credit').hide()
				$(this).removeClass("hasError")
			}
		} else {
			$(this).removeClass("hasError")
			$(this).parent().find('.error-input-ec-credit').hide()
		}
	})
	
	$(document).on('blur', '.row-delib .input-ec-credit', function() {
		var creditEC = $(this).val()
		var idUE = $(this).attr("id").split("-")[2]
		var creditUE = $("#input-ue-" + idUE).val()
		var idEC = $(this).attr("id").split("-")[3]
		var idStudent = $("#choixElevesDelib").val()
		console.log(" Credit UE: ", creditUE)
		$.when(
				$.ajax({
					url: getBaseUrl("deliberation/save_credit"),
					type: 'POST',
					data: {idEC, idUE, creditEC, creditUE, idStudent},
					dataType: "JSON"
				})
			).then(function(data) {
				if(data.status == 1) {
					$("#input-ec-"+idUE+"-"+idEC).parent().find('.glyphicon-ok-sign').show().delay(3000).fadeOut(600)
				} else {
					$("#input-ec-"+idUE+"-"+idEC).parent().find('.glyphicon-warning-sign').show().delay(3000).fadeOut(600)
					$("#input-ec-"+idUE+"-"+idEC).parent().find('.error-input-ec-credit').html("echec  de l'enregistrement").show().delay(3000).fadeOut(600)
				}
			},
			function() {
				$("#input-ec-"+idUE+"-"+idEC).parent().find('.glyphicon-warning-sign').show().delay(3000).fadeOut(600)
				$("#input-ec-"+idUE+"-"+idEC).parent().find('.error-input-ec-credit').html("une erreur s'est produite lors du traitement").show().delay(3000).fadeOut(600)
			})
	})
	
	$(document).on('click','.ec-row', function() {
		$('.ec-row').removeClass('active')
		$(this).addClass('active')
	})
	
	$("body").on('click', function() {
		$('.ec-row').removeClass('active')
	})
	
	$(".deliberation-decision").on('click', function(){
		var idDecision = $(this).attr("id")
		if(idDecision == "renvoi-deliberation"){
			$("#renvoi-deliberation").addClass("btn-danger")
			$("#redouble-deliberation").removeClass("btn-warning")
			$("#passe-deliberation").removeClass("btn-success")
			
			passage = "RENVOI"
		} else if(idDecision == "redouble-deliberation"){
			$("#renvoi-deliberation").removeClass("btn-danger")
			$("#redouble-deliberation").addClass("btn-warning")
			$("#passe-deliberation").removeClass("btn-success")
			
			passage = "REDOUBLE"
		} else {
			$("#renvoi-deliberation").removeClass("btn-danger")
			$("#redouble-deliberation").removeClass("btn-warning")
			$("#passe-deliberation").addClass("btn-success")
			
			passage = "PASSE"
		}
		
		saveDecisionDeliberation(passage)
	})
	
})

function computeSumCredit(idUE){
	var sumCredit = 0
	$(".ecue-" + idUE).each(function(){
		sumCredit += +$(this).find(".input-ec-credit").val()
	 });
	$("#input-ue-" + idUE).val(sumCredit)
	computeSumAllCredit()
}

function computeSumAllCredit(){
	var sumAllCredit = 0
	$(".input-ue-credit").each(function(){
		sumAllCredit += +$(this).val()
	})
	$("#total-credit").html(sumAllCredit)
}

function computeMoyenneUE(idUE, idStudent){
	$(".period-exam").each(function(){
		var idPeriod = $(this).attr("id").split("-")[1]
		var typeSession = $(this).attr("id").split("-")[2]
		
		var moyenneUE = 0
		var sumCoefNotation = 0
		var sumCoefNote = 0
		
		$(".ecue-" + idUE).each(function(){
			var note = $.trim($(this).find(".note-ec-" + idPeriod + "-" + typeSession).html())
			
			/*if student is absent the note will be considered as 0*/
			if( note != ""){
				sumCoefNotation += (+$(this).find(".notation-ec").html() * +$(this).find(".coefficient-ec").html())
				if(note != "ABS" ) sumCoefNote += (+note * +$(this).find(".coefficient-ec").html())
			}
		})
		
		
		$("#tr-ue-" + idUE).on('click', function(){
			var note = $.trim($(this).find(".note-ec-" + idPeriod + "-" + typeSession).html())
			
			/*if student is absent the note will be considered as 0*/
			if( note != ""){
				sumCoefNotation += (+$(this).find(".notation-ec").html() * +$(this).find(".coefficient-ec").html())
				if(note != "ABS" ) sumCoefNote += (+note * +$(this).find(".coefficient-ec").html())
			}
		})
		
		if(sumCoefNotation != 0){
			moyenneUE = ((sumCoefNote / sumCoefNotation) * 20).toFixed(2)
			$("#tr-ue-" + idUE).find(".note-ue-" + idPeriod + "-" + typeSession).html(moyenneUE)
			
			$.ajax({
				url: getBaseUrl("deliberation/save_moyenneUE"),
				type: 'POST',
				data: {idStudent, idUE, idPeriod, moyenneUE, typeSession},
				dataType: 'JSON',
				success: function(data) {
			    	if(data.status == 1) {
			    		$("#success-note-ue").html(data.message).show().delay(3000).fadeOut(600)
					} else {
						$("#error-note-ue").html(data.message ? data.message : 'Note non enregistre').show().delay(3000).fadeOut(600)
					}
				}, 
				error: function(data) {
					$("#error-note-ue").html(data.message ? data.message : 'Une erreur interne s\'est produite! Veuillez reessayer...').show().delay(3000).fadeOut(600)
				}
			})
		}
		saveValidCredit("#danger-ue-", "#success-ue-", 1, idUE, idStudent)
		saveValidCredit("#success-ue-", "#danger-ue-", 0, idUE, idStudent)
	})
	computeSumAllCredit()
}

function saveValidCredit(firstValidCssID, secondValidCssID, valValid, idUE, idStudent){
	$(firstValidCssID + idUE).on('click', function(){
		$(this).hide()
		$(secondValidCssID + idUE).show()
		
		$.ajax({
			url: getBaseUrl("deliberation/save_validCredit"),
			type: 'POST',
			data: {idStudent, idUE, valValid},
			dataType: 'JSON',
			success: function(data){
				if(data.status == 1){
					console.log("success")
				} else {
					console.log("Error")
				}
			},
			error: function(err) {
				$(".ue-row").find(".error-save-valid-credit").html(err).show().delay(1000).fadeOut(300)
			}
		})
	})
}

function  saveDecisionDeliberation(passage){
	var idStudent = $('#choixElevesDelib').val()
	var idLevel = $('#choixLevelDelib').val()
	var idUnivYear = $("#IdUnivYear").val()
	var idPrc = $('#choixParcoursDelib').val()
	var i = listStudents.indexOf($("#choixElevesDelib").val())
	
	
	$.ajax({
		url: getBaseUrl("deliberation/save_delib_decision"),
		type: "POST",
		data: {idStudent, idLevel, idUnivYear, idPrc, passage},
		dataType: "JSON",
		success: function(data) {
			console.log(data.status)
		},
		error: function(err) {
			console.log(err)
		}
	})
}