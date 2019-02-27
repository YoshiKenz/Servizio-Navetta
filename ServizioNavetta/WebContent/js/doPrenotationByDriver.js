$(document).ready(function() {
  $("#invia").click(function(){
    var nome = $("#nome").val();
    var cognome = $("#cognome").val();
    var matricola = $("#matricola").val()
    var data= {nome:nome, cognome:cognome, matricola:matricola}
    $.ajax({
      type: "POST",
      url: "DoPrenotationByDriver",
      data: data.stringify(data),
      dataType: "json",
      success: function(data)
      {	var processOk = JSON.parse(data);
    	if (processOk == true)
    	{ $("#risultato").html("Prenotazione aggiunta correttamente!");}
    	else
    	{$("#risultato").html("Prenotazione non valida!");}
      },
      error: function()
      {
        alert("Chiamata fallita, si prega di riprovare...");
      }
    });
  });
});
