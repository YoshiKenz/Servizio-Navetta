<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Feedback</title>
<%@include file="importBootstrap.jsp" %>
<style type="text/css" media="screen">
</style>
<script>
	$(window).on('load', function() {
		alert("carica");
		$("#comment").hide();
		$("#feedback").show();
	});
	function updateFeedback(text) {
		alert(text);
		var element = document.getElementById("qrcode");
		document.getElementById("codicePren").value = text;
		alert(document.getElementById("codicePren").value);
		
		
		$("#feedback").hide();
		$("#comment").show();
	}
	function indietro() {
		document.getElementById("codicePren").value = null;
		alert(document.getElementById("codicePren").value);
		$("#comment").hide();
		$("#feedback").show();
	}
</script>
</head>
<body>
	<div id=feedback>
		<span class="error-message"> ${message-error}</span>
		<table class="table">
			<tr>
				<th>Prenotazione</th>
				<th>Autista</th>
				<th>Giro</th>
				<th>Navetta</th>
				<th>Partenza</th>
				<th>Arrivo</th>
			</tr>
			<c:forEach items="${prenotazione}" var="pren" varStatus="i">
				<tr>
					<td>${pren.ID}</td>
					<td>${pren.autista.ID}</td>
					<td>${pren.giro}</td>
					<td>${pren.navetta.ID}</td>
					<td>${pren.tratto.partenza.nome}</td>
					<td>${pren.tratto.arrivo.nome}</td>
					<td><input type="button" value="Lascia Feedback"
						onclick=" updateFeedback( ${pren.ID} )" /></td>

				</tr>

			</c:forEach>
		</table>
	</div>
	<div class="form-horizontal" id=comment style="display: none;">
		<form action="lasciaFeedback" method="post">
			<div class="form-group" id=prenotazioneID>
				<input id="codicePren" name="preno" type="text" style="display: none;" />
			</div>
			
			<textarea name="commento" rows="15" cols="50"></textarea>
			
			<br> <br> 
			<input class=myButton type="submit"	value="Invia Feedback" name="cico">
			
		</form>
		<button id="bottone" type="button" onclick="indietro()">Indietro</button>
	</div>
</body>
</html>