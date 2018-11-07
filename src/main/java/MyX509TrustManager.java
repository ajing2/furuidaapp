import javax.net.ssl.TrustManager;

/**
 * @ClassName MyX509TrustManager
 * @Description TODO
 * @Author lingxiangxiang
 * @Date 12:34 PM
 * @Version 1.0
 **/
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class MyX509TrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException
    {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException
    {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers()
    {
        return null;
    }
}
