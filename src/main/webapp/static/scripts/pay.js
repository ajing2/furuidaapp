function ajax_show_menu(){
    $(".showmenu").toggle();
}


function checkpay(){
    var tt = true;
    return tt;
}




function clearShoppingCart() {
    var userId = localStorage.getItem("userId");
    userId = "lingjing";

    $.ajax({
        url : "http://www.gflat.cn:8088/shopping/delete?id=" + userId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(data) {

            // window.location.href = "http://www.gflat.cn:8088/static/shoppingcart.html";

        },
        error: function (data) {

        }
    });
}

var userId = localStorage.getItem("userId");
userId = "lingjing";

setShoppingOrder(userId);
function setShoppingOrder(userId) {
    $.ajax({
        url : "http://www.gflat.cn:8088/shopping/select?userId=" + userId,
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