(function ($) {
 "use strict";

	/*----------------------------
	 jQuery MeanMenu
	------------------------------ */
	jQuery('nav#dropdown').meanmenu();	
	/*----------------------------
	 jQuery myTab
	------------------------------ */
	$('#myTab a').on('click', function (e) {
		  e.preventDefault()
		  $(this).tab('show')
		});
		$('#myTab3 a').on('click', function (e) {
		  e.preventDefault()
		  $(this).tab('show')
		});
		$('#myTab4 a').on('click', function (e) {
		  e.preventDefault()
		  $(this).tab('show')
		});
		$('#myTabedu1 a').on('click', function (e) {
		  e.preventDefault()
		  $(this).tab('show')
		});

	  $('#single-product-tab a').on('click', function (e) {
		  e.preventDefault()
		  $(this).tab('show')
		});
	
	$('[data-toggle="tooltip"]').tooltip(); 
	
	$('#sidebarCollapse').on('click', function () {
		 $('#sidebar').toggleClass('active');
	 });
	// Collapse ibox function
	$('#sidebar ul li').on('click', function () {
		var button = $(this).find('i.fa.indicator-mn');
		button.toggleClass('fa-plus').toggleClass('fa-minus');
		
	});
	/*-----------------------------
		Menu Stick
	---------------------------------*/
	$(".sicker-menu").sticky({topSpacing:0});
		
	$('#sidebarCollapse').on('click', function () {
		$("body").toggleClass("mini-navbar");
		if(SmoothlyMenu) SmoothlyMenu();
	});
	$(document).on('click', '.header-right-menu .dropdown-menu', function (e) {
		  e.stopPropagation();
	});
	/*----------------------------
	 wow js active
	------------------------------ */
	 new WOW().init();
	/*----------------------------
	 owl active
	------------------------------ */  
	$("#owl-demo").owlCarousel({
      autoPlay: false, 
	  slideSpeed:2000,
	  pagination:false,
	  navigation:true,	  
      items : 4,
	  /* transitionStyle : "fade", */    /* [This code for animation ] */
	  navigationText:["<i class='fa fa-angle-left'></i>","<i class='fa fa-angle-right'></i>"],
      itemsDesktop : [1199,4],
	  itemsDesktopSmall : [980,3],
	  itemsTablet: [768,2],
	  itemsMobile : [479,1],
	});
	/*----------------------------
	 price-slider active
	------------------------------ */  
	  $( "#slider-range" ).slider({
	   range: true,
	   min: 40,
	   max: 600,
	   values: [ 60, 570 ],
	   slide: function( event, ui ) {
		$( "#amount" ).val( "£" + ui.values[ 0 ] + " - £" + ui.values[ 1 ] );
	   }
	  });
	  $( "#amount" ).val( "£" + $( "#slider-range" ).slider( "values", 0 ) +
	   " - £" + $( "#slider-range" ).slider( "values", 1 ) );
	/*--------------------------
	 scrollUp
	---------------------------- */	
	$.scrollUp({
        scrollText: '<i class="fa fa-angle-up"></i>',
        easingType: 'linear',
        scrollSpeed: 900,
        animation: 'fade'
    }); 	
	
	$(document).on('click', '.glyphicon.glyphicon-search', function() {
		$(this).parent().parent().find('input').focus()
	})
	
	$(document).on('dblclick', '.glyphicon.glyphicon-search', function() {
		console.log( "Handler for .dblclick() called." );
		var e = $.Event( "keyup", { which: 13 } );
		$(this).parent().parent().find('input').trigger(e)
	})
 
})(jQuery); 

function formValidate(selector) {
    var form = $(selector)
    var formValide = true
    form.find('input, textarea').each(function() {
        var input = $(this)
        if(input.attr('type') == "text" || input.attr('type') == "date" || !input.attr('type')) {
            if(input.attr('required') && $.trim(input.val()) == "") {
                input.addClass('hasError')
                input.parent().find('small.error').html('Champ requis').show()
                formValide = false
            } else {
                input.removeClass('hasError')
                input.parent().find('small.error').hide()
            }

            if(input.attr('validation-type') && $.trim(input.val()) != "") {//Add here if there is exception of validation
                if(input.attr('validation-type') == "phone-number") {
                    if(!input.val().match(/^\+\d{12}$/) && !input.val().match(/^0\d{9}$/)) {
                        input.addClass('hasError')
                        input.parent().find('small.error').html('Numéro de téléphone incorrect').show()
                        formValide = false
                    }
                } else if(input.attr('validation-type') == "cin") {
                    if(!input.val().match(/^[1-9]\d{11}$/)) {
                        input.addClass('hasError')
                        input.parent().find('small.error').html('Numéro CIN incorrect').show()
                        formValide = false
                    }
                }
            }
        } else if(input.attr('type') == "email") {
            if(input.attr('required') && $.trim(input.val()) == "") {
                input.addClass('hasError')
                input.parent().find('small.error').html('Champ requis').show()
                formValide = false
            } else if(!$.trim(input.val()).match(/\S+@\S+\.\S+/)) {
                input.addClass('hasError')
                input.parent().find('small.error').html('Email invalide').show()
                formValide = false
            } else {
                input.removeClass('hasError')
                input.parent().find('small.error').hide()
            }
        } else if(input.attr('type') == "number") {
            if(input.attr('required') && $.trim(input.val()) == "") {
                input.addClass('hasError')
                input.parent().find('small.error').html('Champ requis').show()
                formValide = false
            } else if($.trim(input.val()) != "" && !$.trim(input.val()).match(/\d+\.?\d*/)) {
                input.addClass('hasError')
                input.parent().find('small.error').html('Valeur incorrect').show()
                formValide = false
            } else {
                input.removeClass('hasError')
                input.parent().find('small.error').hide()
            }
        } else if(input.attr('type') == "password" && input.is(':visible')) {
            var strongPassRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})/
            if($.trim(input.val()) == "" && input.attr('required')) {
                input.addClass('hasError')
                input.parent().find('small.error').html('Champ requis').show()
                formValide = false
            }
            console.log(input.hasClass('no-check'))
            if($.trim(input.val()) != "" && !$.trim(input.val()).match(strongPassRegex) && !input.hasClass('no-check')) {
                input.addClass('hasError')
                input.parent().find('small.error').html('Un mot de passe doit être composé de lettres minuscules, majuscules, au moins un chiffre et de longueur 8 caractères minimum').show()
                formValide = false
            } else if(($.trim(input.val()) == "" && !input.attr('required')) || $.trim(input.val()).match(strongPassRegex) || input.hasClass('no-check')) {
                input.removeClass('hasError')
                input.parent().find('small.error').hide()
            }
        }
    })
    return formValide
}

function emptyForm(selector) {
	var form = $(selector)
	form.find('input, textarea').each(function() {
		var input = $(this)
		input.removeClass('hasError').val('')
    input.parent().find('small.error').hide()
	})
}

function getBaseUrl(path) {
	return $('#base-url').val()+''+path
}

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}