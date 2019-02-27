<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script
	src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<script>
	$(document).ready(function() {
		console.log("document loaded");
		alert("ss");
	});

	$(window).on("load", function() {
		console.log("window loaded");
	});
</script>
</head>
<body>

	<h1>Hello World!</h1>

	<!-- <script>
		function myFunction() {
			alert("Page is loaded");
		}
	</script> -->
</html>