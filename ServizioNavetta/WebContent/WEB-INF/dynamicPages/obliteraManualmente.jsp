<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="ObliteraManualmente" method="post">
		<input type="text" id="matricola" name="current-matricola" />
		<button type="submit" value="Submit">Submit</button>
	</form>
	<%-- onclick="stud = new Studente('${stud.matricola}', '${stud.nome}', '${stud.cognome}');   mostraScuola(stud);" /> --%>
</body>
</html>
