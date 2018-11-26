
function selectShoppingCart(userId) {
    $.ajax({
        url : "/shopping/select?userId=" + userId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(data) {
            if (data.length = 1 && data[0] != null){

                $("#shoppingCartId").val(data[0].id);
                $("input[name='goods_number']").val(data[0].num);
                $(".gzprice1").html(data[0].num * data[0].price);

            }else{
                $("input[name='goods_number']").val(1);
                $(".gzprice1").html(158);
            }

        },
        error: function (data) {
            console.log(data);
        }
    });
}



//2位小数
function toDecimal(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return;
    }
    f = Math.round(x * 100) / 100;
    return f;
}



$('.showaddress').live('click', function () {
    var vv = $(this).val();
    if (vv == 0) {
        $('.userreddinfo').show();
    } else {
        $('.userreddinfo').hide();
    }
    $("#city").citySelect({prov:"北京",nodata:"none"});
});

function checkvar() {

    //若已购买直接提示并退出
    var ispayed = localStorage.getItem("ispayed");
    if (ispayed == 1) {
        alert("您已购买，每位用户仅可购买一次");
        return false;
    }

    var userId = localStorage.getItem("userId");

    var shoppingCartId = $("#shoppingCartId").val();
    if ($("#newAddress").attr("checked")) {
        var consignee = $('input[name="consignee"]').val();
        if (typeof(consignee) == 'undefined' || consignee == "") {
            alert("收货人不能为空！");
            return false;
        }

        var provinces = $('#city .prov').val();
        if (provinces == '' || provinces == null) {
            alert("请选择省或者直辖市！");
            return false;
        }

        var city = $('#city .city').val();
        if (city == '' || city == null) {
            alert("请选择城市或者区县！");
            return false;
        }

        var district = $('#city .dist').val();

        var lastAddress = $('input[name="address"]').val();
        if (lastAddress == '' || lastAddress == null) {
            alert("详细地址不能为空");
            return false;
        }
        if (district == null) {
            var address = provinces + city + lastAddress;
        } else {
            var address = provinces + city + district + lastAddress;
        }

        var mobile = $('input[name="mobile"]').val();

        if (mobile == "" || mobile == null) {
            alert("请输入手机或者电话号码！");
            return false;
        }
        var partten = /^(13[0-9]|15[0-9]|18[0-9]|17[0-9]|14[0-9])\d{8}$/;
        if (!partten.test(mobile)) {
            alert('请输入正确的手机号码');
            return false;
        }
        updateUser(userId, mobile, consignee, address);

    }
    if (shoppingCartId != ''){
        updateShoppingCart(userId);
    }else{
        insertShoppingCart(userId);
    }

    var newaddUser = selectUser(userId);
    if (newaddUser.length>0 && newaddUser[0].phone != "" && newaddUser[0].receiveAddr != "" && newaddUser[0].receiveName != ""){
        window.location.href = "/static/pay.html";
    }else{
        alert("没找到该用户或者收货地址有误!")
    }

return false;
}


function addOrder(userId, payPrice, isPay, payTime) {

    var data = {
        orderNum: null,
        userId: userId,
        payPrice: payPrice,
        isPay: isPay,
        payTime: payTime,
        isShip: 0,
        shipTime: null,
        isReceipt: 0,
        receiptTime: null,
        shipNum: null,
        createTime: 1,
        updateTime: 1
    };

    $.ajax({
        url : "/order/add",
        type : "POST",
        data: JSON.stringify(data),
        dataType : 'json',
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(result) {

        },
        error: function (result) {

        }
    });
}

function updateUser(userId, phone, receiveName, receiveAddr){
    var data = {
        userId: userId,
        phone: phone,
        receiveName: receiveName,
        receiveAddr: receiveAddr
    };
    $.ajax({
        url : "/user/update/default",
        type : "POST",
        data: JSON.stringify(data),
        // dataType : 'json',  返回类型不是json
        rsync: false,
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(result) {

            // window.location.href = "/static/pay.html";


        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            // alert(XMLHttpRequest.status);
            // alert(XMLHttpRequest.readyState);
            // alert(textStatus);
            // window.location.href = "/static/mycart.html"
            alert("收货地址没有添加成功, 必须重新添加!")
        }
    });
}


$('#delcartid').click(function () {

    if (confirm("确定移除吗")) {
        gid = $(this).attr('id');
        $(this).parent().parent().parent().remove();
        obj = $(this);
        var id = $("#shoppingCartId").val();
        if (id != null || id != ''){
            clearShoppingCart();
        }
        window.location.href = "/static/shoppingcart.html"
    }
    return false;
});


function updateShoppingCart(userId) {
    var id = $("#shoppingCartId").val();
    var data = {
        id: id,
        userId: userId,
        goodsId: 1,
        num: $("input[name='goods_number']").val(),
        price: 158,
        createTime: 1,
        updateTime: 1,
    };
    $.ajax({
        url : "/shopping/update",
        type : "POST",
        data: JSON.stringify(data),
        // dataType: 'json',
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(result) {

            // window.location.href = "/static/pay.html";

        },
        error: function (result) {

        }
    });
}

function insertShoppingCart(userId) {
    var data = {
        userId: userId,
        goodsId: 1,
        num: parseInt($("input[name='goods_number']").val()),
        price: 158,
        createTime: 1,
        updateTime: 1,
    };
    $.ajax({
        url : "/shopping/add",
        type : "POST",
        data: JSON.stringify(data),
        dataType : 'json',
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(result) {



        },
        error: function (result) {

        }
    });
}



//数量减1
$('.jian').live('click', function () {
    ob = $(this).parent();
    numobj = ob.find('input[name="goods_number"]');
    vall = $(numobj).val();
    if (!(vall > 0)) {
        ob.val('1');
        return false;
    }
    if (vall > 1) {
        $(numobj).val((parseInt(vall) - 1));
        $(".gzprice1").html((parseInt(vall) - 1)*158);
    }

    // nn = $('.mycarts').html();
    // $('.mycarts').html(parseInt(nn) - 1);
    // change_number(numobj);
});
//数量加1
$('.jia').live('click', function () {
    ob = $(this).parent();
    numobj = ob.find('input[name="goods_number"]');
    vall = $(numobj).val();
    if (!(vall > 0)) {
        $(ob).val('1');
        return false;
    }
    $(numobj).val((parseInt(vall) + 1));
    $(".gzprice1").html((parseInt(vall) + 1)*158);
    // nn = $('.mycarts').html();
    // $('.mycarts').html(parseInt(nn) + 1);
    // change_number(numobj);
});



function ajax_show_menu() {
    $(".showmenu").toggle();
}



function clearShoppingCart() {
    var userId = localStorage.getItem("userId");

    $.ajax({
        url : "/shopping/delete?id=" + userId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(data) {

            window.location.href = "/static/shoppingcart.html";

        },
        error: function (data) {

        }
    });
}


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
// localStorage.setItem("userId", "2-1");
$(document).ready(function () {
    var userId = localStorage.getItem("userId");
    var data = selectUser(userId);


    if (data.length>0 && data[0].phone != "" && data[0].receiveAddr != "" && data[0].receiveName != ""){
        $("#haveUser").toggle();
        $("#havedAddress").html(data[0].receiveAddr);
        $("#havedName").html(data[0].receiveName);
        $("#havedphone").html(data[0].phone);
    }

    selectShoppingCart(userId);

});

