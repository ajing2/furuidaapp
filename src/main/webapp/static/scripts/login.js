var parent_id = window.location.search.replace("?parent_id=", "");

localStorage.setItem("parentId", parent_id);


var redirect_url = encodeURIComponent("http://www.gflat.cn:8088/static/index.html");
var url = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8cba5272ec62110c&redirect_uri=' + redirect_url + '&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect';
window.location.href = url;
