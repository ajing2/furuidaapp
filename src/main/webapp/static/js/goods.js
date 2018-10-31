function pload() {
    setTimeout("JqueryDialog.Close()", 2000);
}
var goods = new Object();

//购物车js
/* *
 * 添加商品到购物车
 * tt:标记的页面类型
 * obj:
 */
function addToCart(goodsid, tt, pf) {
//     if (tt == "jifen_cartlist" || tt == "jifen") {
//         if (confirm("你确定兑换吗？兑换后你的积分将会相应减少！")) {
//         } else {
//             return false;
//         }
//     }
//
// //判断js函数是否存在
//     try {
//         if (typeof(eval('checkcartattr')) == "function") {
//             if (checkcartattr() == false) {
//                 return false;
//             }
//         }
//     } catch (e) {
//         //alert("not function");
//     }

      //一个商品的所有属性
    var spec_arr = new Array(); //获取过来的商品属性
    var number = 1;  //购买数据
    var formBuy = document.forms['ECS_FORMBUY'];  //表单

    var prices = 0;
    // 检查是否有商品规格
    if (formBuy) {
        spec_arr = getSelectedAttributes(formBuy);

        if (formBuy.elements['number']) {
            number = formBuy.elements['number'].value;
        }

        if (formBuy.elements['price']) {
            prices = formBuy.elements['price'].value;
        }

    }

    goods.spec = spec_arr;
    goods.goods_id = goodsid;
    goods.number = number;
    goods.price = prices;
    goods.optype = tt

    createwindow();
    $.ajax({
        type: "POST",
        url: "http://www.gflat.cn:8088/test/ajax?action=addcart",
        success: function (data) {
            removewindow();
            if (data.error == '500') {

                result = "<div style=\"font-size:14px; line-height:26px; padding:10px; text-align:left;\">\n" +
                    "    <p>您的推荐服务商是：</p>\n" +
                    "    <p style=\"padding-left:30px; padding-bottom:10px; line-height:20px; height:20px;\"><b style=\"color:blue\">(ID:845632)王越仕</b></p>\n" +
                    "    <p style=\"color:#F96\">*&nbsp;请确认购买产品数量，不要重复购买，购买后无法退单。</p>\n" +
                    "    <p style=\"color:#F36\">*&nbsp;产品一经购买,除产品质量问题外,不支持无理由退货、退款！</p>\n" +
                    "    <p style=\"color:#F00\">*&nbsp;产品购买前请确认服务商是否正确，一经购买下单后将无法更改上下级服务商信息，后果将个人承担。</p>\n" +
                    "    <hr/>\n" +
                    "    <p style=\"text-align:center; height:50px;\">\n" +
                    "        <a onclick=\"return alertb();\" style=\" float:left;display:block; width:40%; line-height:35px;height:35px;border-radius:5px; background:#f35600; text-align:center; color:#fff\">服务商正确</a>\n" +
                    "        <a onclick=\"return alerta();\" style=\" margin-left:20px;float:left;display:block; width:40%; line-height:35px; height:35px;border-radius:5px; background:#ff8f03; text-align:center; color:#fff\">服务商不对</a>\n" +
                    "    </p>\n" +
                    "</div>"

                JqueryDialog.Open('确认服务商', result, 280, 200);
                $('.jd_dialog_m_t').hide();
                removewindow();

                return false;
            }

        //     //是否关注后才能购买处理
        //     if (data.error == '22') {
        //         $('.show_gz').show();
        //         $('body,html').animate({scrollTop: 0}, 500);
        //         return false;
        //     }
        //
        //     //限制购买数量
        //     if (data.error == '44') {
        //         alert(data.message);
        //         return false;
        //     }
        //
        //     //虚拟商品购物需跳转
        //     if (data.error == '33') {
        //         window.location.href = SITE_URL + 'vgoods.php?type=checkout&gid=' + goodsid;
        //         return false;
        //     }
        //
        //     if (tt != "jumpshopping") {
        //         var flyElm = $('.ggimg').clone().css('opacity', '0.7');
        //         flyElm.css({
        //             'z-index': 9000,
        //             'display': 'block',
        //             'position': 'absolute',
        //             'top': $('.ggimg').offset().top + 'px',
        //             'left': $('.ggimg').offset().left + 'px',
        //             'width': $('.ggimg').width() + 'px',
        //             'height': $('.ggimg').height() + 'px'
        //         });
        //         $('body').append(flyElm);
        //         hw = getPageSize();
        //         flyElm.animate({
        //             top: $('#collectBox').offset().top,
        //             left: (hw[0] - 30) + 'px',
        //             width: 30,
        //             height: 30,
        //         }, '2500', function () {
        //             flyElm.animate({opacity: 'hide'}, 1000);
        //         });
        //
        //     }
        //
        //     if (tt == 'cartlist') { //购物车列表页面
        //         if (data.error == 4) {
        //             JqueryDialog.Open('官方系统提醒你', '<br />' + data.message, 300, 50);
        //             return false;
        //         }
        //         else if (data.error == 5) { //存在商品属性
        //             JqueryDialog.Open('官方系统提醒你', data.message, 300, 200);
        //         }
        //
        //         createwindow();
        //         $.post(SITE_URL + "mycart.php", {action: 'delcartid', id: 0}, function (data) {
        //             $('.cart1 .MYCART').hide();
        //             if (data != "") {
        //                 $('.cart1 .MYCART').html(data);
        //                 $('.cart1 .MYCART').fadeIn("slow");
        //             }
        //             removewindow();
        //         });
        //     } else if (tt == 'jifen') { //兑换积分商品
        //         if (data.error == 3) { //需要登录
        //             JqueryDialog.Open('登录系统', return_login_string('jifen', goodsid), 300, 50);
        //         } else if (data.error == 5) { //存在商品属性
        //             JqueryDialog.Open('官方系统提醒你', data.message, 300, 200);
        //         } else if (data.error == 2) {
        //             str = data.message + '<br />';
        //             //JqueryDialog.Open('官方系统提醒你',str,300,60);
        //         } else {
        //             window.location.href = SITE_URL + 'excart.php?type=checkout';
        //         }
        //     } else if (tt == 'jifen_cartlist') { //兑换积分商品
        //         if (data.error == 3) { //需要登录
        //             JqueryDialog.Open('登录系统', return_login_string('jifen', goodsid), 300, 50);
        //         } else if (data.error == 5) { //存在商品属性
        //             JqueryDialog.Open('官方系统提醒你', data.message, 300, 200);
        //         } else {
        //             if (data.error == 2) {
        //                 str = '<br />' + data.message + '<br /><p style="width:175px; position:relative"><a href="' + SITE_URL + 'mycart.php?type=shopping" onclick="window.location.href=\'' + SITE_URL + 'mycart.php?type=shopping\'" style="display:block; height:25px; line-height:25px; width:80px;background-color:#ffdff3; position:absolute; left:0px; bottom:-30px;">查看购物车</a>&nbsp;<a href="javascript:;" onclick="JqueryDialog.Close();" style="display:block; height:25px; line-height:25px; width:80px;background-color:#ffdff3; position:absolute; right:0px; bottom:-30px;">继续选购</a></p>';
        //                 JqueryDialog.Open('官方系统提醒你', str, 300, 50);
        //             } else {
        //                 createwindow();
        //                 $.post(SITE_URL + "mycart.php", {action: 'delcartid', id: 0}, function (data) {
        //                     $('.cart1 .MYCART').hide();
        //                     if (data != "") {
        //                         $('.cart1 .MYCART').html(data);
        //                         $('.cart1 .MYCART').fadeIn("slow");
        //                     }
        //                     removewindow();
        //                 });
        //             }
        //             pload();
        //         }
        //     } else {
        //         if (data.error == 5) { //存在商品属性
        //             JqueryDialog.Open('官方系统提醒你', data.message, 300, 200);
        //         } else {
        //             //pload();
        //
        //             if (data.error == 0) {
        //                 if (tt == 'jumpshopping') { //jump shopping cart
        //                     if (pf == 'pifa') {
        //                         pfs = '&pf=pifa';
        //                     } else {
        //                         pfs = '';
        //                     }
        //                     window.location.href = SITE_URL + 'mycart.php?type=checkout' + pfs;
        //                 } else {
        //                     //JqueryDialog.Open('官方系统提醒你','加入购物车成功',260,40);
        //                     //alert("加入购物车成功！");
        //                     if (data.nums > 0) {
        //                         $('.mycarts').html(data.nums);
        //                     }
        //                 }
        //             } else {
        //                 //alert("加入购物车成功！");
        //                 JqueryDialog.Open('官方系统提醒你', data.message, 260, 40);
        //             }
        //         }
        //     }
        //
        }//end sucdess
    });
    return false;
}


function addShoppingCart(userId, goods) {
    $.ajax({
        url : "http://www.gflat.cn:8088/shopping/add",
        type : "POST",
        data : JSON.stringify({
            userId: userId,
            goodsId: goods.goods_id,
            num: goods.number,
            createTime: 1,
            updateTime: 1
        }), //转JSON字符串
        dataType : 'json',
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(result) {
            console.log(result);
            debugger;
        },
        error: function (result) {
            debugger;

        }
    });
}

function alerta(){
    alert('请联系推荐服务商确认推荐关系!');
    return false;
}
function alertb(){
    debugger;
    createwindow();
    // $.post(SITE_URL+"product.php",{action:'ajax_get_tongyidata'},function(data){
    data = "<div style=\"font-size:14px; line-height:26px; padding:10px; text-align:left; height:400px; overflow:scroll;\">\n" +
        "    <h2 style=\"text-align:center;\">商品购销协议</h2>\n" +
        "    <p>甲方：美澳环保科技有限公司</p>\n" +
        "    <p>乙方：消费者</p>\n" +
        "    <p><span style=\"color:#e53333;\"><b>※购买前请确认个人中心服务商是否正确。</b></span></p>\n" +
        "    <span style=\"color:#e53333;\"><b> </b></span><p><span style=\"color:#e53333;\"><b>※产品一经购买后，若无产品本身质量问题将无法退款、退货。</b></span></p>\n" +
        "    <span style=\"color:#e53333;\"><b> </b></span><p><span style=\"color:#e53333;\"><b>备注⑴产品购买前请确认服务商是否正确，一经购买下单后将无法更改上下级服务商信息，后果将个人承担。</b></span></p>\n" +
        "    <span style=\"color:#e53333;\"><b> </b></span><p><span style=\"color:#e53333;\"><b>⑵如果收货后发现有包装损坏、产品泄漏等外在因素，请参考（交货及验货、商品促销第3点）。</b></span></p>\n" +
        "    <p>根据《中华人民共和国合同法》及其他有关法律、行政法规的规定，甲、乙双方遵循平等、自愿、公平和诚实信用的原则，就商品进货购销事宜协商订立本合同。</p>\n" +
        "    <h2>一、订购商品</h2>\n" +
        "    <p>1．上述商品价格已经双方确认，如因原材料价格、生产经营成本、市场供求关系等变化导致合同期内商品价格变化，要求价格变动一方应当提前15日通知对方。价格变动自确认之日起生效，适用于确认之日后的新订单。</p>\n" +
        "    <p>2．甲方所提供商品的外包装应当符合中华人民共和国相关法律法规的规定，用中文标明产品名称、生产厂名与厂址、规格、等级、采用的产品标准、质量检验合格证明、使用说明、生产日期和安全使用期或者失效期、警示标志及其它说明等。商品应当使用正规条形码，以便于POS机识别。</p>\n" +
        "    <p>3．甲方应当保证其所提供商品的质量符合本合同或订单约定的质量标准；甲方提供有关商品质量说明的，应当符合该说明的质量要求。质量要求不明确的，按照国家标准、行业标准履行；无国家标准、行业标准的，按照通常标准或者符合合同目的的特定标准履行。</p>\n" +
        "    <h2>二、订货</h2>\n" +
        "    <p>1．乙方向甲方订货，双方约定的订单形式为：（1）乙方电子商务平台（2）电子邮件（3）传真（4）订货合同（5）其他</p>\n" +
        "    <p>2．订单应标明商品名称、生产厂名和厂址、规格、计量单位、品牌、质量、产地、数量、单价等具体内容。</p>\n" +
        "    <h2>三、交货及验收</h2>\n" +
        "    <p>1．甲方应当将订单列明的商品，按照约定的时间、运输方式交付到乙方指定地点。</p>\n" +
        "    <p>2．商品的所有权自交付时起转移给乙方，商品毁损、灭失的风险自交付起由乙方承担。</p>\n" +
        "    <p>3．乙方应当妥善安排工作人员或者本人在到货后当时内按照订单对商品的种类、规格、产地、数量、包装等进行初步验收。 一经验货完成，乙方不得无故退货。</p>\n" +
        "    <p>4．乙方对于已经验收的商品发现存在内在质量问题，应当在质量保证期内提出。</p>\n" +
        "    <h2>四、商品促销</h2>\n" +
        "    <p>1．乙方可以根据企业经营战略制定商品促销计划，以加速商品的周转和销售。</p>\n" +
        "    <p>2．甲方可以根据自身产品状况，有选择地参加促销活动，同时向乙方支付促销服务费用或者给予商品价格优惠。</p>\n" +
        "    <p>3．乙方换货应当向甲方发出书面或电子信息换货通知，甲方应当于收到后5日内对所换商品进行核实并书面确认，10日内负责更换。</p>\n" +
        "    <h2>五、争议的解决</h2>\n" +
        "    <p>凡因执行本协议所发生的争议，或与本协议有关的一切争议，双方应通过友好协商解决。如果协商不成，可以向甲方住所地法院起诉。</p>\n" +
        "    <div style=\"height:14px;\"></div>\n" +
        "    <div style=\"text-align:center; position:absolute; bottom:0px; left:0px; z-index:999; width:100%; height:34px;\">\n" +
        "        <a onclick=\"return tongyixieyi();\" style=\" display:block;border:none; height:34px; line-height:34px; background:#999; color:#fff; font-size:14px; text-align:center\">同意协议</a>\n" +
        "    </div>\n" +
        "</div>"
        JqueryDialog.Open('商品购销协议',data,280,420);
        $('.jd_dialog_m_t').hide();
        removewindow();
    // });
}


function tongyixieyi(){
    debugger;
    if (goods.optype == "shoppingcart"){
        addShoppingCart("lingjing", goods);
    }else{
        window.location.href = "/static/mycart.html"
    }
    JqueryDialog.Close();


    return false;
}
//积分兑换
// function addToCartJifen(goodsid) {
// //判断js函数是否存在
//     try {
//         if (typeof(eval('checkcartattr')) == "function") {
//             if (checkcartattr() == false) {
//                 return false;
//             }
//         }
//     } catch (e) {
//         //alert("not function");
//     }
//
//     var goods = new Object();  //一个商品的所有属性
//     var spec_arr = new Array(); //获取过来的商品属性
//     var number = 1;  //购买数据
//     var formBuy = document.forms['ECS_FORMBUY']; //表单
//
//     // 检查是否有商品规格
//     if (formBuy) {
//         spec_arr = getSelectedAttributes(formBuy);
//
//         if (formBuy.elements['number']) {
//             number = formBuy.elements['number'].value;
//         }
//
//     }
//
//     goods.spec = spec_arr;
//     goods.goods_id = goodsid;
//     goods.number = number;
//     goods.optype = (typeof(tt) == 'undefined' || tt == "") ? "" : tt;
//     createwindow();
//     $.ajax({
//         type: "POST",
//         url: SITE_URL + "excart.php?action=ajax_add_cart",
//         data: "goods=" + $.toJSON(goods),
//         dataType: "json",
//         success: function (data) {
//             removewindow();
//             if (data.error == 3) { //需要登录
//                 window.location.href = SITE_URL;
//             } else if (data.error == 5) { //存在商品属性
//                 JqueryDialog.Open('官方系统提醒你', data.message, 280, 200);
//             } else if (data.error == 2) {
//                 str = data.message + '<br />';
//                 JqueryDialog.Open('官方系统提醒你', str, 280, 60);
//                 pload();
//             } else {
//                 window.location.href = SITE_URL + 'excart.php?type=checkout';
//             }
//
//         }//end sucdess
//     });
//     return false;
// }

/**
 * 获得选定的商品属性
 */
function getSelectedAttributes(formBuy) {
    var spec_arr = new Array();
    var j = 0;

    for (i = 0; i < formBuy.elements.length; i++) {
        if (((formBuy.elements[i].type == 'radio' || formBuy.elements[i].type == 'checkbox') && formBuy.elements[i].checked) || formBuy.elements[i].tagName == 'SELECT' || formBuy.elements[i].type == 'hidden') {
            spec_arr[j] = formBuy.elements[i].name + '---' + formBuy.elements[i].value;
            j++;
        }

    }

    return spec_arr;
}

/*##############################################*/

/*
*添加收藏
*/
// function addToColl(gid) {
//     if (gid == "" || typeof(gid) == 'undefined') return false;
//
//     $.post(SITE_URL + 'ajax.php', {action: 'addtocoll', goods_id: gid}, function (data) {
//         //这里有4个返回值
//         /*
//         * return 1 =>商品id为空
//         * return 2 =>还没有登录
//         * return 3 =>添加成功
//         * return 4 =>添加失败，意外错误
//         * return 5 =>该商品已经存在购物车中了
//         */
//         data = parseInt(data);
//         if (data == 1) {
//
//             str = '添加失败！！传送ID为空！<br /><p class="opitem"><a href="href="' + SITE_URL + 'user.php?act=mycoll" onclick="location.href=\'' + SITE_URL + 'user.php?act=mycoll\'" class="collview">查看收藏</a>&nbsp;<a href="javascript:;" class="collcolse" onclick="JqueryDialog.Close();">关闭</a></p>';
//             //meswindow(str,'官方系统提醒你',300,110);
//             JqueryDialog.Open('官方系统提醒你', str, 280, 70);
//             pload();
//         } else if (data == 2) {
//
//             JqueryDialog.Open('登录系统', return_login_string('coll', gid), 300, 100);
//             //meswindow(return_login_string('coll',gid),'登录系统',300,150);
//         } else if (data == 3) {
//
//             str = '恭喜你！已成功添加到你的收藏夹！<br /><p class="opitem"><a href="javascript:;" onclick="location.href=\'' + SITE_URL + 'user.php?act=mycoll\'" class="collview">查看收藏</a>&nbsp;<a href="javascript:;" onclick="JqueryDialog.Close();" class="collcolse">关闭</a></p>';
//             //meswindow(str,'官方系统提醒你',300,110);
//             JqueryDialog.Open('官方系统提醒你', str, 280, 70);
//             pload();
//         } else if (data == 5) {
//
//             str = '该商品已经存在收藏夹中！<br /><p class="opitem"><a href="javascript:;" onclick="location.href=\'' + SITE_URL + 'user.php?act=mycoll\'" class="collview">立即查看</a>&nbsp;<a href="javascript:;"  onclick="JqueryDialog.Close();" class="collcolse">关闭</a></p>';
//             JqueryDialog.Open('官方系统提醒你', str, 280, 70);
//             //meswindow(str,'官方系统提醒你',300,110);
//             pload();
//         } else {
//
//             str = '添加失败，意外错误！<br /><p class="opitem"><a href="javascript:;" onclick="location.href=\'' + SITE_URL + 'user.php?act=mycoll\'" class="collview">查看收藏</a>&nbsp;<a href="javascript:;" onclick="JqueryDialog.Close();" class="collcolse">关闭</a></p>';
//             JqueryDialog.Open('官方系统提醒你', str, 280, 70);
//             //meswindow(str,'官方系统提醒你',300,110);
//             pload();
//         }
//     });
// }

/*
*添加收藏
*/
function addToShopColl(gid) {
    if (gid == "" || typeof(gid) == 'undefined') return false;

    $.post(SITE_URL + 'ajaxfile/shop.php', {action: 'addtocoll', shop_id: gid}, function (data) {
        //这里有4个返回值
        /*
        * return 1 =>商品id为空
        * return 2 =>还没有登录
        * return 3 =>添加成功
        * return 4 =>添加失败，意外错误
        * return 5 =>该商品已经存在购物车中了
        */
        data = parseInt(data);
        if (data == 1) {

            str = '<br />添加失败！！传送ID为空！<br /><p class="opitem"><a href="' + SITE_URL + 'user.php?act=mycoll" onclick="location.href=\'' + SITE_URL + 'user.php?act=mycoll\'" class="collview">查看收藏</a>&nbsp;<a href="javascript:;"  onclick="closewindow(this);" class="collcolse">关闭</a></p>';
            meswindow(str, '官方系统提醒你', 300, 110);
            //JqueryDialog.Open('官方系统提醒你',str,300,40);

        } else if (data == 2) {

            //JqueryDialog.Open('登录系统',return_login_string('coll',gid),300,50);
            meswindow(return_login_string('coll', gid), '登录系统', 300, 150);
        } else if (data == 3) {

            str = '<br />恭喜你！已成功添加到你的收藏夹！<br /><p class="opitem"><a href="javascript:;" onclick="location.href=\'' + SITE_URL + 'user.php?act=mycoll\'" class="collview">查看收藏</a>&nbsp;<a href="javascript:;" onclick="closewindow(this);" class="collcolse">关闭</a></p>';
            meswindow(str, '官方系统提醒你', 300, 110);
            //JqueryDialog.Open('官方系统提醒你',str,300,50);

        } else if (data == 5) {

            str = '<br />该店铺已经存在收藏夹中！<br /><p class="opitem"><a href="javascript:;" onclick="location.href=\'' + SITE_URL + 'user.php?act=mycoll\'" class="collview">立即查看</a>&nbsp;<a href="javascript:;"  onclick="closewindow(this);" class="collcolse">关闭</a></p>';
            //JqueryDialog.Open('官方系统提醒你',str,300,50);
            meswindow(str, '官方系统提醒你', 300, 110);
        } else {

            str = '<br />添加失败，意外错误！<br /><p class="opitem"><a href="javascript:;" onclick="location.href=\'' + SITE_URL + 'user.php?act=mycoll\'" class="collview">查看收藏</a>&nbsp;<a href="javascript:;" onclick="closewindow(this);" class="collcolse">关闭</a></p>';
            //JqueryDialog.Open('官方系统提醒你',str,300,40);
            meswindow(str, '官方系统提醒你', 300, 110);
        }
    });
}

/*############################################*/
function ajax_set_comtent(str) {
    t = typeof($('.GOODSCOMMENT').html());
    if (t == "string") {
        $('.GOODSCOMMENT').html(str);
        return 2;
    } else {
        return 1;
    }
}

//评论处理区
function submit_comment(goods_id) {
    if (goods_id == "") return false;
    var formComment = document.forms['ECS_COMMENT']; //表单
    var comments = new Object();
    if (formComment) {
        comments = getCommentAttributes(formComment);
    } else {
        str = 'Error:不存在评论表单对象！<br /><br';
        JqueryDialog.Open('官方系统提醒你', str, 300, 50);
        return false;
    }
    //comments.comment_rank = 3;
    comments.shopid = goods_id;
    createwindow();
    $.ajax({
        type: "POST",
        // url: SITE_URL+"ajaxfile/goods.php?action=comment",
        url: SITE_URL + "ajaxfile/shop.php?action=comment",
        data: "comments=" + $.toJSON(comments),
        dataType: "json",
        success: function (data) {
            removewindow();
            if (data.error == "" || data.error == 0) {
                $('.comment_con textarea[name="comment"]').val("");

                /*if(window.parent.ajax_set_comtent(data.message) == 1){
                    str = '<br/>尊敬的用户,点评成功！<br /><br />';
                    JqueryDialog.Open('官方系统提醒你',str,300,50);
                }else{*/
                window.parent.ajax_set_comtent(data.message);
                window.parent.JqueryDialog.Close();
                //}

                //$('.GOODSCOMMENT').html(data.message);
            } else if (parseInt(data.error) == 4) { //需要先登录
                JqueryDialog.Open('登录系统', return_login_string('comment', goods_id), 300, 50);
            } else if (parseInt(data.error) == 1) { //需要先登录
                $('.comment_mes').html(data.message);
            } else {
                str = '警告：' + data.message;
                JqueryDialog.Open('官方系统提醒你', str, 300, 50);
            }
            return false;
        } //end sucdess
    });
}

function submit_message(goods_id) {
    if (goods_id == "") return false;
    var formObj = document.forms['MESSAGES']; //表单
    var mesobj = new Object();
    if (formObj) {
        mesobj = getCommentAttributes(formObj);
    } else {
        JqueryDialog.Open('官方系统提醒你', '不存在留言表单对象！', 300, 50);
        return false;
    }

    mesobj.goods_id = goods_id;

    $.ajax({
        type: "POST",
        url: SITE_URL + "ajaxfile/feedback.php",
        data: "action=feedback&message=" + $.toJSON(mesobj),
        dataType: "json",
        success: function (data) {
            if (data.error == 0) {
                JqueryDialog.Open('官方系统提醒你', '提问成功，我们会很快回复你提出的问题！', 300, 50);
                $('.GOODSMESSAGE').html(data.message);
            } else if (data.error == 2) {
                $('.message_mes').html(data.message);
            } else {
                JqueryDialog.Open('官方系统提醒你', '<br />' + data.message, 300, 50);
            }
        } //end sucdess
    }); //end ajax
} // end function

/**
 * 获得评论属性
 */
function getCommentAttributes(formComment) {
    //var arr = new Array();
    var obj = new Object();
    var j = 0;

    for (i = 0; i < formComment.elements.length; i++) {
        if (((formComment.elements[i].type == 'radio' || formComment.elements[i].type == 'checkbox') && formComment.elements[i].checked) || formComment.elements[i].tagName == 'SELECT' || formComment.elements[i].type == 'text' || formComment.elements[i].type == 'textarea' || formComment.elements[i].type == 'hidden') {
            obj[formComment.elements[i].name] = formComment.elements[i].value;
            j++;
        }
    }
    return obj;
    // return arr;
}

//获取商品评论
// function get_comment_page(page, goods_id) {
//     if (page == "" || typeof(page) == 'undefined') var page = 1;
//     if (goods_id == "" || typeof(goods_id) == 'undefined') return false;
//     createwindow();
//     $.post(SITE_URL + 'product.php', {action: 'ajax_getcommentlist', page: page, goods_id: goods_id}, function (data) {
//         if (data != "" && typeof(data) != 'undefined') {
//             $('.GOODSCOMMENT').html(data);
//         }
//         removewindow();
//     });
// }
//
// function ajax_check_comment(gid) {
//     var uid = 0;
//     $.post(SITE_URL + "user.php", {action: "getuid"}, function (data) {
//         if (typeof(data) == 'string') {
//             uid = parseInt(data) > 0 ? parseInt(data) : 0;
//         } else {
//             uid = 0;
//         }
//         if (uid > 0) {
//             JqueryDialog.Open('评论系统', return_comment_string(gid), 450, 300);
//         } else {
//             JqueryDialog.Open('登录系统', return_login_string('comment', gid), 300, 50);
//         }
//     });
// }

//获取商品提问
// function get_message_page(page, goods_id) {
//     if (page == "" || typeof(page) == 'undefined') var page = 1;
//     if (goods_id == "" || typeof(goods_id) == 'undefined') return false;
//     createwindow();
//     $.post(SITE_URL + 'ajaxfile/feedback.php', {
//         action: 'getmessagelist',
//         page: page,
//         goods_id: goods_id
//     }, function (data) {
//         if (data != "" && typeof(data) != 'undefined') {
//             $('.GOODSMESSAGE').html(data);
//         }
//         removewindow();
//     });
// }
//
// function ajax_check_message(gid, gname) {
//     var uid = 0;
//     $.post(SITE_URL + "user.php", {action: "getuid"}, function (data) {
//         if (typeof(data) == 'string') {
//             uid = parseInt(data) > 0 ? parseInt(data) : 0;
//         } else {
//             uid = 0;
//         }
//         if (uid > 0) {
//             JqueryDialog.Open('商品提问系统', return_message_string(gid), 405, 270);
//         } else {
//             JqueryDialog.Open('登录系统', return_login_string('message', gid), 300, 50);
//         }
//     });
// }

// //购买历史
// function get_buyhistory_page(page) {
//     //HISTORYVIEW
// }

// //商品详情页面的分类商品
// function get_categoods_page(page, cid) {
//     $.post(SITE_URL + "ajaxfile/goods.php", {action: "categoods", page: page, cid: cid}, function (data) {
//         alert(data);	//还代做
//     });
// }

//分类商品
function get_categoods_page_list(page, cid, bid, price, order, sorts, limit) {
    var arr = new Object();
    if (page == "" || typeof(page) == 'undefined') page = 1;
    arr.page = page;
    if (cid == "" || typeof(cid) == 'undefined') cid = 0;
    arr.cid = cid;
    if (bid == "" || typeof(bid) == 'undefined') bid = 0;
    arr.bid = bid;
    if (price == "" || typeof(price) == 'undefined') price = "";
    arr.price = price;
    if (order == "" || typeof(order) == 'undefined') order = "cat_id";
    arr.order = order;
    if (sorts == "" || typeof(sorts) == 'undefined') sorts = "ASC";
    arr.sorts = sorts;
    if (limit == "" || typeof(limit) == 'undefined') limit = "";
    arr.limit = limit;
    if (keyword == "" || typeof(keyword) == 'undefined') keyword = "";
    arr.keyword = keyword;

    createwindow();
    $.ajax({
        type: "POST",
        url: SITE_URL + "ajaxfile/goodscate.php?action=getgoodslist",
        data: "goodswhere=" + $.toJSON(arr),
        dataType: "json",
        success: function (data) {
            if (data.message != "") {
                $('.AJAX-PRODUCT-CONNENT').html(data.message);
            }
            removewindow();
        } //end sucdess
    });
    return false;
}

// //商品分类页面的显示方式
// function setdisplay(page, cid, bid, price, order, sorts, limit, type) {
//     if (type == "" || typeof(type) == "undefined") var type = "list";
//     $.cookie('DISPLAY_TYPE', type);
//     get_categoods_page_list(page, cid, bid, price, order, sorts, limit);
// }
