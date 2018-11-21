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
        // $.post(
        //     "/pays/pay",
        //     {
        //         price: 0.01,
        //         istype: 2,
        //         uid: localStorage.getItem("userId"),
        //     },
        //     function (data) {
        //
        //         if (data.data.code > 0) {
        //             $("#goodsname").val(data.data.goodsname);
        //             $("#istype").val(data.data.istype);
        //             $('#key').val(data.data.key);
        //             $('#notify_url').val(data.data.notify_url);
        //             $('#orderid').val(data.data.orderid);
        //             $('#orderuid').val(data.data.orderuid);
        //             $('#price').val(data.data.price);
        //             $('#return_url').val(data.data.return_url);
        //             $('#uid').val(data.data.uid);
        //             $('#submitdemo1').click();
        //
        //         } else {
        //             alert(data.data.msg);
        //         }
        //     }, "json"
        // );
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
    // wx.config({
    //     debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    //     appId: 'wxfe535a4a8609fa17', // 必填，公众号的唯一标识
    //     timestamp: 1542727955, // 必填，生成签名的时间戳
    //     nonceStr: 'JO7MleTnFiLv4W0J', // 必填，生成签名的随机串
    //     signature: '5104166545C3833C9F23455A98F75967B4B2D22333ECA8AA778FC4C60D6974A0',// 必填，签名
    //     jsApiList: ['chooseWXPay', 'onMenuShareAppMessage', 'onMenuShareTimeline', 'onMenuShareQZone'] // 必填，需要使用的JS接口列表
    // });
    // wx.ready(function(){
    //     // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
    //     alert("aaa");
    // });



    /*if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        }else if (document.attachEvent){
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    }else{
        onBridgeReady();
    }*/
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
                window.location.href="/static/index.html";
            }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                console.log('支付取消');
            }else if(res.err_msg == "get_brand_wcpay_request:fail"){
                console.log('支付失败');
                alert("支付失败");
                WeixinJSBridge.call('closeWindow');
            } //使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok,但并不保证它绝对可靠。
        });
}