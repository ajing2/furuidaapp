

$(document).ready(function () {
    var userId = localStorage.getItem("userId");
    var data = findQr(userId);
    debugger;
    $("#img").attr("src", data);

});

function findQr(userId) {
    var result;
    $.ajax({
        url : "/user/qr?userId=" + userId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        async:false,
        success : function(data) {
            result = data.data;
        },
        error: function (data) {
            result = null;
        }
    });

    return result;
}