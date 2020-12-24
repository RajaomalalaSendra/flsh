$(document).ready(function() {
	
	$('.ue-row').each(function(){	
		var idUE = $(this).attr("id").split("-")[2]
		
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
			}
		})
		computeSumAllCredit()
		computeSumAllCreditObtain()
	})
})

function computeSumAllCredit(){
	var sumAllCredit = 0;
	$(".period-exam").each(function(){
		var idPeriod = $(this).attr("id").split("-")[1]
		var index = $(this).attr("id").split("-")[2]
		var sumAllCreditPeriod = 0
		$("tr td.input-ue-credit.credit-"+idPeriod+"-"+index).each(function(){
			sumAllCreditPeriod += +$(this).html()
		})
		$("#total-credit-"+idPeriod+"-"+index).html(sumAllCreditPeriod)
		sumAllCredit += sumAllCreditPeriod
	})
	$("#summ-credit-obtain").html(sumAllCredit)
}