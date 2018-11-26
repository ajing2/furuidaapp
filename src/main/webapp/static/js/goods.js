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
function addToCart(goodsid, tt) {

    //若已购买直接提示并退出
    var ispayed = localStorage.getItem("ispayed");
    if (ispayed == 1) {
        alert("您已购买，每位用户仅可购买一次");
        return false;
    }
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

    var parentId = localStorage.getItem("parentId");
    var parentName = selectUser(parentId);

    result = "<div style=\"font-size:14px; line-height:26px; padding:10px; text-align:left;\">\n" +
        "    <p>您的推荐服务商是：</p>\n" +
        "    <p style=\"padding-left:30px; padding-bottom:10px; line-height:20px; height:20px;\"><b style=\"color:blue\">(ID:" + parentName + ")</b></p>\n" +
        "    <p style=\"\">*&nbsp;请确认购买产品数量，不要重复购买，购买后无法退单。</p>\n" +
        "    <p style=\"\">*&nbsp;产品一经购买,除产品质量问题外,不支持无理由退货、退款！</p>\n" +
        "    <p style=\"\">*&nbsp;产品购买前请确认服务商是否正确，一经购买下单后将无法更改订单信息，后果将个人承担。</p>\n" +
        "    <hr/>\n" +
        "    <p style=\"text-align:center; height:50px;\">\n" +
        "        <a onclick=\"return alerta();\" style=\" float:left;display:block; width:40%; line-height:35px; height:35px; border-radius:10px;background:#ff916b; text-align:center; color:#fff\">服务商错误</a>\n" +
        "        <a onclick=\"return alertb();\" style=\" margin-left:20px;float:left;display:block; width:40%; line-height:35px;height:35px;border-radius:5px; border-radius:10px; background:green; text-align:center; color:#fff\">服务商正确</a>\n" +
        "    </p>\n" +
        "</div>"

    JqueryDialog.Open('确认服务商', result, 280, 200);
    $('.jd_dialog_m_t').hide();
    removewindow();

    return false;

}


function selectUser(userId) {
    var result;
    $.ajax({
        url : "/user/select?userId=" + userId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        async:false,
        success : function(data) {

            if (data.length>0 && data[0] != null){
                result = data[0].userId + ": " + data[0].webchatName
            }else {
                result = null;
                alert("请联系您的服务商!");
            }

        },
        error: function (data) {
            result = null;
        }
    });

    return result;
}
function get_parent(parentId) {

    var result;
    $.ajax({
        url : "/user/parent?parentId=" + parentId,
        type : "GET",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        async:false,
        success : function(callback) {
            if (callback.code == 0){
                var data = callback.data;
                if (data.length>0 && data[0] != null){
                    result = data[0].userId + ": " + data[0].webchatName
                }
            }else{
                result = null;
                alert("请联系您的服务商!");
            }
        },
        error: function (data) {
            result = null;
        }
    });

    return result;
}

function addShoppingCart(goods) {
    var userId = localStorage.getItem("userId");
    //for test

    $.ajax({
        url : "/shopping/add",
        type : "POST",
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        data : JSON.stringify({
            userId: userId,
            goodsId: 1,
            num: 1,
            createTime: "1",
            updateTime: "1",
            price: 158
        }), //转JSON字符串
        // dataType : 'json',
        async: false,
        success : function(result) {
            console.log(result);
            window.location.href = "/static/mycart.html"
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
           // alert(XMLHttpRequest.status);
           // alert(XMLHttpRequest.readyState);
           // alert(textStatus);
           window.location.href = "/static/mycart.html"
        }
    });
}

function alerta(){
    alert('请联系推荐服务商再次扫码!');
    return false;
}
function alertb(){

    createwindow();
    // $.post(SITE_URL+"product.php",{action:'ajax_get_tongyidata'},function(data){
    data = "<div style=\"font-size:14px; line-height:26px; padding:10px; text-align:left; height:400px; overflow:scroll;\">\n" +
        "    <h2 style=\"text-align:center;\">商品购销协议</h2>\n" +
        "    <p>甲方：新生爱目洗眼液微商系统</p>\n" +
        "    <p>乙方：消费者</p>\n" +
        "    <p><span style=\"color:#e53333;\"><b>※购买前请确认个人中心服务商是否正确。</b></span></p>\n" +
        "    <span style=\"color:#e53333;\"><b> </b></span><p><span style=\"color:#e53333;\"><b>※产品一经购买后，若无产品本身质量问题将无法退款、退货。</b></span></p>\n" +
        "    <span style=\"color:#e53333;\"><b> </b></span><p><span style=\"color:#e53333;\"><b>备注⑴产品购买前请确认服务商是否正确，一经购买下单后将无法更改订单信息，后果将个人承担。</b></span></p>\n" +
        "    <span style=\"color:#e53333;\"><b> </b></span><p><span style=\"color:#e53333;\"><b>⑵如果收货后发现有包装损坏、产品泄漏等外在因素，请参考（交货及验货、商品促销第3点）。</b></span></p>\n" +
        "    <p>根据《中华人民共和国合同法》及其他有关法律、行政法规的规定，甲、乙双方遵循平等、自愿、公平和诚实信用的原则，就商品进货购销事宜协商订立本合同。</p>\n" +
        "    <h2>一、订购商品</h2>\n" +
        "    <p>1．上述商品价格已经双方确认，如因原材料价格、生产经营成本、市场供求关系等变化导致合同期内商品价格变化，要求价格变动一方应当提前15日通知对方。价格变动自确认之日起生效，适用于确认之日后的新订单。</p>\n" +
        "    <p>2．甲方所提供商品的外包装应当符合中华人民共和国相关法律法规的规定，用中文标明产品名称、生产厂名与厂址、规格、等级、采用的产品标准、质量检验合格证明、使用说明、生产日期和安全使用期或者失效期、警示标志及其它说明等。商品应当使用正规条形码，以便于POS机识别。</p>\n" +
        "    <p>3．甲方应当保证其所提供商品的质量符合本合同或订单约定的质量标准；甲方提供有关商品质量说明的，应当符合该说明的质量要求。质量要求不明确的，按照国家标准、行业标准履行；无国家标准、行业标准的，按照通常标准或者符合合同目的的特定标准履行。</p>\n" +
        "    <h2>二、订货</h2>\n" +
        "    <p>1．乙方向甲方订货，双方约定的订单形式为：甲方微信服务号下单</p>\n" +
        "    <p>2．订单应标明商品名称、生产厂名和厂址、规格、计量单位、品牌、质量、产地、数量、单价等具体内容。</p>\n" +
        "    <h2>三、交货及验收</h2>\n" +
        "    <p>1．甲方应当将订单列明的商品，按照约定的时间、运输方式交付到乙方指定地点。</p>\n" +
        "    <p>2．商品的所有权自交付时起转移给乙方，商品毁损、灭失的风险自交付起由乙方承担。</p>\n" +
        "    <p>3．乙方应按照订单对商品的种类、规格、产地、数量、包装等进行初步验收。 一经验货完成，乙方不得无故退货。</p>\n" +
        "    <p>4．乙方对于已经验收的商品发现存在内在质量问题，应当在质量保证期内提出。</p>\n" +
        "    <h2>四、商品促销</h2>\n" +
        "    <p>1．甲方可以根据自身产品状况，有选择地参加促销活动，同时向乙方支付促销服务费用或者给予商品价格优惠。\n</p>\n" +
        "    <h2>五、商品确认</h2>\n" +
        "    <p>1、系统只认在本系统里下单的订单产品，在系统外购买的产品一率不计在系统中，如有不规范产品，违规操作等等，均与本系统无关。</p>\n" +
        "    <h2>六、争议的解决</h2>\n" +
        "    <p>凡因执行本协议所发生的争议，或与本协议有关的一切争议，双方应通过友好协商解决。如果协商不成，可以向甲方住所地法院起诉。</p>\n" +
        "    <div style=\"height:14px;\"></div>\n" +
        "    <div style=\"text-align:center; position:absolute; bottom:0px; left:0px; z-index:999; width:100%; height:34px;\">\n" +
        "        <a onclick=\"return tongyixieyi();\" style=\" display:block;border:none; height:34px; line-height:34px; background:#259946; color:#fff; font-size:14px; text-align:center\">同意协议</a>\n" +
        "    </div>\n" +
        "</div>"
        JqueryDialog.Open('商品购销协议',data,280,420);
        $('.jd_dialog_m_t').hide();
        removewindow();
    // });
}
function selectShoppingCart(userId) {
    var result;
    $.ajax({
        url : "/shopping/select?userId=" + userId,
        type : "GET",
        async:false,
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(data) {
            if (data.length >0 && data[0] != null){
                result = data;
            }
        },
        error: function (data) {

            console.log(data);
        }
    });
    return result;
}

function tongyixieyi(){

    var userId = localStorage.getItem("userId");
    //for test
    // var userId = "121";

    var data = selectShoppingCart(userId);
    if (data!= null){
        window.location.href = "/static/mycart.html"
    }else{
        addShoppingCart(goods);
    }
    JqueryDialog.Close();


    return false;
}


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

