



function selectShoppingCart(userId) {
    $.ajax({
        url : "/shopping/select?userId=" + userId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(data) {

            if (data.length >= 0){
                window.location.href = "/static/mycart.html"
            }

        },
        error: function (data) {

            console.log(data);
        }
    });
}

$(document).ready(function () {
    var userId = localStorage.getItem("userId");

    selectShoppingCart(userId);
});