/**
 * 
 */

$(function(){ /* DOM ready */
	$(".login-type").change(function(){
		
		if(this.checked){
			if(this.value==="student"){
				
				$("input.register").show();
			}else if(this.value==="driver"){
				
				$("input.register").hide(); 
			}
		} 
	});
});

