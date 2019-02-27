<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Servizio Navetta - Driver Homepage</title>

<%@ include file="leafletImport.jsp" %> 
<!-- Custom style import -->
<link rel="stylesheet" type="text/css" href="css/driver.css">
<!-- Custom javascript import -->
<script type="text/javascript" src="js/bootstrapConverter.js"></script>

<script type="text/javascript" src="js/driverMap.js"></script>




</head>
<body>
	<%@ include file="driverNavbar.jsp" %> 
	<div class="myCol-1"></div>
	<div class="myCol-10">
		<div id="next-stop" class="row">
			<span>Prossima fermarta : </span> <span id="next-stop-value">Fermata.Nome</span>
		</div>
		<div class="row">
			
			<div id="map" class="myCol-12">
				<!-- Temporary --
				
					<img
						src="https://snazzy-maps-cdn.azureedge.net/assets/127403-no-label-bright-colors.png?v=20171101110035">
				
				-- Temporary -->
				<div id="mapid">
				</div>
			</div>
		</div>
	</div>
</body>
</html>