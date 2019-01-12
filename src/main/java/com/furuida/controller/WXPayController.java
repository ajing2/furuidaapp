package com.furuida.controller;

import com.furuida.model.User;
import com.furuida.service.OrderService;
import com.furuida.service.UserService;
import com.furuida.service.pay.wx.sdk.MyConfig;
import com.furuida.service.pay.wx.sdk.WXPay;
import com.furuida.service.pay.wx.sdk.WXPayConstants;
import com.furuida.service.pay.wx.sdk.WXPayUtil;
import com.furuida.utils.ExecCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 微信浏览器内微信支付/公众号支付(JSAPI)
 */
@Controller
@RequestMapping("wxpay")
public class WXPayController {
    Log log = LogFactory.getLog(WXPayController.class);
    @Resource
    UserService userService;
    @Resource
    OrderService orderService;

    /**
     * 统一订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> order(HttpServletRequest request, String ip, String openid) {
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            MyConfig config = new MyConfig();
            WXPay wxpay = new WXPay(config);

            Map<String, String> data = new HashMap<String, String>();
            data.put("body", "心生爱目洗眼液-订单结算");
            data.put("out_trade_no", getOrderIdByTime());
            data.put("device_info", "");
            data.put("fee_type", "CNY");
            data.put("total_fee", "15800");
            data.put("spbill_create_ip", ip);
            data.put("notify_url", "http://www.oldoak.com.cn/wxpay/callback");
            data.put("trade_type", "JSAPI");  // 此处指定为扫码支付
            data.put("product_id", "121");
            data.put("openid", openid);

            Map<String, String> resp = wxpay.unifiedOrder(data);
            String prepay_id = "";//预支付id
            if (resp.get("result_code").equals("SUCCESS")) {
                prepay_id = resp.get("prepay_id");
            }
            resultMap.put("appId", "wxfe535a4a8609fa17");
            resultMap.put("timeStamp", WXPayUtil.getCurrentTimestamp()+"");
            resultMap.put("nonceStr", WXPayUtil.generateNonceStr());
            resultMap.put("signType", "MD5");
            resultMap.put("package", "prepay_id=" + prepay_id);
            String paySign = WXPayUtil.generateSignature(resultMap, WXPayConstants.paternerKey);
            resultMap.put("paySign", paySign);
            return resultMap;
        } catch (Exception e) {
            log.error("---统一下单失败：" + e.getMessage(), e);
        }
        return resultMap;
    }
    public static String getOrderIdByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<6;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }
    @RequestMapping("callback")
    public String callBack(HttpServletRequest request,HttpServletResponse response){
        //System.out.println("微信支付成功,微信发送的callback信息,请注意修改订单信息");
        InputStream is = null;
        try {
            is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
            String xml = WXPayUtil.inputStream2String(is);
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);//将微信发的xml转map
            if(notifyMap.get("return_code").equals("SUCCESS") && notifyMap.get("result_code").equals("SUCCESS")){
                    String ordersSn = notifyMap.get("out_trade_no");//商户订单号
//                    String amountpaid = notifyMap.get("total_fee");//实际支付的订单金额:单位 分
//                    BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);//将分转换成元-实际支付金额:元
                    String openid = notifyMap.get("openid");  //如果有需要可以获取
                    //String trade_type = notifyMap.get("trade_type");
                    log.info("=================回调了:" + notifyMap);
                    //以下是自己的业务处理-----
                    User user = userService.selectByopenId(openid);
                    if (null != user) {
                        /**
                         * 更新user对应字段/level/ispayed
                         */
                        user.setLevel(0);
                        user.setIspayed(1);
                        userService.updateUser(user);
                        String uid = String.valueOf(openid.hashCode()).replace("-", "");
                        String parentId = user.getParentId();
                        //打钱升级逻辑
                        orderService.pay(uid, parentId);
                        //更新订单信息
                        orderService.addOrderByUser(openid, ordersSn);
                        //生成推荐码
                        ExecCommand run = new ExecCommand();
                        String cmd = "/usr/local/tomcat8/postMaker.sh " + uid;
                        run.runLocal(cmd);
                    }
            }

            //告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
            response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
            is.close();
        } catch (Exception e) {
            log.error("===回调异常：" + e.getMessage(), e);
        }
        return null;
    }

}
