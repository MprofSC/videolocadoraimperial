function removeCliente(idCli) {


	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
    $.ajax({
        type: "DELETE",
        url: "/clientes/"+idCli,
        timeout: 600000,
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },        
        success: function (data) {
            location.reload();
        },
        error: function (e) {
            //console.log("ERROR : ", e);
        	location.reload();
        }
    });

}
