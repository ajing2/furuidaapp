
$(document).ready(function () {
    main();
});


function main() {
    var userId = localStorage.getItem("userId");
    var data = selectUser(userId);
    if (data.length>0 && data[0] != null){

        $("input[name='userId']").val(data[0].id);
        $("input[name='name']").val(data[0].webchatName);
        $("input[name='weixin']").val(data[0].userId);
        $("input[name='phone']").val(data[0].phone);
        $("input[name='address']").val(data[0].receiveAddr);
    }else{
        alert("请联系服务商进行扫码购买商品!")
    }


}


function updateUser(id, userId, webchat_name, phone, receive_addr) {
    var data = {
        id: id,
        userId: userId,
        phone: phone,
        webchatName: webchat_name,
        receiveAddr: receive_addr
    };


    $.ajax({
        url : "/user/update/default",
        type : "POST",
        data: JSON.stringify(data),
        dataType: 'json',
        async:false,
        contentType : 'application/json;charset=UTF-8', //contentType很重要
        success : function(result) {

            alert("保存成功!")

        },
        error: function (result) {


        }
    });

    return result;
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



var InterValObj; //timer变量，控制时间
var count =120; //间隔函数，1秒执行
var curCount;//当前剩余秒数
function sendMessage(){
    phone = '';
    phone = $('input[name="phone"]').val();
    if(phone=="" || typeof(phone)=='undefinde'){
        layer.msg("手机号不能为空");
        return false;
    }
    var partten=/^(13[0-9]|15[0-9]|19[0-9]|18[0-9]|17[0-9]|14[0-9])\d{8}$/;
    if (!partten.test(phone)) {
        layer.msg('请输入正确的手机号码');
        return  false;
    }



    curCount = count;
    //设置button效果，开始计时
    $("#btnSendCode").attr("disabled", "true");
    $("#btnSendCode").val("请在" + curCount + "秒内输入");
    InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
}
//timer处理函数
function SetRemainTime() {
    if (curCount == 0) {
        window.clearInterval(InterValObj);//停止计时器
        $("#btnSendCode").removeAttr("disabled");//启用按钮
        $("#btnSendCode").val("重新发送验证码");
    }
    else {
        curCount--;
        $("#btnSendCode").val("请在" + curCount + "秒内输入");
    }
}

function check_senddata(){

    var weixin = $('input[name="weixin"]').val();
    var phone = $('input[name="phone"]').val();
    if(phone=="" || typeof(phone)=='undefinde'){
        layer.msg("手机号不能为空");
        return false;
    }
    var partten=/^(13[0-9]|15[0-9]|19[0-9]|18[0-9]|17[0-9]|14[0-9])\d{8}$/;
    if (!partten.test(phone)) {
        layer.msg('请输入正确的手机号码');
        return  false;
    }


    var address = $('input[name="address"]').val();
    if(address=="" || typeof(address)=='undefinde'){
        layer.msg("请输入app登录密码");
        return false;
    }
    var name = $('input[name="name"]').val();
    if(name=="" || typeof(name)=='undefinde'){
        layer.msg("更改的名字不能为空");
        return false;
    }
    var id = $('input[name="userId"]').val();


    updateUser(id, weixin, name, phone, address);


    return false;
}

