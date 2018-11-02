function ajax_show_menu(){
    $(".showmenu").toggle();
}




function _report(a,c){

}
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
    window.shareData = {
        "imgUrl": "/static/images/ico-success.png",
        "LineLink": '/static/mycart.php?type=pay2&oid=301211',
        "Title": "炫耀一下，支付一下吧",
        "Content": "有惊喜哦"
    };
    // 发送给好友
    WeixinJSBridge.on('menu:share:appmessage', function (argv) {
        WeixinJSBridge.invoke('sendAppMessage', {
            "img_url": window.shareData.imgUrl,
            "img_width": "640",
            "img_height": "640",
            "link": window.shareData.LineLink,
            "desc": window.shareData.Content,
            "title": window.shareData.Title
        }, function (res) {
            _report('send_msg', res.err_msg);
        })
    });
    // 分享到朋友圈
    WeixinJSBridge.on('menu:share:timeline', function (argv) {
        WeixinJSBridge.invoke('shareTimeline', {
            "img_url": window.shareData.imgUrl,
            "img_width": "640",
            "img_height": "640",
            "link": window.shareData.LineLink,
            "desc": window.shareData.Content,
            "title": window.shareData.Title
        }, function (res) {
            _report('timeline', res.err_msg);
        });
    });
    // 分享到微博
    WeixinJSBridge.on('menu:share:weibo', function (argv) {
        WeixinJSBridge.invoke('shareWeibo', {
            "content": window.shareData.Content,
            "url": window.shareData.LineLink,
        }, function (res) {
            _report('weibo', res.err_msg);
        });
    });
}, false)

function checkpay(){
    var tt = true;
    return tt;
}

function ajax_update_payid(payid,oid,wpay,ypay){
    $.post(SITE_URL+'mycart.php',{action:'ajax_update_payid',payid:payid,oid:oid,wpay:wpay,ypay:ypay},function(data){})
}