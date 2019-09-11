$(document).ready(function() {
	
	$(document).on('click','#start-deliberation', function() {
		$(this).attr('href', getBaseUrl('deliberation?univYear='+$('#select-uy').val()))
	})
})