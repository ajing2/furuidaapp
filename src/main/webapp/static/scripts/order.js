$(document).ready(function(){
    // order();
    /**导出* */
    // $("#exportSubmit").on('click', function () {
    //     $.exportExcel("order/export");
    // });
});
function exportExcel() {
    // $.exportExcel("order/export");
    // window.location.href="/order/export";
    $.ajax({
        type: "POST",
        timeout: 10000, // 超时时间 10 秒
        url: "/order/export",
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
            console.log("error了。");
            result = null;
        },
        complete: function (XMLHttpRequest, status) { //请求完成后最终执行参数　

        }
    });
}

function updateOrder(data) {

    $.ajax({
        url : "/order/update",
        type : "POST",
        data: JSON.stringify(data),
        dataType: 'json',
        async:false,
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(result) {

            // alert("保存成功!")

        },
        error: function (result) {

        }
    });
    
}


function get_order_data() {
    var result;
    debugger;
    var userId = $("#orderSearch").val();
    var isShip = $("#orderStatue").val();

    $.ajax({
        type: "GET",
        timeout: 10000, // 超时时间 10 秒
        url: "/order/query?userId=" + userId + "&isShip=" + isShip,
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
    });
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
            // toolbar: true,
            totalRow: true,
            height: 'full',
            page: true,
            even: true,
            limit: 15, //默认展示多少条数据
            limits: [15, 50, 100, 1000],// 设置每页显示多少条数据
            cols: [[
                {type: 'checkbox'},
                {field: 'userId', title: 'UserId', width: 150, align: 'center'},
                {field: 'payPrice', title: '购买价格', width: 150, sort: true, align: 'center'},
                {field: 'receiveName', title: '收货名字', width: 150, sort: true, align: 'center'},
                {field: 'phone', title: '联系电话', width: 150, sort: true, align: 'center'},
                {field: 'receiveAddr', title: '收货地址', width: 150, sort: true, align: 'center'},
                {field: 'payTime', title: '订单时间', width: 180, sort: true, align: 'center'},
                {field: 'isShip', title: '是否发货', width: 150, sort: true, align: 'center', templet: '.orderConfirm'},
                {field: 'shipNum', title: '快递单号', width: 150, sort: true, align: 'center', edit: 'text',},
            ]],
            data: data,

        });


        var $ = layui.$, active = {
            reload: function () {
                table.reload("orderTable", {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    },
                    data:get_order_data()
                })

            }
        };

        $('#orderSubmit').on('click', function (e) {
            e.preventDefault();
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });


        form.on('switch(isShipDemo)', function(obj){
            debugger;
            var id = parseInt(this.value);
            var isShip;
            var nowTime = new Date();
            var updateTime = nowTime.getFullYear() + "-" + nowTime.getMonth() + "-" + nowTime.getDate() + " " + nowTime.getHours() + ":" + nowTime.getMinutes() + ":" + nowTime.getSeconds();
            if(obj.elem.checked){
                isShip = true;
            }else {
                isShip = false;
            }

            updateOrder({id: id, isShip: isShip, updateTime: updateTime});

            return false;//只此一句

        });



    });
}



jQuery.extend({
    go:function(url){
        window.location.href=url;
    },
    alert:function (msg) {
        swal({title:msg});
    },
    confirm:function(msg,callback){
        swal({
            title : msg,
            type : "warning",
            showCancelButton : true,
            confirmButtonColor : "#DD6B55",
            confirmButtonText : "确定",
            cancelButtonText : "取消",
            closeOnConfirm : false
        }, function(){
            callback.call(this);
        });
    },
    exportExcel:function(url){
        this.exportFile("确定要导出EXCEL吗？",url,1);

    },exportCsv:function(url){
        alert(url);
        this.exportFile("确定要导出为CSV吗？",url,2);

    },exportFile:function(title,url,exportType){
        swal({
            title : title,
            type : "warning",
            showCancelButton : true,
            confirmButtonColor : "#DD6B55",
            confirmButtonText : "确定",
            cancelButtonText : "取消",
            closeOnConfirm : true
        }, function(){
            if(url.indexOf("?")!=-1){
                location.href = url;
            }else{
                location.href = url;
            }
        });

    }
});