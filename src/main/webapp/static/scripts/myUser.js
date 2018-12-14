$(document).ready(function () {
    main();
});


function main() {
    var userId = localStorage.getItem("userId");

    var data = selectUser(userId);
    if (data.length > 0 && data[0] != null) {
        $('#userName').html(data[0].webchatName);
        $("#InvitationCode").html(data[0].userId);

        $("#hd_image").attr("src", data[0].webchatUrl);
        if (data[0].showLevel == 0) {
            $("#UserLevel").html("会员");
        } else if (data[0].showLevel == 1) {
            $("#UserLevel").html("组长");
        } else if (data[0].showLevel == 2) {
            $("#UserLevel").html("主管");
        } else if (data[0].showLevel == 3) {
            $("#UserLevel").html("副经理");
        } else if (data[0].showLevel == 4) {
            $("#UserLevel").html("经理");
        } else if (data[0].showLevel == 5) {
            $("#UserLevel").html("总经理");
        } else {
            $("#UserLevel").html("游客");

        }
    } else {
        alert("请联系服务商进行扫码购买商品!")
    }
}


function selectUser(userId) {
    var result;
    $.ajax({
        url: "/user/select?userId=" + userId,
        type: "GET",
        contentType: 'application/json;charset=UTF-8', //contentType很重要
        async: false,
        success: function (data) {
            result = data;
        },
        error: function (data) {
            result = null;
        }
    });

    return result;
}