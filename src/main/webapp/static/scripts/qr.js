

$(document).ready(function () {
    var userId = localStorage.getItem("userId");
    // userId = "191775225";
    var data = selectUser(userId);

    if (data.length > 0 && data[0] != null) {
        if (data[0].ispayed == 1 && data[0].level > -1) {
            var url = "/static/images/qr/images/" + userId + ".jpg"
            $("#img").attr("src", url);
        }else {
            alert("没有付款, 需要成为会员以后才能邀请别人");
        }
    }
});

function selectUser(userId) {
    var result;
    $.ajax({
        url : "/user/select?userId=" + userId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        async:false,
        success : function(data) {
            result = data;
        },
        error: function (data) {
            result = null;
        }
    });

    return result;
}