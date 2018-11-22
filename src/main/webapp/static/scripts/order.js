$(document).ready(function(){
    order();
});


function get_order_data() {
    var result;
    $.ajax({
        type: "GET",
        timeout: 10000, // 超时时间 10 秒
        url: "/order/query",
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
    debugger;
    layui.use('table', function () {
        var form = layui.form;
        var table = layui.table;
        table.render({
            elem: '#orderTable',
            id: 'orderTable',
            title: '发货数据表',
            height: 'full',
            page: true,
            even: true,
            limit: 15, //默认展示多少条数据
            limits: [10, 50, 100, 1000],// 设置每页显示多少条数据
            cols: [[
                {type: 'checkbox'},
                {field: 'userId', title: 'UserId', width: 150, edit: 'text', align: 'center'},
                {field: 'payPrice', title: '购买价格', width: 150, sort: true, align: 'center'},
                {field: 'ispayed', title: '是否支付', width: 150, sort: true, align: 'center'},
                {field: 'receiveName', title: '收货名字', width: 150, sort: true, align: 'center'},
                {field: 'phone', title: '联系电话', width: 150, sort: true, align: 'center'},
                {field: 'receiveAddr', title: '收货地址', width: 150, sort: true, align: 'center'},
                {field: 'shipNum', title: '快递单号', width: 150, sort: true, align: 'center', edit: 'text',},
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