$(document).ready(function () {
    var parentId = localStorage.getItem("parentId");
    var data = selectUser(parentId);
    debugger;
    if (data.length>0 && data[0] != null){
        debugger;
        $("#hd_image").attr("src", data[0].webchatUrl);
        $(".nikeName").html(data[0].webchatName);
        $("#userId").html(data[0].userId);
        var mtel = data[0].phone.substr(0, 3) + '****' + data[0].phone.substr(7);
        $("#phone").html(mtel);

    }else{
        alert("没有服务商!");
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