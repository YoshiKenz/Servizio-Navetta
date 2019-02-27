function validateForm() {
	var x = document.forms["myForm"]["matricola"].value;
	if (x == "") {
		alert("Name must be filled out");
		return false;
	}
}

$(function() { /* DOM ready */
	$(".form-control")
			.blur(
					function() {
						var x = this.value;
						$(this).next(".error-message").remove();
						if (x === "") {
							$(
									"<span class=\"error-message\">Non puo essere vuoto </span>")
									.insertAfter(this);
							$("#submit").remove();
						} else {
							controllaForm();
						}
					});
});
function controllaForm() {
	var array = $(".form-control");
	var tuttoCompilato = true;
	for (var i = 0; i < array.length; i++) {
		if (array[i].value === "") {
			tuttoCompilato = false;
			break;
		}
	}
	if (tuttoCompilato) {
		var element = $("#submit");
		// alert(element[0]);
		if (element[0] == undefined)
			$(
					"<input id=\"submit\" class=\"btn btn-success\" type=\"submit\" />")
					.insertBefore("#reset");
	}
}

