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
		
		if(credit != ""){
			if(parseInt(credit) > maxCredit){
				$(this).parent().find('.error-input-ue-credit').html('Credit est superieur au credit max.').show()
				$(this).addClass("hasError")
			} else if(parseInt(credit) < 0 || credit == "-"){
				$(this).parent().find('.error-input-ue-credit').html('Credit ne devrait pas etre negatif.').show()
				$(this).addClass("hasError")
			} else if(!parseInt(credit)){
				$(this).parent().find('.error-input-ue-credit').html('Credit ne devrait pas etre un alphabet.').show()
				$(this).addClass("hasError")
			}else {
				var idUE = $(this).attr("id").split("-")[2]
				computeSumCredit(idUE)
				
				$(this).parent().find('.error-input-ue-credit').hide()
				$(this).removeClass("hasError")
			}
		} else {
			$(this).removeClass("hasError")
			$(this).parent().find('.error-input-ue-credit').hide()
		}
	})
})

function computeSumCredit(idUE){
	var sumCredit = 0
	$(".ecue-" + idUE).each(function(){
		sumCredit += +$(this).find(".input-ec-credit").val()
		console.log("Somme credit ue ", sumCredit)
	 });
	$("#input-ue-" + idUE).val(sumCredit)
	computeSumAllCredit()
}

function computeSumAllCredit(){
	var sumAllCredit = 0
	$(".input-ue-credit").each(function(){
		sumAllCredit += +$(this).val()
		console.log("Somme credit total ", sumAllCredit)
	})
	$("#total-credit").html(sumAllCredit)
}

function computeMoyenneUE(idUE, idStudent){
	$(".period-exam").each(function(){
		var idPeriod = $(this).attr("id").split("-")[1]
		var typeSession = $(this).attr("id").split("-")[2]
		
		console.log("id Period: ", idPeriod, " type session: ", typeSession , " id UE: ", idUE)
		
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
			console.log("sum note ", sumCoefNote ," sum all note max ", sumCoefNotation)
		});
		
		
		if(sumCoefNotation != 0){
			moyenneUE = ((sumCoefNote / sumCoefNotation) * 20).toFixed(2)
			$("#tr-ue-" + idUE).find(".note-ue-" + idPeriod + "-" + typeSession).html(moyenneUE)
			
			$.ajax({
				url: getBaseUrl("deliberation/save_moyenneUE"),
				type: 'POST',
				data: {idStudent, idUE, idPeriod, moyenneUE, typeSession},
				success: function(data) {
					console.log("enregistrer")
				}, 
				error: function() {
					$("#tr-ue-" + idUE).find("#error-note-ue-" + idPeriod + "-" + typeSession).show()
				}
			})
		}
		
		console.log("test: ", moyenneUE)
	})
	
}