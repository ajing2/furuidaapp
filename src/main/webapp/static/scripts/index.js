$(function() {
    //滚动新闻
    var scrollTimer = null;
    var delay = 3000;
    scrollTimer = setInterval(function() {
        scrollNews();
    }, delay);

    function scrollNews() {
        var $news = $('div.scrollNews .box>ul');
        var $lineHeight = $news.find('li:first').height();
        $news.animate({
            'marginTop': -$lineHeight + 'px'
        }, 600, function() {
            $news.css({
                margin: 0
            }).find('li:first').appendTo($news);
        });
    }

    //倒计时
    timeCounter("timeCounter");

    function timeCounter(obj) {
        var total = 2 * 3600,
            maxMill = total * 1000;
        var t = null,
            restTime = 0;
        t = $.cookie("starttime");
        if (!t) {
            $.cookie('starttime', +(new Date()));
            restTime = total
        } else {
            var timePoint = maxMill + Number(t),
                currentTime = +(new Date());
            if (timePoint > currentTime) { //刷新时还在第一个计时周期时
                restTime = ((timePoint - currentTime) / 1000) | 0;
            } else {
                restTime = ((maxMill - ((currentTime - t) % maxMill)) / 1000) | 0;
            }
        }
        restTime--;
        setTimeout(function() {
            if (restTime >= 0) {
                var hours = Math.floor(restTime / 3600);
                var minutes = Math.floor(restTime / 60 - hours * 60);
                var seconds = Math.floor(restTime % 60);
                //console.log(hours +"时" + minutes + "分" + seconds + "秒");
                hours = hours < 10 ? '0' + hours : hours;
                minutes = minutes < 10 ? '0' + minutes : minutes;
                seconds = seconds < 10 ? '0' + seconds : seconds;
                var ohtml = '<span class="h">' + hours + '</span><span class="m">' + minutes + '</span><span class="s">' + seconds + '</span>';
                // if(document.getElementById(obj) != ""){
                //     document.getElementById(obj).innerHTML = ohtml;
                // }
                --restTime;
                setTimeout(arguments.callee, 1000);
            } else {
                restTime = 0;
                timeCounter("timeCounter");
            }
        }, 1000);
    }
    //banner
    $(".m-info").each(function() {
        if ($(this).find("div.swipe-wrap").children().length > 1) {
            window.mySwipe = swipe($(this).find(".swipe").get(0), {
                startSlide: 0,
                speed: 400,
                auto: 3000,
                nav: $(this).find("div.swipe-nav").get(0),
                continuous: true,
                disableScroll: false,
                stopPropagation: false,
                callback: function(p, q) {}
            });
        }
    })


});










var code;
var param = window.location.search.replace("?", "").split("&");
for (var i in param){
    if (param[i].indexOf("code=")>-1){
        code = param[i].split("code=")[1]
        break;
    }
}


function get_userinfo(code, parentId) {

    $.ajax({
        type: "GET",
        timeout: 10000, // 超时时间 10 秒
        url: "/user/info?code=" + code + "&parentId=" + parentId,
        xhrFields: {
            withCredentials: true
        },
        success: function (callback) {
            if (callback.code == 0) {
                localStorage.setItem("openid", callback.data.openid);
                localStorage.setItem("userId", callback.data.userid);
                localStorage.setItem("parentId", callback.data.parentId);
            }
        },
        error: function (err) {

            console.log(err);
        },
        complete: function (XMLHttpRequest, status) { //请求完成后最终执行参数　

        }
    })


}
// localStorage.setItem("parentId", "1");
$(document).ready(function () {
    var parentId = localStorage.getItem("parentId");
    get_userinfo(code, parentId);
});
