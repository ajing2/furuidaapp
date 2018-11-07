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

    //按需加载
    var jsList_ = [];
    var item = Lazy.create({
        jsList: jsList_,
        lazyId: "doc",
        trueSrc: '#src',
        offset: 10,
        delay: 100,
        delay_tot: 1000,
        imgLoad: function(element, width, height) {}
    });
    Lazy.init(item);

    //


});

//获取页面顶部被卷起来的高度
function scrollTop() {
    return Math.max(document.body.scrollTop, document.documentElement.scrollTop);
}

//获取页面文档的总高度
function documentHeight() {
    return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);
}

//获取页面浏览器视口的高度
function windowHeight() {
    return (document.compatMode == "CSS1Compat") ? document.documentElement.clientHeight : document.body.clientHeight;
}




debugger;
var url = 'https://open.weixin.qq.com/connect/qrconnect?appid=wx5e42f6049a1df0fd&redirect_uri=http%3a%2f%2fwww.gflat.cn&response_type=code&scope=snsapi_login&state=state#wechat_redirect';
$.ajax({
    url : url,
    type : "GET",
    xhrFields: {
        withCredentials: true
    },
    contentType : 'application/json;charset=UTF-8', //contentType很重要
    async:false,
    success : function(data) {
        debugger;

    },
    error: function (data) {
        debugger;
    }
});
