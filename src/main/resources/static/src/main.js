function removeCliente(id) {


    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: "/clientes/"+id,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            alert(data);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

}
