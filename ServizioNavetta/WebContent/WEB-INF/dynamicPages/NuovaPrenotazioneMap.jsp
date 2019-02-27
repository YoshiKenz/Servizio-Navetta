<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nuova Prenotazione - ${username}</title>
<%@ include file="leafletImport.jsp"%>

<!-- Custom javascript import -->
<script type="text/javascript" src="js/bootstrapConverter.js"></script>
<script type="text/javascript" src="js/nuovaPrenotazione.js"></script>

<!-- Custom style import -->
<link rel="stylesheet" type="text/css" href="css/nuovaPrenotazione.css">
<link rel="stylesheet" type="text/css" href="css/common.css">



</head>
<body>

	<%@ include file="studNavbar.jsp"%>

	<div class="mycol-1"></div>
	<div class="col-sx mycol-4">

		<div class="asynch-form panel-group">
			<div id="start-panel" class="panel panel-primary selectable-panel">
				<div class="panel-heading">Partenza :</div>
				<div class="panel-body">
					<label class="checkbox-inline"> <input id="start-geoloc"
						class="start" type="radio" name="start" value="geoloc" checked>
						<span>Posizione Attuale</span>
					</label> <label class="checkbox-inline"> <input id="start-map"
						class="start" type="radio" name="start" value="from-map">
						<span>Seleziona sulla mappa</span>
					</label>
				</div>
			</div>
			<div id="stop-panel" class="panel panel-success">
				<div class="panel-heading">Arrivo :</div>
				<div class="panel-body">
					<label class="checkbox-inline"> <input id="stop-geoloc"
						class="stop" type="radio" name="stop" value="geoloc"> <span>Posizione
							Attuale</span>
					</label> <label class="checkbox-inline"> <input id="stop-map"
						class="stop" type="radio" name="stop" value="from-map" checked>
						<span>Seleziona sulla mappa</span>
					</label>
				</div>
			</div>
			<button id="compute-routes" class="btn btn-info" disabled>Calcola
				percorsi</button>

			<span id="error-space" class="row"> </span>
		</div>
		
		<form action="creaPrenotazione" method="post">
			<input id="go-booking" type="submit" value="Prenota" hidden="hidden">
			<input id="selected-line" type="text" name="tratto-array"
				hidden="hidden"></input>
		</form>
	</div>
	<div class="mycol-1"></div>
	<div id="map-container" class="col-dx mycol-4">
		<div id="map"></div>
	</div>

</body>
</html>