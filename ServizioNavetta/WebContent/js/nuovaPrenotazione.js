/**
 * 
 *Author : Alessio Portaro
 *Date : Mer 30 Gen 2019 00:55:51 CET
 * 
 */

var myToken = 'pk.eyJ1IjoibWltbW9mbG93IiwiYSI6ImNqcDZ4eGF4ZTFlazQzdmxrb3UwYXV2MnAifQ.60aM8oR1UjGXezjzNZkacw';
var mapAttribution = 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>';
var LAT = 0.0;
var LNG = 0.0;
var customIcon;
var redIcon;
var mymap;
var tileLayer;
var nearStart = [];
var nearStop = [];
var fermataPartenza = undefined;/* new Fermata();*/
var fermataArrivo = undefined;/* new Fermata();*/
var lastElClicked = "start";
var selectedLine;

/*TEST*/
var viewLAT;
var viewLNG;

/*DEBUG*/
var someMarker;
var leafRouteDeb;
var objLine;
var buttonDeb;

var lines;

$(function(){ /* DOM ready */

	initCustomMarker();
	updatePosition("start");
    
//    $("#go-booking").click(function(){
//        alert("click");
//        $.ajax({
//            data : {
//                "state" : "computeBus",
//                "tratto-array" : JSON.stringify(selectedLine)
//            },
//            success : function(){
//                console.log("Unreachable point");
//            }
//        });
//    });


	$("#start-geoloc").click(function(ev){
		graphicClick("start");
		//ev.preventDefault();
		//ev.stopPropagation();
		lastElClicked ="start";
		this.checked = true;
		emptyArray(nearStart);
		/*STD*/
		updatePosition("start");
		checkDisabledButton();
	});/*ACTION LISTENER*/

	$("#start-map").click(function(ev){
		graphicClick("start");
		//ev.preventDefault();
		//ev.stopPropagation();
		lastElClicked ="start";
		this.checked = true;
		emptyArray(nearStart);
		/*STD*/
		updatePosition("start");
		checkDisabledButton();
	});/*ACTION LISTENER*/

	$("#stop-geoloc").click(function(ev){
		graphicClick("stop");
		//ev.preventDefault();
		//ev.stopPropagation();
		lastElClicked ="stop";
		this.checked = true;
		emptyArray(nearStop);
		/*STD*/
		updatePosition("stop");
		checkDisabledButton();

	});/*ACTION LISTENER*/

	$("#stop-map").click(function(ev){
		graphicClick("stop");
		//ev.preventDefault();
		//ev.stopPropagation();
		lastElClicked ="stop";
		this.checked = true;
		emptyArray(nearStop);
		/*STD*/
		updatePosition("stop");
		checkDisabledButton();
	});/*ACTION LISTENER*/

	$("#compute-routes").click(function(){
		var disabled = this.hasAttribute("disabled");
		if(disabled==false){
			$.ajax({type: "GET", url: "creaPrenotazione", 
				data : {
					"state" : "computeLine", 
					"start-point" : JSON.stringify(fermataPartenza), 
					"stop-point" : JSON.stringify(fermataArrivo)
				},
				success: function(data){
					createMap();
					/*add routes in map*/
					lines = JSON.parse(data);
					if(lines.length===0){
						($("#error-space")[0]).innerHTML="Nessuna linea trovata !";
						return;
					}
					if(mymap==undefined){
						alert("There isn't any map to add routes to !");
						return;
					}
					for(var i = 0 ; i < lines.length ; i++){
						var jsonRoute = lines[i];
						var wp = [];
						if(jsonRoute.length>0){
							wp.push(new L.latLng(jsonRoute[0].partenza.latitudine, jsonRoute[0].partenza.longitudine));
							for(var j = 0 ; j < jsonRoute.length ; j++){
								wp.push(new L.latLng(jsonRoute[j].arrivo.latitudine, jsonRoute[j].arrivo.longitudine));
							}
						}

						var leafRoute = new customControl(i,wp,myToken);
						leafRouteDeb = leafRoute;
						leafRoute.options.routeLine.goBackIndex = i;
						leafRoute.addTo(mymap);

					}
					/*VERY DANGEROUS (In every moment it could create
                        problems cause I don't know if the API needs it in for some reason)*/
					$(".leaflet-routing-container").remove();
				}	
			});/*AJAX CALL*/
		}
	});/*ACTION LISTENER*/
});/* DOM ready */

function updatePosition(whereAdd){
	navigator.geolocation.getCurrentPosition(function(position){
		LAT = position.coords.latitude;
		LNG = position.coords.longitude;
		createMap();
		markCurrentPosition();
		if(whereAdd==="start"){
			fermateNear(LAT,LNG,"start");
			if(fermataArrivo!=undefined)
				markStop(fermataArrivo,redIcon);
		}
		else if(whereAdd==="stop"){
			fermateNear(LAT,LNG,"stop");
			if(fermataPartenza!=undefined)
				markStop(fermataPartenza,redIcon);
		}
	});
}

/*This function makes an asynch call to the server and retrieves the N nearest stops to the point given as argument*/
function fermateNear(lat,lng,whereAdd){
	var state;
	if(whereAdd==="start"){
		state = "partenza";
		emptyArray(nearStart);
	}else if(whereAdd==="stop"){
		state = "arrivo";
		emptyArray(nearStop);
	}
	$.ajax({type: "GET", url: "creaPrenotazione", data : {"state" : state, "actual-lat" : lat, "actual-lng" : lng },
		success: function(data){
			var fermateVicine = JSON.parse(data);
			if(mymap!=undefined){
				for(var i=0;i<fermateVicine.length;i++){
					var tmp = new Fermata();
					tmp.nome = fermateVicine[i].nome;
					tmp.latitudine=fermateVicine[i].latitudine;
					tmp.longitudine=fermateVicine[i].longitudine;
					var marker;
					if((whereAdd==="start" && fermataArrivo!=undefined && fermataArrivo.nome===tmp.nome)||
							(whereAdd==="stop" && fermataPartenza!=undefined &&fermataPartenza.nome===tmp.nome))
						continue;
					else
						var marker = new L.Marker([tmp.latitudine, tmp.longitudine]/*, {icon : redIcon}*/).addTo(mymap);
					marker.options.title = tmp.nome;

					if(whereAdd==="start"){
						nearStart.push(tmp);
						//$(marker).click(function(){
						marker.on('click',function(){
							var tmp1 = Fermata.fakeConstr(this.options.title, this.getLatLng().lat,this.getLatLng().lng);
							fermataPartenza = tmp1;
							checkDisabledButton();
							createMap();
							if(fermataArrivo!=undefined){
								markStop(fermataArrivo,redIcon);
							}
							markStop(tmp1,redIcon);
						});
					}else if(whereAdd==="stop"){
						nearStop.push(tmp);
						marker.on('click',function(){
							var tmp1 = Fermata.fakeConstr(this.options.title, this.getLatLng().lat,this.getLatLng().lng);
							fermataArrivo = tmp1;
							checkDisabledButton();
							createMap();
							if(fermataPartenza!=undefined){
								markStop(fermataPartenza,redIcon);
							}
							markStop(tmp1,redIcon);
						});
					}
				}
			}
		}	
	});
}

/*This function removes every */
function emptyArray(array){
	while(array.length>0)
		array.pop();
}

function createMap(){

	var initLat = LAT;
	var initLng = LNG;
	if(mymap!=undefined){
		initLat = mymap.getCenter().lat;
		initLng = mymap.getCenter().lng;
	}
	deleteMap();
	mymap = new L.Map('map').setView([initLat,initLng],15);
	tileLayer = new L.TileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', 
			{
		attribution: mapAttribution,
		maxZoom: 18,
		id: 'mapbox.streets',
		/*acquisito su mapbox.com -> Token pubblico di default*/
		accessToken: myToken
			}).addTo(mymap);

	mymap.on('click',function(ev){
		var objLatLng = mymap.mouseEventToLatLng(ev.originalEvent);
//		alert("CLICK ON : LAT "+objLatLng.lat+" LNG "+objLatLng.lng);
		clickPoint = objLatLng;
		if(lines==undefined){
			handleMapClick(objLatLng);
		}
	}); 
}

function initCustomMarker(){
	customIcon = L.icon({
		iconUrl: 'img/bus.png',
		shadowUrl: 'img/marker.png',

		iconSize:     [45, 20], // size of the icon
		shadowSize:   [8, 8], // size of the shadow
		iconAnchor:   [20, 20], // point of the icon which will correspond to marker's location
		shadowAnchor: [1, 1],  // the same for the shadow
		popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
	});

	redIcon = L.icon({
		iconUrl: 'img/red-marker.png',
		shadowUrl: 'img/marker.png',

		iconSize:     [30, 45], // size of the icon
		shadowSize:   [7, 7], // size of the shadow
		iconAnchor:   [15, 45], // point of the icon which will correspond to marker's location
		shadowAnchor: [4, 4],  // the same for the shadow
		popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
	});
}

function markCurrentPosition(){
	if(mymap!=undefined)
		L.marker([LAT, LNG], {icon : customIcon}).addTo(mymap);
}

function areStopNStartSet(){
	if(fermataArrivo!=undefined && fermataPartenza!=undefined)
		return true;
	return false;
}

function checkDisabledButton(){
	if(areStopNStartSet()===true)
		$("#compute-routes").removeAttr("disabled");
}

function deleteMap(){
	//L.map('map').remOve();
	var mapEl = $("#map-container")[0];
	mapEl.innerHTML = "<div id=\"map\"></div>";
}

function handleMapClick(objLatLng){
	if(lastElClicked==="start"){
		var elCH = $("#start-map")[0];
		var condition = elCH.checked;
		if(condition===true){
			var tmp = new Fermata();
			tmp.nome="Custom start";
			tmp.latitudine = objLatLng.lat;
			tmp.longitudine = objLatLng.lng;
			createMap();
			markStop(tmp,customIcon);
			fermateNear(tmp.latitudine,tmp.longitudine,"start");
			if(fermataArrivo!=undefined)
				markStop(fermataArrivo,redIcon);
		}
	}else if(lastElClicked=="stop"){
		var elCH = $("#stop-map")[0];
		var condition = elCH.checked;
		if(condition===true){
			var tmp = new Fermata();
			tmp.nome="Custom stop";
			tmp.latitudine = objLatLng.lat;
			tmp.longitudine = objLatLng.lng;
			createMap();
			markStop(tmp,customIcon);
			fermateNear(tmp.latitudine,tmp.longitudine,"stop");
			if(fermataPartenza!=undefined)
				markStop(fermataPartenza,redIcon);
		}
	}
}

function markStop(fermata){
	if(mymap!=undefined && fermata!=undefined){
		var m = L.marker([fermata.latitudine, fermata.longitudine],{icon : customIcon});
		m.addTo(mymap);
		m.options.title = fermata.nome;
	}
}

function markStop(fermata,icon1){
	if(mymap!=undefined && fermata!=undefined){
		var m = new L.Marker([fermata.latitudine, fermata.longitudine]);
		m.addTo(mymap);
		m.options.title = fermata.nome;
		m.setIcon(icon1);
		someMarker = m;
	}
}

function graphicClick(where){
	if(where==="start"){

		$("#stop-panel").removeClass("selectable-panel selectable-panel-success selectable-panel-error");
		if(fermataPartenza!=undefined)
			$("#start-panel").addClass("selectable-panel-success");
		else
			$("#start-panel").addClass("selectable-panel-error");
	}else if(where==="stop"){
		$("#start-panel").removeClass("selectable-panel selectable-panel-success selectable-panel-error");
		if(fermataArrivo!=undefined)
			$("#stop-panel").addClass("selectable-panel-success");
		else
			$("#stop-panel").addClass("selectable-panel-error");
	}
}

class customControl extends L.Routing.Control{
	constructor(goBackIndex,wp,token){
		//this.goBackIndex = goBackIndex;
		//this.wp = wp;
		//this.token = token;
		super({
			waypoints : wp,
			routeWhileDragging : true,
			router : L.Routing.mapbox(token),
			/*createMarker : function() {return null;},We don't want any marker to be drawn*/
			routeLine: function(route) {
				var line = L.Routing.line(route, {
					addWaypoints: false,
					extendToWaypoints: false,
					routeWhileDragging: false,
					autoRoute: true,
					useZoomParameter: false,
					draggableWaypoints: false,
					addWaypoints: false
				});
				/*line.on('click', function(e) { console.log(e); });*/
				//line.goBackIndex = goBackIndex;
				line.eachLayer(function(l){
					l.index = goBackIndex;
					$(l).click(function(e){
						//alert("Click : "+this.index);
//                        selectedLine = lines[this.index];
//                        console.log(this);
                        //this.options.color = "blue";
                        //this.setColor("blue");
                        this.setStyle({color:"blue"});
                        //$("#booking").removeAttr("hidden");
                        
                        
                        var textInput = $("#selected-line")[0];
                        
                        $(textInput).val(JSON.stringify(lines[this.index]));
                        
                        var buttonComp = $("#go-booking")[0];
                        buttonDeb = buttonComp;
                        
                        $(buttonComp).removeAttr("hidden");
                        
                        $(buttonComp).addClass("btn btn-success");
                        
                        objLine = this;
					});
				});

				return line;
			}
		});
	}
}

class Fermata{
	constructor(){
		this.nome = "";
		this.latitudine=0.0;
		this.longitudine=0.0;
	}

	static fakeConstr(nome,lat,lng){
		var fermata = new Fermata();
		fermata.nome = nome;
		fermata.latitudine = lat;
		fermata.longitudine = lng;
		return fermata;
	}
}