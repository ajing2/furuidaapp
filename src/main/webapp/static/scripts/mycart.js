
function selectShoppingCart(userId) {
    $.ajax({
        url : "http://www.gflat.cn:8088/shopping/select?userId=" + userId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(data) {
            if (data.length = 1){
                $("input[name='goods_number']").val(data[0].num);
                $(".gzprice1").html(data[0].num*data[0].price);
            }else{
                $("input[name='goods_number']").val(1);
                $(".gzprice1").html(158);
            }

        },
        error: function (data) {

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

    debugger;
    $("#city").citySelect();
    //$('.userreddinfo').toggle();
});

function checkvar() {
    createwindow();
    pp = $('input[name="pay_id"]:checked').val();
    if (typeof(pp) == 'undefined' || pp == "") {
        alert("请选择支付方式！");
        return false;
    }

    ss = $('input[name="shipping_id"]:checked').val();
    if (typeof(ss) == 'undefined' || ss == "") {
        alert("请选择配送方式！");
        return false;
    }

    userress_id = $('input[name="userress_id"]:checked').val();
    if (userress_id == '0' || userress_id == '' || typeof(userress_id) == 'undefined') {
        consignee = $('input[name="consignee"]').val();
        if (typeof(consignee) == 'undefined' || consignee == "") {
            alert("收货人不能为空！");
            return false;
        }

        provinces = $('select[name="province"]').val();
        if (provinces == '0') {
            alert("请选择收货地址！");
            return false;
        }

        city = $('select[name="city"]').val();
        if (city == '0') {
            alert("请完整选择收货地址！");
            return false;
        }

        district = $('select[name="district"]').val();
        if (district == '0') {
            alert("请完整选择收货地址！");
            return false;
        }

        address = $('input[name="address"]').val();
        if (typeof(address) == 'undefined' || address == "") {
            alert("详细地址不能为空！");
            return false;
        }

        mobile = $('input[name="mobile"]').val();
        tel = $('input[name="tel"]').val();
        if (mobile == "" && tel == "") {
            alert("请输入手机或者电话号码！");
            return false;
        }
        var partten = /^(13[0-9]|15[0-9]|18[0-9]|17[0-9]|14[0-9])\d{8}$/;
        if (!partten.test(mobile)) {
            alert('请输入正确的手机号码');
            return false;
        }
    }


    return true;
}

$('.delcartid').click(function () {
    if (confirm("确定移除吗")) {
        gid = $(this).attr('id');
        $(this).parent().parent().parent().remove();
        obj = $(this);
        $.post('/static/mycart.php', {action: 'ajax_remove_cargoods', gid: gid}, function (prices) {
            $('.ztotals').html(prices);
            nn = $('.mycarts').html();
            number = $(obj).parent().parent().find('input[name="goods_number"]').val();
            $('.mycarts').html(parseInt(nn) - parseInt(number));
        });
    }
    return false;
});

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


function ger_ress(type, obj, seobj) {
    parent_id = $(obj).val();
    if (parent_id == "" || typeof(parent_id) == 'undefined') {
        return false;
    }
    $.post(SITE_URL + 'user.php', {
        action: 'get_ress',
        type: type,
        parent_id: parent_id
    }, function (data) {
        if (data != "") {
            $(obj).parent().find('#' + seobj).html(data);

            if (type == 5) { //村

                $(obj).parent().find('#' + seobj).show();
                $(obj).parent().find('#select_peisong').hide();

            } else if (type == 4) { //城镇
                $(obj).parent().find('#select_village').hide();
                $(obj).parent().find('#select_village').html('<option value="0" >选择村</option>');
                $(obj).parent().find('#select_peisong').hide();
                $(obj).parent().find('#select_peisong').html('<option value="0" >选择配送店</option>');
                $(obj).parent().find('#select_town').show();

            } else if (type == 3) { //区
                $(obj).parent().find('#select_peisong').hide();
                $(obj).parent().find('#select_peisong').html('<option value="0" >选择配送店</option>');

                $(obj).parent().find('#select_village').hide();
                $(obj).parent().find('#select_village').html('<option value="0" >选择村</option>');

                $(obj).parent().find('#select_town').hide();
                $(obj).parent().find('#select_town').html('<option value="0" >选择城镇</option>');

                $(obj).parent().find('#select_district').show();

            } else if (type == 2) { //市
                $(obj).parent().find('#select_peisong').hide();
                $(obj).parent().find('#select_peisong').html('<option value="0" >选择配送店</option>');

                $(obj).parent().find('#select_village').hide();
                $(obj).parent().find('#select_village').html('<option value="0" >选择村</option>');

                $(obj).parent().find('#select_town').hide();
                $(obj).parent().find('#select_town').html('<option value="0" >选择城镇</option>');

                $(obj).parent().find('#select_district').hide();
                $(obj).parent().find('#select_district').html('<option value="0" >选择区</option>');
            }

        } else {
            alert(data);
        }
    });
}

//获取配送店

