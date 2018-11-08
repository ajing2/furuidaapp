



function selectShoppingCart(userId) {
    $.ajax({
        url : "http://www.gflat.cn:8088/shopping/select?userId=" + userId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(data) {
            debugger;
            if (data.length >= 0){
                window.location.href = "http://www.gflat.cn:8088/static/mycart.html"
            }

        },
        error: function (data) {
            debugger;
            console.log(data);
        }
    });
}

$(document).ready(function () {
    var userId = localStorage.getItem("userId");
    userId = "lingjing";
    debugger;
    selectShoppingCart(userId);
});