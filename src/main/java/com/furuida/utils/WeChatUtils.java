package com.furuida.utils; /**
 * @ClassName WeChatUtils
 * @Description TODO
 * @Author lingxiangxiang
 * @Date 12:22 PM
 * @Version 1.0
 **/


import com.furuida.model.UserInfo;
import com.furuida.model.WechatAccessTokenCheck;
import net.sf.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;


public class WeChatUtils {

    private static String getCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8cba5272ec62110c&redirect_uri=http%3a%2f%2fwww.yitaonet.cn%2fstatic%2findex.html&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
    // 微信公众号的appId以及secret
    private static String appId = "wxcd28872d096b833d";
    private static String secret = "531ce87c1c1dfa017a2ef82ecc2d71c8";
    // 获取网页授权access_token的Url，和基础服务access_token不同，记得区分
    private static String getAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    // 刷新网页授权access_token的Url，和基础服务access_token不同，记得区分
    private static String getRefreshAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=cd684765a63d305a38085ab25b562673&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    // 检验授权凭证access_token是否有效,和基础服务access_token不同，记得区分
    private static String checkAccessTokenUrl = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
    // 获取用户信息的Url
    private static String getWXUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * 根据code获取到网页授权access_token
     *
     * @param code 微信回调后带有的参数code值
     * @author Shen
     */
    public static WeChatAccessToken getAccessToken(String code) {
        String url = WeChatUtils.getAccessTokenUrl.replace("APPID", WeChatUtils.appId).replace("SECRET", secret)
                .replace("CODE", code);
        JSONObject jsonObj = JSONObject.fromObject(httpRequest(url, "POST", null));
        return (WeChatAccessToken) JSONObject.toBean(jsonObj, WeChatAccessToken.class);
    }

    /**
     * 根据在获取accessToken时返回的refreshToken刷新accessToken
     *
     * @param refreshToken
     * @author Shen
     */
//    public static WeChatAccessToken getRefreshAccessToken(String refreshToken) {
//        String url = WeChatUtils.getRefreshAccessTokenUrl.replace("APPID", WeChatUtils.appId).replace("REFRESH_TOKEN",
//                refreshToken);
//        JSONObject jsonObj = JSONObject.fromObject(httpRequest(url, "POST", null));
//        return (WeChatAccessToken) JSONObject.toBean(jsonObj, WeChatAccessToken.class);
//    }

    /**
     * 获取微信用户信息
     *
     * @param openId      微信标识openId
     * @param accessToken 微信网页授权accessToken
     * @author Shen
     */
    public static UserInfo getWXUserInfoUrl(String openId, String accessToken) {
        String url = WeChatUtils.getWXUserInfoUrl.replace("OPENID", openId).replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObj = JSONObject.fromObject(httpRequest(url, "POST", null));
        return (UserInfo) JSONObject.toBean(jsonObj, UserInfo.class);
    }

    /**
     * 检验授权凭证accessToken是否有效
     *
     * @param openId
     * @param accessToken
     * @author Shen
     */
    public WechatAccessTokenCheck checkAccessToken(String openId, String accessToken) {
        String url = WeChatUtils.checkAccessTokenUrl.replace("OPENID", openId).replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObj = JSONObject.fromObject(httpRequest(url, "POST", null));
        return (WechatAccessTokenCheck) JSONObject.toBean(jsonObj, WechatAccessTokenCheck.class);
    }

    /**
     * get或者post请求
     *
     * @param requestUrl
     * @param requestMethod GET or POST 需要大写*
     * @param outputStr
     * @return
     * @author Shen
     */
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();
            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
        } catch (ConnectException ce) {
            System.out.println("Weixin server connection timed out." + ce.getMessage());
        } catch (Exception e) {
            System.out.println("https request error:{}" + e.getMessage());
        }
        return buffer.toString();
    }


}