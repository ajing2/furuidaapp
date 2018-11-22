    function ajax_show_menu() {
        $(".showmenu").toggle();
    }


    function checkpay() {
        var tt = true;
        return tt;
        var data = isChlldren();
        if (data == false) {
            alert("请联系您的服务商, 他已经服务了三个顾客! 您需要等待!");
            exit();
        }


    }


    var appId,timeStamp,nonceStr,package,signType,paySign;
    function pay() {
        var data = isChlldren();
        if (data == false) {
            alert("请联系您的服务商, 他已经服务了三个顾客! 您需要联系服务商!");
            return false;
        }
        //demo
        var url = "/wxpay/order?ip="+returnCitySN["cip"]+"&openid=" + localStorage.getItem("openid");
        $.get(url,function(result) {
            appId = result.appId;
            timeStamp = result.timeStamp;
            nonceStr = result.nonceStr;
            package = result.package;
            signType = result.signType;
            paySign = result.paySign;

            if (typeof WeixinJSBridge == "undefined") {
                if (document.addEventListener) {
                    document.addEventListener('WeixinJSBridgeReady',
                        onBridgeReady, false);
                } else if (document.attachEvent) {
                    document.attachEvent('WeixinJSBridgeReady',
                        onBridgeReady);
                    document.attachEvent('onWeixinJSBridgeReady',
                        onBridgeReady);
                }
            } else {
                onBridgeReady();
            }
        });
        return false;
    }

    function isChlldren() {
        var parentId = localStorage.getItem("parentId");
        var result;
        $.ajax({
            url: "/user/children?parentId=" + parentId,
            type: "GET",
            async: false,
            contentType: 'application/json;charset=UTF-8', //contentType很重要
            success: function (data) {

                // window.location.href = "/static/shoppingcart.html";
                result = data;

            },
            error: function (data) {
                result = data;

            }
        });
        return result;
    }


    function clearShoppingCart() {
        var userId = localStorage.getItem("userId");

        $.ajax({
            url: "/shopping/delete?id=" + userId,
            type: "GET",
            contentType: 'application/json;charset=UTF-8', //contentType很重要
            success: function (data) {

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
            url: "/shopping/select?userId=" + userId,
            type: "GET",
            contentType: 'application/json;charset=UTF-8', //contentType很重要
            success: function (data) {
                if (data.length = 1 && data[0] != null) {

                    $("#order_price").html(data[0].price);
                    $(".num").html(data[0].num);
                    $(".allPrice").html(data[0].num * data[0].price);

                } else {
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

$(document).ready(function(){
});
function onBridgeReady(){
    WeixinJSBridge.invoke( 'getBrandWCPayRequest', {
            "appId":appId,     //公众号名称,由商户传入
            "timeStamp":timeStamp,         //时间戳,自1970年以来的秒数
            "nonceStr":nonceStr, //随机串
            "package":package,
            "signType":signType,         //微信签名方式：
            "paySign":paySign //微信签名
        },
        function(res){
            if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                console.log('支付成功');
                //支付成功后跳转的页面
                window.location.href="/static/login.html?parent_id=" + localStorage.getItem("parentId");
            }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                console.log('支付取消');
            }else if(res.err_msg == "get_brand_wcpay_request:fail"){
                console.log('支付失败');
                alert("支付失败");
                WeixinJSBridge.call('closeWindow');
            } //使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok,但并不保证它绝对可靠。
        });
}