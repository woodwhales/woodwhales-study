package cn.woodwhales.httpclient.get;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author woodwhales on 2022-08-21 15:27
 */
@Slf4j
public class GetDemo {

    public static void main(String[] args) throws Exception {
//        testGet();
        testPost1();
        testPost2();
    }

    /**
     * 发送 application/json 类型请求
     * @throws Exception
     */
    public static void testPost2() throws Exception {
        String url = "https://woodwhales.cn/";
        URIBuilder uriBuilder = new URIBuilder(url);
        URI uri = uriBuilder.build();
        log.info("uri={}", uri.toString());

        // 创建httpPost
        HttpPost httpPost = new HttpPost(uri);

        // 设置自定义配置
        RequestConfig requestConfig = RequestConfig.custom()
                // 连接超时时间
                .setConnectTimeout(2000)
                // 读取超时时间
                .setSocketTimeout(2000)
                // 从连接池获取 connection 的超时时间
                .setConnectionRequestTimeout(2000)
                .build();
        httpPost.setConfig(requestConfig);

        StringEntity stringEntity = new StringEntity("{\"username\":\"woodwhales.cn\"}", StandardCharsets.UTF_8);
        stringEntity.setContentType(new BasicHeader("Content-Type", "application/json;charset=utf-8"));
        stringEntity.setContentEncoding(StandardCharsets.UTF_8.name());
        // 发送 application/json 类型请求
        httpPost.setEntity(stringEntity);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
            HttpEntity entity = httpResponse.getEntity();
            // 获取响应报文
            String response = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            log.info("响应体={}", response);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    /**
     * 发送 application/x-www-form-urlencoded 类型请求
     * @throws Exception
     */
    public static void testPost1() throws Exception {
        String url = "https://woodwhales.cn/";
        URIBuilder uriBuilder = new URIBuilder(url);
        URI uri = uriBuilder.build();
        log.info("uri={}", uri.toString());

        // 创建httpPost
        HttpPost httpPost = new HttpPost(uri);

        // 设置自定义配置
        RequestConfig requestConfig = RequestConfig.custom()
                // 连接超时时间
                .setConnectTimeout(2000)
                // 读取超时时间
                .setSocketTimeout(2000)
                // 从连接池获取 connection 的超时时间
                .setConnectionRequestTimeout(2000)
                .build();
        httpPost.setConfig(requestConfig);


        List<BasicNameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("username", "woodwhales.cn"));
        list.add(new BasicNameValuePair("password", "woodwhales.cn"));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list);
        // 发送 application/x-www-form-urlencoded 类型请求
        httpPost.setEntity(urlEncodedFormEntity);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
            HttpEntity entity = httpResponse.getEntity();
            // 获取响应报文
            String response = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            log.info("响应体={}", response);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    /**
     * get 请求入门
     */
    public static void testGet() throws Exception {
        String url = "https://woodwhales.cn/";
        URIBuilder uriBuilder = new URIBuilder(url);

        // 设置get参数，会将参数进行urlEncode
        uriBuilder.addParameter("type", "wood+whales");
        URI uri = uriBuilder.build();
        log.info("uri={}", uri.toString());

        // 创建httpGet
        HttpGet httpGet = new HttpGet(uri);

        // 设置自定义配置
        RequestConfig requestConfig = RequestConfig.custom()
                // 连接超时时间
                .setConnectTimeout(2000)
                // 读取超时时间
                .setSocketTimeout(2000)
                // 从连接池获取 connection 的超时时间
                .setConnectionRequestTimeout(2000)
                .setProxy(new HttpHost("120.195.150.91", 9091))
                .build();
        httpGet.setConfig(requestConfig);

        // 设置请求头
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36");
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {

            // 获取响应状态码
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            log.info("响应状态码={}", statusCode);

            // 获取所有响应头
            Header[] allHeaders = httpResponse.getAllHeaders();
            for (Header headers : allHeaders) {
                log.info("响应头：name={}, value={}", headers.getName(), headers.getValue());
            }

            HttpEntity entity = httpResponse.getEntity();
            // 快捷获取响应头 Content-Type 信息
            Header contentType = entity.getContentType();
            log.info("contentType={}", contentType);

            // 获取响应报文
            String response = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            log.info("响应体={}", response);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
    }

}
