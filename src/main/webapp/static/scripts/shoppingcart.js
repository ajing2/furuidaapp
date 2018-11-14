



function selectShoppingCart(userId) {
    $.ajax({
        url : "http://www.yitaonet.cn/shopping/select?userId=" + userId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(data) {
            debugger;
            if (data.length >= 0){
                window.location.href = "http://www.yitaonet.cn/static/mycart.html"
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
    debugger;
    selectShoppingCart(userId);
});