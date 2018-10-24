function  test() {
    $.get("/test/t",function(data,status){
        alert("Data: " + data + "\nStatus: " + status);
    });
}