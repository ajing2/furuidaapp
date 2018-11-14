function ajax_show_menu(){
    $(".showmenu").toggle();
}


function checkpay(){
    var tt = true;
    return tt;
}
function pay(){
    $.post(
        "/pays/pay",
        {
            price : 0.01,
            istype : 2,

        },
        function(data){
            if (data.code > 0){
                $("#goodsname").val(data.data.goodsname);
                2;
                $('#key').val(data.data.key);
                $('#notify_url').val(data.data.notify_url);
                $('#orderid').val(data.data.orderid);
                $('#orderuid').val(data.data.orderuid);
                $('#price').val(data.data.price);
                $('#return_url').val(data.data.return_url);
                $('#uid').val(data.data.uid);
                $('#submitdemo1').click();

            } else {
                alert(data.msg);
            }
        }, "json"
    );
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