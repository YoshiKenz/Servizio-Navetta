$(function() { /* DOM ready */
	$(".biglietti").click(function() {
		var array = $(".biglietti");
		alert(array.length);
		for (var i = 0; i < array.length; i++) {
			// alert("for");
			if (array[i] != this) {
				// alert("uu");
				array[i].disabled = true;
				// $("#entrata").attr("disabled",
				// "disabled");
				// $("#entrata").removeAttr("disabled");
			}
		}
	});
});
