<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Prenotazione Manuale</title>

<!-- JQuery import -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

<!-- Custom style import -->
<link rel="stylesheet" type="text/css" href="css/studente.css">
<!-- Custom javascript import -->
<script type="text/javascript" src="js/bootstrapConverter.js"></script>


</head>
<body>
	<form name="modulo">
	 	<p>Matricola</p>
   		<p><input type="text" name="matricola" id="matricola"></p>
   		<p>Nome</p>
   		<p><input type="text" name="nome" id="nome"></p>
		<p>Cognome</p>
    	<p><input type="text" name="cognome" id="cognome"></p>
   		 <input type="button" id="invia" value="Invia i dati">
	</form>

	<div id="risultato"></div>
</body>
</html>