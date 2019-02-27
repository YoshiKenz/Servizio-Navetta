<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pick Bus - ${username}</title>
<%@include file="importBootstrap.jsp"%>
<link rel="stylesheet" type="text/css" href="css/common.css">
<link rel="stylesheet" type="text/css" href="css/bus.css">
<script type="text/javascript" src="js/bootstrapConverter.js"></script>
</head>
<body>

	<%@include file="studNavbar.jsp"%>

	<h1>Seleziona le navette che vuoi prenotare per raggiungere
		l&#39;Arrivo</h1>

	<div class="myCol-3"></div>
	<div class="myCol-6">
		<form action="booking" method="get">
			<div class="panel-group">
				<!-- Iteration using an index, cause it's needed to iterate contemporary bus collection -->
				<c:set var="cost" value="${1}" />
				<c:set var="dim" value="${ fn:length(tratti)}" />
				<c:set var="sum" value="${dim-cost}" scope="request" />

				<c:forEach var="i" begin="0" end="${sum}">
					<div class="panel panel-success">
						<c:set var="tratto" value="${tratti[i]}" />
						<div class="panel-heading">
						<label>From <span class="fermata">${tratto.partenza.nome}</span> to
							<span class="fermata">${tratto.arrivo.nome}</span></label>
						</div>
						<c:set var="navette" value="${ listeNavette[i]}" />
						<c:set var="navNum" value="${fn:length(navette)}" />
						
						<input type="text" name="start-${i}" value="${tratto.partenza.nome}" style="display:none;">
						<input type="text" name="stop-${i}" value="${tratto.arrivo.nome}" style="display:none;">
						
						<div class="row panel-body">
							<c:if test="${navNum!=0 }">
								<select class="in-body" name="tratto-${i}">
									<c:forEach var="navetta" items="${ navette}">
										<option>${navetta.ID}</option>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${navNum==0 }">
								<label class="in-body">Nessuna navetta attiva</label>
							</c:if>
						</div>
					</div>
				</c:forEach>
			</div>
			<div class="row">
				<div class="mycol-1">
				<input id="prenota" class="btn btn-info" type="submit" value="Prenota">
				</div>
			</div>
			<input type="text" name="dimension" value="${dim}" hidden="hidden"/>
		</form>
	</div>
</body>
</html>