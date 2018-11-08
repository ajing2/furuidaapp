

$(document).ready(function () {
    var userId = localStorage.getItem("userId");
    userId = "1119747506";
    var url = "http://api.k780.com:88/?app=qr.get&data=http://www.gflat.cn/static/login.html?parent_id=" + userId + "&level=L&size=12"
    $("#img").attr("src", url);
});