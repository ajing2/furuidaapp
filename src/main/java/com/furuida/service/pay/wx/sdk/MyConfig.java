package com.furuida.service.pay.wx.sdk;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @program: wxpay-sdk
 * @description:
 * @author: fuzhengquan
 * @create: 2018-11-20 22:36
 **/
public class MyConfig extends WXPayConfig {
    private byte[] certData;

    public MyConfig() throws Exception {
        String certPath = "D:\\apiclient_cert.p12";
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public String getAppID() {
        return "wxfe535a4a8609fa17";
    }

    public String getMchID() {
        return "1519192111";
    }

    public String getKey() {
        return "QdFE3IU4qjDrQA0kyf7X0OMxA648t8bt";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     *
     * @return
     */
    IWXPayDomain.DomainInfo getWXPayDomain() {
        return new IWXPayDomain.DomainInfo("api.mch.weixin.qq.com", true);
    }
}
