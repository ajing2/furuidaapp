
function selectShoppingCart(userId) {
    $.ajax({
        url : "http://www.gflat.cn:8088/shopping/select?userId=" + userId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(data) {
            if (data.length = 1 && data[0] != null){
                debugger;
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

selectShoppingCart("lingjing");


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

    var shoppingCartId = $("#shoppingCartId").val();
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
    if (district == null){
        var address = provinces + city + lastAddress;
    }else{
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
    debugger;
    if (shoppingCartId != ''){
        updateShoppingCart("hello_word");
    }else{
        insertShoppingCart("hello_word");
    }
    debugger;
    addUser("hello_word", 'test', mobile, consignee, address, '46', 0);
    debugger;
    addOrder("hello_word", parseInt($(".gzprice1").html()), 0, 1);
    debugger;
    window.location.href = "http://www.gflat.cn:8088/static/pay.html";

return false;
}


function addOrder(userId, payPrice, isPay, payTime) {
    debugger;
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
        url : "http://www.gflat.cn:8088/order/add",
        type : "POST",
        data: JSON.stringify(data),
        dataType : 'json',
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(result) {
            debugger;
        },
        error: function (result) {
            debugger;
        }
    });
}

function addUser(userId, webchat, phone, receiveName, receiveAddr, parent_id, level){
    var data = {
        userId: userId,
        webchat: webchat,
        phone: phone,
        receiveName: receiveName,
        receiveAddr: receiveAddr,
        parentId: parent_id,
        level: level,
    };
    $.ajax({
        url : "http://www.gflat.cn:8088/user/add",
        type : "POST",
        data: JSON.stringify(data),
        dataType : 'json',
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(result) {
            debugger;
            window.location.href = "/static/pay.html";


        },
        error: function (result) {
            debugger;
        }
    });
}


$('#delcartid').click(function () {
    debugger;
    if (confirm("确定移除吗")) {
        gid = $(this).attr('id');
        $(this).parent().parent().parent().remove();
        obj = $(this);
        var id = $("#shoppingCartId").val();
        if (id != null || id != ''){
            clearShoppingCart();
        }
        window.location.href = "http://www.gflat.cn:8088/static/shoppingcart.html"
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
        url : "http://www.gflat.cn:8088/shopping/update",
        type : "POST",
        data: JSON.stringify(data),
        dataType: 'json',
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(result) {
            debugger;
            window.location.href = "/static/pay.html";

        },
        error: function (result) {
            debugger;
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
        url : "http://www.gflat.cn:8088/shopping/add",
        type : "POST",
        data: JSON.stringify(data),
        dataType : 'json',
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(result) {
            debugger;


        },
        error: function (result) {
            debugger;
        }
    });
}

//计算邮费
function jisuan_shopping(id) {
    if (id == "" || typeof(id) == 'undefined') return false;
    uu = $('input[name="userress_id"]:checked').val();
    if (typeof(uu) == 'undefined' || uu == "") {
        alert("请选择一个收货地址！");
        return false;
    }
    createwindow();
    $.post('/static/mycart.php', {action: 'jisuan_shopping', shopping_id: id, userress_id: uu}, function (data) {
        if (data != "" && typeof(data) != 'undefined') {
            arr = data.split('+');
            if (arr.length == 2) {
                $('.freeshopp').html(arr[1]);
                b = $('.ppzprice').html();
                if (b == null || typeof(b) == 'undefined') {
                    b = $('.ztotals').html();
                }

                $('.freeshoppandprice').html(toDecimal(parseFloat(b) + parseFloat(arr[1]) - 0));
            } else {
                alert(data);
            }
        } else {
            $('.freeshopp').html('0.00');
            b = $('.ppzprice').html();
            if (b == null || typeof(b) == 'undefined') {
                b = $('.ztotals').html();
            }
            $('.freeshoppandprice').html(parseFloat(b) - 0);
        }
        removewindow();
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

//改变商品价格
function change_number(obj) {
    //地址ID
    userressid = $('input[name="userress_id"]:checked').val();
    if (userressid > 0) {
    } else {
        userressid = 5;
    }
    //配送ID
    shippingid = $('input[name="shipping_id"]:checked').val();

    id = $(obj).attr('id');
    numbers = $(obj).val();
    if (!(numbers > 0)) {
        numbers = 1;
        $(obj).val('1');
    }
    createwindow();
    $.post(SITE_URL + 'mycart.php', {
        action: 'ajax_change_price',
        id: id,
        number: numbers,
        shipping_id: shippingid,
        userress_id: userressid
    }, function (data) {
        removewindow();
        if (data.error == '0') {
            dis = 1;
            //data.prices = toDecimal(data.prices * dis);
            $('.ztotals').html(data.prices);
            $('.gprice' + id).html('￥' + data.thisprice);
            $('.gzprice' + id).html('￥' + toDecimal(data.thisprice * numbers));
            ff = data.freemoney;
            $('.freeshopp').html(ff);
            $('.freeshoppandprice').html(toDecimal(toDecimal(data.prices) + toDecimal(ff) - 0));
        } else {
            alert(data.message);
        }
    }, "json");
    return true;
}

function ajax_show_menu() {
    $(".showmenu").toggle();
}



function clearShoppingCart() {
    debugger;
    var id = $("#shoppingCartId").val();
    $.ajax({
        url : "http://www.gflat.cn:8088/shopping/delete?id=" + id,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(data) {
            debugger;
            window.location.href = "http://www.gflat.cn:8088/static/shoppingcart.html";

        },
        error: function (data) {
            debugger;
        }
    });
}


function selectUser(userId) {
    var result;
    $.ajax({
        url : "http://www.gflat.cn:8088/user/select?userId=" + userId,
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
    debugger;
    return result;
}

debugger;
var data = selectUser("hello_word");
debugger;
if (data.length>0 && data[0] != null){
    $("#haveUser").toggle();
    $("#havedAddress").html(data[0].receiveAddr);
    $("#havedName").html(data[0].receiveName);
    $("#havedphone").html(data[0].phone);
}