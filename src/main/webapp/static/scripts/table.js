$(document).ready(function(){
    table();
});

function get_cash_data() {
    var result;
    var userId = $("#tableSearch").val();
    var state = $("#tableStatue").val();
    $.ajax({
        type: "GET",
        timeout: 10000, // 超时时间 10 秒
        url: "/cashHistory/query?userId=" + userId + "&state=" + state,
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

function table() {
    $("#table").show();
    $("#tree").hide();
    $("#order").hide();
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
            limits: [15, 50, 100, 1000],// 设置每页显示多少条数据
            cols: [[
                {type: 'checkbox'},
                {field: 'userId', title: 'UserId', width: 150, sort: true, align: 'center'},
                {field: 'webchatName', title: '微信昵称', width: 200, sort: true, align: 'center'},
                {field: 'level', title: '职级', width: 100, sort: true, align: 'center'},
                {field: 'money', title: '钱数', width: 100, align: 'center'},
                {field: 'phone', title: '联系电话', width: 150, sort: true, align: 'center'},
                {field: 'time', title: '打钱时间', width: 200, sort: true, align: 'center'},
                {field: 'state', title: '状态', width: 150, sort: true, templet: '.state', align: 'center'},
            ]],
            data: data,

        });


        var $ = layui.$, active = {
            reload: function () {
                table.reload("table", {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    },
                    data:get_cash_data()
                })

            }
        };

        $('#tableSubmit').on('click', function (e) {
            e.preventDefault();
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });



        form.on('switch(statusLiveDemo)', function(obj){

            var id = parseInt(this.value);
            var state;
            var nowTime = new Date();
            var updateTime = nowTime.getFullYear() + "-" + nowTime.getMonth() + "-" + nowTime.getDate() + " " + nowTime.getHours() + ":" + nowTime.getMinutes() + ":" + nowTime.getSeconds();
            if(obj.elem.checked){
                state = 1;
            }else {
                state = 0;
            }
            updateCashHistory({id: id, state: state, time: updateTime});

            return false;//只此一句

        });


    });



}


function updateCashHistory(data) {
    $.ajax({
        url : "/cashHistory/update",
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