$(document).ready(function(){
    table();
});

function get_cash_data() {
    var result;
    $.ajax({
        type: "GET",
        timeout: 10000, // 超时时间 10 秒
        url: "http://www.gflat.cn:8088/cashHistory/select",
        xhrFields: {
            withCredentials: true
        },
        async:false,
        success: function (callback) {
            debugger;
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
    debugger;
    var data = get_cash_data();
    debugger;
    layui.use('table', function () {
        var table = layui.table;
        table.render({
            elem: '#test',
            title: '打钱数据表',
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'userId', title: 'UserId', width: 80, fixed: 'left', unresize: true, sort: true},
                {field: 'money', title: '钱数', width: 120, edit: 'text'},
                {field: 'phone', title: '电话', width: 100, sort: true},
                {field: 'state', title: '状态', width: 100, sort: true, templet: '.state'},
                {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 150}
            ]],
            data: data,

        });


        table.on('tool(test)', function(obj){
            var data = obj.data;
            //console.log(obj)
            if(obj.event === 'update'){
                layer.confirm('真的确认打过钱了吗', function(index){
                    // obj.del();
                    layer.close(index);
                });
            }
        });


    });



}