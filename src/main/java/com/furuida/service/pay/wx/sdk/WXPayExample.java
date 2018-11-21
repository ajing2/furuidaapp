package com.furuida.service.pay.wx.sdk;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: wxpay-sdk
 * @description:
 * @author: fuzhengquan
 * @create: 2018-11-20 22:59
 **/
public class WXPayExample {
    public static void main(String[] args) throws Exception {

        MyConfig config = new MyConfig();
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "腾讯充值中心-QQ会员充值");
        data.put("out_trade_no", "201609091059590000001223");
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", "JSAPI");  // 此处指定为扫码支付
        data.put("product_id", "121");
        data.put("openid", "oBTvG0w2SWwH1Nq7G3VOO3AT7Ayc");
        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(new Date().getTime()/1000 + " " + resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
