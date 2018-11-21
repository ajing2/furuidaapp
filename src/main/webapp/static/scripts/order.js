$(document).ready(function(){
    order();
});


function get_order_data() {
    var result;
    $.ajax({
        type: "GET",
        timeout: 10000, // 超时时间 10 秒
        url: "/order/select",
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

            result = null;
        },
        complete: function (XMLHttpRequest, status) { //请求完成后最终执行参数　

        }
    })
    return result;
}
function order() {
    $("#table").hide();
    $("#tree").hide();
    $("#order").show();

    var data = get_order_data();
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
                {field: 'userId', title: 'UserId', width: 200, sort: true, align: 'center'},
                {field: 'money', title: '钱数', width: 100, edit: 'text', align: 'center'},
                {field: 'phone', title: '电话', width: 150, sort: true, align: 'center'},
                {field: 'state', title: '状态', width: 150, sort: true, templet: '.state', align: 'center'},
                // {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 150}
            ]],
            data: data,

        });


        form.on('switch(statusLiveDemo)', function(obj){

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