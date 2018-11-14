
$(document).ready(function () {
    var userId = localStorage.getItem("userId");
    var data = selectChildren(userId);
    debugger;
    $('#table').bootstrapTable({
        columns: [{
            field: 'userId',
            title: '编号'
        }, {
            field: 'webchatName',
            title: '昵称'
        }, {
            field: 'phone',
            title: '电话'
        }],
        data: data
    });

})



function selectChildren(userId) {
    var result;
    $.ajax({
        url : "/user/children?userId=" + userId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        async:false,
        success : function(callback) {
            if (callback.code == 0){
                result = callback.data;
            }
        },
        error: function (callback) {
            result = null;
        }
    });

    return result;
}