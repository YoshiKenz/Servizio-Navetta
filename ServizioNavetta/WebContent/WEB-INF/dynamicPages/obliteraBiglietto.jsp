<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Oblitera Biglietto</title>

<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/webrtc-adapter/3.3.3/adapter.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.1.10/vue.min.js"></script>
<script type="text/javascript"
	src="https://rawgit.com/schmich/instascan-builds/master/instascan.min.js"></script>

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


<%@include file="importBootstrap.jsp" %>

<!-- Custom style import -->
<link rel="stylesheet" type="text/css" href="css/common.css">

<link rel="stylesheet" type="text/css" href="css/obliterazione.css">
</head>
<body>



			<video id="video"></video>
	
	

	<script type="text/javascript" src="js/reader.js"></script>

	<form action="obliteraManualmente" method="post">
		<input type="text" id="matricola" name="current-matricola" />
		<button type="submit" value="Submit">Obliterazione manuale</button>
	</form>
	<c:if test="${registration-error != null }">
		<c:set var="message" value="registration-error" />
		<span class="error-message"><c:out
				value="${sessionScope[message]}" /></span>
	</c:if>
	<form action="HomeSenzaErrori" method="post">
		<button type="submit" value="Indietro">Indietro</button>
	</form>
</body>
</html>