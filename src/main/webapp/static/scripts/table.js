$(document).ready(function(){
    table();
});

function get_cash_data() {
    var result;
    $.ajax({
        type: "GET",
        timeout: 10000, // 超时时间 10 秒
        url: "http://www.yitaonet.cn/cashHistory/select",
        xhrFields: {
            withCredentials: true
        },
        async:false,
        success: function (callback) {
            if (callback.length>0) {
                result = callback;
            }
        },
        error: function (err) {
            debugger;
            result = null;
        },
        complete: function (XMLHttpRequest, status) { //请求完成后最终执行参数　

        }
    })
    return result;
}

function table() {
    $("#table").show();
    $("#tree").hide();
    var data = get_cash_data();
    layui.use('table', function () {
        var form = layui.form;
        var table = layui.table;
        table.render({
            elem: '#test',
            id: 'table',
            title: '打钱数据表',
            height: 'full',
            page: true,
            even: true,
            limit: 15, //默认展示多少条数据
            limits: [10, 50, 100, 1000],// 设置每页显示多少条数据
            cols: [[
                {type: 'checkbox'},
                {field: 'userId', title: 'UserId', width: 80, sort: true},
                {field: 'money', title: '钱数', width: 120, edit: 'text'},
                {field: 'phone', title: '电话', width: 100, sort: true},
                {field: 'state', title: '状态', width: 100, sort: true, templet: '.state'},
                // {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 150}
            ]],
            data: data,

        });


        form.on('switch(statusLiveDemo)', function(obj){
            debugger;
            var id = parseInt(this.value);
            var state;
            if(obj.elem.checked){
                state = 1;
            }else {
                state = 0;
            }
            updateCashHistory({id: id, state: state});

            return false;//只此一句

        });


    });



}


function updateCashHistory(data) {
    $.ajax({
        url : "http://www.yitaonet.cn/cashHistory/update",
        type : "POST",
        data: JSON.stringify(data),
        dataType: 'json',
        async:false,
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(result) {

            // alert("保存成功!")

        },
        error: function (result) {
            debugger;
        }
    });
}