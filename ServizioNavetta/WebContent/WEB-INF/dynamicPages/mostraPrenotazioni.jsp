<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src="js/prenotazioni.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
	$(window).on('load', function() {
		alert(document.getElementById("pal").value);
		var y = document.getElementById("pal").value;
		var x = {
			id : y
		};
		$.ajax({
			type : "POST",
			url : "controlloObliterazione",
			datatype : "json",
			data : JSON.stringify(x),
			success : function(data) {
				var coppia = JSON.parse(data);
				alert(coppia.x);
				alert("cxc");
				if (coppia.x == 1)
					document.getElementById("entrata").disabled = true;
				if (coppia.y == 1)
					document.getElementById("uscita").disabled = true;
				//document.getElementById("entrata").disabled = true;

				// document.getElementsByClassName("entrata")[0].disabled = true;
				// document.getElementsByTagName("input")[0].disabled = true;
				// console.log($('body').html());
				// $("#entrata").attr("disabled", "disabled");
				// $("#entrata").removeAttr("disabled");
			}
		});
	});
</script>
</head>
<body>
	<input id=pal type="text" name="fname" value="${prenotazione.ID}"
		style="display: none">
	<table>
		<tr>
			<th>Prenotazione</th>
			<th>Prenotazione</th>
			<th>Autista</th>
			<th>Giro</th>
			<th>Navetta</th>
			<th>Partenza</th>
			<th>Arrivo</th>
		</tr>
		<tr>
			<td>${prenotazione.ID}</td>
			<td>${prenotazione.ID}</td>
			<td>${prenotazione.giro}</td>
			<td>${prenotazione.navetta.ID}</td>
			<td>${prenotazione.tratto.partenza.nome}</td>
			<td>${prenotazione.tratto.arrivo.nome}</td>
			<td>${prenotazione.tratto.arrivo.nome}</td>
			<td><input id=entrata class="biglietti-pren"
				value="Oblitera Entrata" type="button"
				onclick="obliteraEntrataUscita('${prenotazioneID}');" /></td>
			<td><input id=uscita value="Oblitera Uscita" type="button"
				onclick="obliteraEntrataUscita('${prenotazioneID}');" /></td>
		</tr>
	</table>
	<%-- <h1>${prenotazioni.giro}</h1> --%>
</body>
</html>