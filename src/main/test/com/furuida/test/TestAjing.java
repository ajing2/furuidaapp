package com.furuida.test;

import com.furuida.test.WeChatAccessToken;
import com.furuida.test.WeChatUtils;

/**
 * @ClassName TestAjing
 * @Description TODO
 * @Author lingxiangxiang
 * @Date 12:35 PM
 * @Version 1.0
 **/
public class TestAjing {
    public static void main(String[] args) {
        WeChatUtils weChatUtils = new WeChatUtils();
        WeChatAccessToken data = weChatUtils.getAccessToken("aaa");
        System.out.println("+++++++++++++++++++++++");
        System.out.println(data.toString());
        System.out.println("+++++++++++++++++++++++");

    }

    public String getRequestCodeUrl(String redirectUrl) {
        return String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect", "wx8cba5272ec62110c", redirectUrl, "snsapi_userinfo", "ajing_state");
    }
}
