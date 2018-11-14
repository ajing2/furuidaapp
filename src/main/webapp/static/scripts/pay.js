function ajax_show_menu(){
    $(".showmenu").toggle();
}


function checkpay(){
    var tt = true;
    return tt;
    var data = isChlldren();
    if (data == false){
        alert("请联系您的服务商, 他已经服务了三个顾客! 您需要等待!");
        exit();
    }


}


function isChlldren() {
    var userId = localStorage.getItem("userId");
    var result;
    $.ajax({
        url : "http://www.yitaonet.cn/user/children?userId=" + userId,
        type : "GET",
        async: false,
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(data) {

            // window.location.href = "http://www.yitaonet.cn/static/shoppingcart.html";
            result = data;

        },
        error: function (data) {
            result = data;

        }
    });
}


function clearShoppingCart() {
    var userId = localStorage.getItem("userId");

    $.ajax({
        url : "http://www.yitaonet.cn/shopping/delete?id=" + userId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(data) {

            // window.location.href = "http://www.yitaonet.cn/static/shoppingcart.html";

        },
        error: function (data) {

        }
    });
}

var userId = localStorage.getItem("userId");

setShoppingOrder(userId);
function setShoppingOrder(userId) {
    $.ajax({
        url : "http://www.yitaonet.cn/shopping/select?userId=" + userId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(data) {
            if (data.length = 1 && data[0] != null){

                $("#order_price").html(data[0].price);
                $(".num").html(data[0].num);
                $(".allPrice").html(data[0].num * data[0].price);

            }else{
                $("#order_price").html(158);
                $(".num").html(1);
                $(".allPrice").html(158);
            }

        },
        error: function (data) {
            console.log(data);
        }
    });
}