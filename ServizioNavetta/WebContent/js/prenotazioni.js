function obliteraEntrataUscita(ID) {
	alert("cc");
	var prenF = {
		id : ID,
	};
	$.ajax({
		type : "POST",
		url : "obliteraEntrataManualmente",
		datatype : "json",
		data : JSON.stringify(prenF),
		success : function(data) {
			var x = JSON.parse(data);
			alert("cc");
			alert(x.x);
			if (x.x == 0)
				document.getElementById("entrata").disabled = true;
			else
				document.getElementById("uscita").disabled = true;

			// document.getElementsByClassName("entrata")[0].disabled = true;
			// document.getElementsByTagName("input")[0].disabled = true;
			// console.log($('body').html());
			// $("#entrata").attr("disabled", "disabled");
			// $("#entrata").removeAttr("disabled");
		}
	});
	// $(function() {
	// $(".biglietti-pren").click(function() {
	// alert("entra");
	// var array = $(".biglietti-pren");
	// alert(array.length);
	// });
	// });
	// alert("uscito");
	// if (x === true) {
	// alert(x);
	// alert("if");
	// }
}

// $(document).ready(function() {
// $('#entrata').onclick()
// {
// console.log("prova");
// // // alert(ID);
// // var x = false;
// // // alert(x);
// // var prenF = {
// // id : ID,
// // };
// // $.ajax({
// // type : "POST",
// // url : "obliteraEntrataManualmente",
// // datatype : "json",
// // data : JSON.stringify(prenF),
// // success : function(data) {
// // // alert("ss");
// // // alert("ssss");
// // console.log($('body').html());
// // // $('#entrata').attr("pallino");
// // // var element = $("#entrata");
// // alert("pal");
// // // prova(x);
// // x = true;
// // // element.attr("disabled", "disabled");
// // // element.removeAttr("disabled");
// // }
// // });
// // alert("uscito");
// // if (x === true) {
// // alert(x);
// // alert("if");
// // }
// }
// });
function loadStudent() {
	alert("cio");
	var stud = {
		matricola : studente.matricola,
		nome : studente.nome,
		cognome : studente.cognome
	};
	$.ajax({
		type : "POST",
		url : "dettagliScuola",
		datatype : "json",
		data : JSON.stringify(stud),
		success : function(data) {
			var scuola = JSON.parse(data);
			$("#dettagliScuola").text(scuola.nome);
		}
	});
}