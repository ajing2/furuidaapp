package com.furuida.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @Author: xiaojianjun
 * @Date: 2018/8/6 17:17
 * @Description:
 */
public class HttpClientUtils {


    /**
     * POST方式发起http请求
     *
     */
    public static String postMethod(String url, String body) {
        CloseableHttpClient httpClient = getHttpClient();
        try {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            post.setHeader("token", "d7c685ec154830a28693454edf79b2e4");

            StringEntity stringEntity = new StringEntity(body);
            post.setEntity(stringEntity);
            //执行请求
            CloseableHttpResponse httpResponse = httpClient.execute(post);
            try {
                // test
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity) {
                    return EntityUtils.toString(entity);
                }
            } finally {
                httpResponse.close();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closeHttpClient(httpClient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static CloseableHttpClient getHttpClient() {
        return HttpClients.createDefault();
    }

    private static void closeHttpClient(CloseableHttpClient client) throws IOException {
        if (client != null) {
            client.close();
        }
    }

    /**
     * 带头和参数的get请求方法
     *
     * @param url
     * @param headers
     * @param params
     * @return
     */

    public static String getMethod(String url, Map<String, String> headers, Map<String, String> params) {
        CloseableHttpClient httpClient = getHttpClient();
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(url);
            if (params != null) {
                for (String key :
                        params.keySet()) {
                    uriBuilder.setParameter(key, params.get(key));
                }
            }
            HttpGet get = new HttpGet(uriBuilder.build());
            if (headers != null) {
                for (String key :
                        headers.keySet()) {
                    get.addHeader(key, headers.get(key));
                }
            }
            CloseableHttpResponse httpResponse = httpClient.execute(get);
            try {
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity) {
                    return EntityUtils.toString(entity);
                }
            } finally {
                httpResponse.close();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * POST方式发起http请求,带自定义headers
     */
    public static String postMethod(String url, String body, Map<String, String> headers) {
        CloseableHttpClient httpClient = getHttpClient();
        try {
            HttpPost post = new HttpPost(url);
//            post.setHeader("Content-Type", "application/json");
//            post.setHeader("token", "d7c685ec154830a28693454edf79b2e4");
            post.setHeader("Content-Type", "application/json");
            for (String key :
                    headers.keySet()) {
                post.addHeader(key, headers.get(key));
            }
            StringEntity stringEntity = new StringEntity(body);
            post.setEntity(stringEntity);
            //执行请求
            CloseableHttpResponse httpResponse = httpClient.execute(post);
            try {
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity) {
                    return EntityUtils.toString(entity);
                }
            } finally {
                httpResponse.close();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closeHttpClient(httpClient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
