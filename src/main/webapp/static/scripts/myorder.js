$(document).ready(function () {
    var data = get_order_data();
    debugger;
    if (data.length>0){
        $("#orderNum").html(" " + data[0].orderNum);
        $("#payPrice").html(" " + data[0].payPrice);
        $("#payTime").html(" " + data[0].payTime);
        $("#shipNum").html(" " + data[0].shipNum);
    }
});


function get_order_data() {
    var result;
    var userId =  localStorage.getItem("userId");
    //
    $.ajax({
        type: "GET",
        timeout: 10000, // 超时时间 10 秒
        url: "/order/query?userId=" + userId,
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