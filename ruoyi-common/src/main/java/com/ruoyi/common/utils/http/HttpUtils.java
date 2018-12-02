package com.ruoyi.common.utils.http;

import com.ruoyi.common.support.CharsetKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 通用http发送方法
 *
 * @author ruoyi
 */
@Slf4j
public class HttpUtils {
    
    private HttpUtils(){
        throw new IllegalStateException("Utility class");
    }

    private static final String RECV = "recv - {}";

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        StringBuilder result = new StringBuilder();

        try {
            String urlNameString = url + "?" + param;
            log.info("sendGet - {}", urlNameString);
            URLConnection connection = getUrlConnection(urlNameString);
            connection.connect();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));) {
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                log.info(RECV, result);
            }
        } catch (ConnectException e) {
            log.error(String.format("调用HttpUtils.sendGet ConnectException, url=%s,param=%s", url,param), e);
        } catch (SocketTimeoutException e) {
            log.error(String.format("调用HttpUtils.sendGet SocketTimeoutException, url=%s,param=%s", url,param), e);
        } catch (IOException e) {
            log.error(String.format("调用HttpUtils.sendGet IOException, url=%s,param=%s", url,param), e);
        } catch (Exception e) {
            log.error(String.format("调用HttpsUtil.sendGet Exception, url=%s,param=%s", url,param), e);
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {


        StringBuilder result = new StringBuilder();
        try {
            String urlNameString = url + "?" + param;
            log.info("sendPost - {}", urlNameString);
            URLConnection conn = getUrlConnection(urlNameString);
            conn.setRequestProperty("Accept-Charset", CharsetKit.UTF8);
            conn.setRequestProperty("contentType", CharsetKit.UTF8);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            try(PrintWriter out = new PrintWriter(conn.getOutputStream())){
                out.print(param);
                out.flush();
            }
            try(BufferedReader in =new BufferedReader(new InputStreamReader(conn.getInputStream(), CharsetKit.UTF8))) {
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                log.info(RECV, result);
            }
        } catch (ConnectException e) {
            log.error(String.format("调用HttpUtils.sendPost ConnectException, url=%s,param=%s", url,param), e);
        } catch (SocketTimeoutException e) {
            log.error(String.format("调用HttpUtils.sendPost SocketTimeoutException, url=%s,param=%s", url,param), e);
        } catch (IOException e) {
            log.error(String.format("调用HttpUtils.sendPost IOException, url=%s,param=%s", url,param), e);
        } catch (Exception e) {
            log.error(String.format("调用HttpsUtil.sendPost Exception, url=%s,param=%s", url,param), e);
        }
        return result.toString();
    }

    public static String sendSSLPost(String url, String param) {
        StringBuilder result = new StringBuilder();
        String urlNameString = url + "?" + param;
        try {
            log.info("sendSSLPost - {}", urlNameString);
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            URL console = new URL(urlNameString);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", CharsetKit.UTF8);
            conn.setRequestProperty("contentType", CharsetKit.UTF8);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String ret = "";
            while ((ret = br.readLine()) != null) {
                if (!"".equals(ret.trim())) {
                    result.append(new String(ret.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                }
            }
            log.info(RECV, result);
            conn.disconnect();
            br.close();
        } catch (ConnectException e) {
            log.error(String.format("调用HttpUtils.sendSSLPost ConnectException, url=%s,param=%s", url,param), e);
        } catch (SocketTimeoutException e) {
            log.error(String.format("调用HttpUtils.sendSSLPost SocketTimeoutException, url=%s,param=%s", url,param), e);
        } catch (IOException e) {
            log.error(String.format("调用HttpUtils.sendSSLPost IOException, url=%s,param=%s", url,param), e);
        } catch (Exception e) {
            log.error(String.format("调用HttpsUtil.sendSSLPost Exception, url=%s,param=%s", url,param), e);
        }
        return result.toString();
    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        private X509TrustManager origTm = null;

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            if (!ObjectUtils.isEmpty(this.origTm)) {
                this.origTm.checkClientTrusted(chain, authType);
            }
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            if (!ObjectUtils.isEmpty(this.origTm)) {
                this.origTm.checkServerTrusted(chain,authType);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String requestedHost, SSLSession remoteServerSession) {
            return requestedHost.equalsIgnoreCase(remoteServerSession.getPeerHost());
        }
    }

    private static URLConnection getUrlConnection(String urlNameString) throws IOException {
        URL realUrl = new URL(urlNameString);
        URLConnection connection = realUrl.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        return connection;
    }
}