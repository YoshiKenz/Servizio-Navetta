<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<script src="js/loadStudent.js"></script>
<!-- Custom style import -->
<!-- <link rel="stylesheet" type="text/css" href="css/common.css"> -->

</head>
<body>
	<header>
		<h1>Iscrivi un nuovo studente</h1>
		<h2>Compila il seguente form</h2>
		<form id="modulo1" name="myForm" class="form-horizontal" method="post"
			action=inviaDatiStudente>
			<div class="form-group">
				<label class="control-label col-sm-2" for="matricola">Matricola</label>
				<div class="col-sm-5">
					<input class="form-control" name="matricola" type="text" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="nome">Nome</label>
				<div class="col-sm-5">
					<input class="form-control" name="nome" type="text" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="cognome">cognome</label>
				<div class="col-sm-5">
					<input class="form-control" name="cognome" type="text" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="email">email</label>
				<div class="col-sm-5">
					<input class="form-control" name="email" type="email" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="password">Password</label>
				<div class="col-sm-5">
					<input class="form-control" name="password" type="password" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="password2">Conferma
					Password</label>
				<div class="col-sm-5">
					<input class="form-control" name="password2" type="password" />
				</div>
			</div>

			<input id="reset" class="btn btn-warning" type="reset" />
		</form>
		<form action="DopoIscrizioneSenzaErrori" method="post">
			<button type="submit" value="Indietro">Indietro</button>
		</form>
		<c:if test="${registration-error != null }">
			<c:set var="message" value="registration-error" />
			<span class="error-message"><c:out
					value="${sessionScope[message]}" /></span>
		</c:if>





	</header>
</body>
</html>