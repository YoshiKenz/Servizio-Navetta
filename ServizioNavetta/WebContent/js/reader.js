let scanner = new Instascan.Scanner({ video: document.getElementById('video') });
scanner.addListener('scan', function (content) {
		console.log(content);
		 var ticketCode = {code:content}; 
	        $.post({
	    		type: "POST",
	    		url: "ObliteraBigliettoQR",
	    		datatype: "json",
	    		data: JSON.stringify(ticketCode),
	    		success: function (data){
	    			var codeOk = JSON.parse(data);
	    			if(codeOk.verified == true){
	    				alert("Obliterato in entrata");
	    			}
	    			else{
	    				alert("Obliterato in uscita");
	    			} 
	    		}
	    	});
      
});
Instascan.Camera.getCameras().then(function (cameras) {
        if (cameras.length > 0) {
          scanner.start(cameras[0]); //camera[1] quella posteriore?
        } else {
          console.error('No cameras found.');
        }
}).catch(function (e) {
        console.error(e);
   });