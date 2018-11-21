package com.furuida.service.pay.wx.sdk;

/**
 * @program: wxpay-sdk
 * @description:
 * @author: fuzhengquan
 * @create: 2018-11-20 23:01
 **/
public class WXPayDomain implements IWXPayDomain {
    /**
     * 上报域名网络状况
     *
     * @param domain            域名。 比如：api.mch.weixin.qq.com
     * @param elapsedTimeMillis 耗时
     * @param ex                网络请求中出现的异常。
     *                          null表示没有异常
     *                          ConnectTimeoutException，表示建立网络连接异常
     */
    public void report(String domain, long elapsedTimeMillis, Exception ex) {
        return;
    }

    /**
     * 获取域名
     *
     * @param config 配置
     * @return 域名
     */
    public DomainInfo getDomain(WXPayConfig config) {
        DomainInfo domain = config.getWXPayDomain();
        return domain;
    }
}
