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
function pay(){
    var data = isChlldren();
    debugger;
    if (data == false){
        alert("请联系您的服务商, 他已经服务了三个顾客! 您需要联系服务商!");
        return ;
    };
    $.post(
        "/pays/pay",
        {
            price : 0.01,
            istype : 2,
            uid : sessionStorage.getItem("userId"),
        },
        function(data){
            if (data.data.code > 0){
                $("#goodsname").val(data.data.goodsname);
                $("#istype").val(data.data.istype);
                $('#key').val(data.data.key);
                $('#notify_url').val(data.data.notify_url);
                $('#orderid').val(data.data.orderid);
                $('#orderuid').val(data.data.orderuid);
                $('#price').val(data.data.price);
                $('#return_url').val(data.data.return_url);
                $('#uid').val(data.data.uid);
                $('#submitdemo1').click();

            } else {
                alert(data.data.msg);
            }
        }, "json"
    );
}

function isChlldren() {
    var parentId = localStorage.getItem("parentId");
    var result;
    $.ajax({
            url : "/user/children?parentId=" + parentId,
        type : "GET",
        async: false,
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(data) {

            // window.location.href = "/static/shoppingcart.html";
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
        url : "/shopping/delete?id=" + userId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(data) {

            // window.location.href = "/static/shoppingcart.html";

        },
        error: function (data) {

        }
    });
}

var userId = localStorage.getItem("userId");

setShoppingOrder(userId);
function setShoppingOrder(userId) {
    $.ajax({
        url : "/shopping/select?userId=" + userId,
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