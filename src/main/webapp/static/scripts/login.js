$(document).ready(function () {

    var parent_id = window.location.search.replace("?parent_id=", "");

    localStorage.setItem("parentId", parent_id);


    var redirect_url = encodeURIComponent("http://www.yitaonet.cn/static/index.html");
    var url = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfe535a4a8609fa17&redirect_uri=' + redirect_url + '&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect';
    window.location.href = url;
});
