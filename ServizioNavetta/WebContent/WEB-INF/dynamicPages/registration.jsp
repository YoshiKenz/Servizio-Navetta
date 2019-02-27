<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Servio Navetta - Registrazione</title>
<!-- JQuery import -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

<!-- Custom javascript import -->
<script type="text/javascript" src="js/bootstrapConverter.js"></script>

<!-- Custom style import -->
<link rel="stylesheet" type="text/css" href="css/common.css">
</head>
<body>
<form action="GET">
<h1>Servizio Navetta Unical : Registrati!</h1>
<div class="panel panel-registration">
					<div class="panel-body">
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input
								class="form-control" type="text" name="name"
								placeholder="Inserisci Nome"><br>
						</div>
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input
								class="form-control" type="text" name="surname"
								placeholder="Inserci Cognome"><br>
						</div>
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input
								class="form-control" type="text" name="regNumber"
								placeholder="Inserisci Matricola"><br>
						</div>
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span> <input
								class="form-control" type="password" name="password"
								placeholder="Inserisci password"><br>
						</div>
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span> <input
								class="form-control" type="password" name="confermedPassword"
								placeholder="Conferma password"><br>
						</div>
					</div>
				</div>
				<input class="btn btn-success" type="submit" value="Registrati!">
</form>
</body>
</html>