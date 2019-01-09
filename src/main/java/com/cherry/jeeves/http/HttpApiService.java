package com.cherry.jeeves.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
public class HttpApiService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;


    /**
     * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);

        // 装载配置信息
        //httpGet.setConfig(config);
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

        //httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");

        httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");

        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        httpGet.setHeader("Cookie",  "t=6802c30ac83be12a5aad41230f2638c2; cna=LWW3FFH+ZEcCAXrrvpE7YESG; l=aB5n4YdJyYyDzkCKkMa5pSGPg70jmB5P8E7WEMayuTEhNaTksPAQkjto-Vw69_qC5Phy_X-iI; isg=BFNTjD0lC09oIcebHOKL1iRp4de9oCAztCslHQVwrHKphHMmjdkfGrDWujRPPz_C; 122862637_yxjh-filter-1=true; cookie2=11e4d8e8d4cb2f4fc47a37be3e6378dc; v=0; _tb_token_=3e69e3a13d010; alimamapwag=TW96aWxsYS81LjAgKFdpbmRvd3MgTlQgMTAuMDsgV2luNjQ7IHg2NDsgcnY6NjQuMCkgR2Vja28vMjAxMDAxMDEgRmlyZWZveC82NC4w; cookie32=a0a52b1d30ec94b8d4f21c8bb38cb415; alimamapw=RnBWFnIPFnUCQXEFBVQCVAgIUwVvAwVdBwFWUwACVVEEWAtbBQMFAAcKAFUGUwZUA1VXBFs%3D; cookie31=MTIyODYyNjM3LCVFOCU5MyU5RCVFOCVCMCU4MzIwMTUxMDA2LDEyMDc5MzA2ODNAcXEuY29tLFRC; login=W5iHLLyFOGW7aA%3D%3D; rurl=aHR0cHM6Ly9wdWIuYWxpbWFtYS5jb20v; apushc959b9f7e6ba270304159d181db2e169=%7B%22ts%22%3A1547041705248%2C%22heir%22%3A1547041261500%2C%22parentId%22%3A1547041250041%7D");
        httpGet.setHeader("Connection", "keep-alive");

        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0");

        //t=6802c30ac83be12a5aad41230f2638c2; cna=LWW3FFH+ZEcCAXrrvpE7YESG; l=aB5n4YdJyYyDzzbBzMaYpSR_s70jmB5P8fnCEMaywTEhNaTksPAQkjno-Vw69_qC5TGy_X-iI; isg=BDg4XBpNkK5dzPwqy9sgF2ugCuYKCVvS67J-THKphHMmjdh3GrFsu06nQUWYxlQD; JSESSIONID=51A6A61F3E486DC2906C2D7085B01097; cookie2=18758dca87cf41420de2089ca2965e27; v=0; _tb_token_=6ee3e73e37a7; alimamapwag=TW96aWxsYS81LjAgKFdpbmRvd3MgTlQgMTAuMDsgV2luNjQ7IHg2NDsgcnY6NjQuMCkgR2Vja28vMjAxMDAxMDEgRmlyZWZveC82NC4w; cookie32=a0a52b1d30ec94b8d4f21c8bb38cb415; alimamapw=RnBWFnIPFnUCQXEFBVQCVAgIUwVvAwVdBwFWUwACVVEEWAtbBQMFAAcKAFUGUwZUA1VXBFs%3D; cookie31=MTIyODYyNjM3LCVFOCU5MyU5RCVFOCVCMCU4MzIwMTUxMDA2LDEyMDc5MzA2ODNAcXEuY29tLFRC; login=VT5L2FSpMGV7TQ%3D%3D; rurl=aHR0cHM6Ly9wdWIuYWxpbWFtYS5jb20vP3NwbT1hMjE5dC43OTAwMjIxLzEuMTk5ODkxMDQxOS5kMzRiMWY5ZmEuZmRkMzc1YTVJNkRSTHY%3D

//        httpGet.setHeader("Accept", "Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//
//        httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
//
//        httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
//
//        httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
//        httpGet.setHeader("Cookie",  "cna=m/sCFAFYXnUCAXOrPep/gomP; undefined_yxjh-filter-1=true; 122862637_yxjh-filter-1=true; account-path-guide-s1=true; t=83218a6bcaeb2b18b6f9ab8ebf54111e; cookie2=1acb1678ed1eb2a1c2de46effce94d16; v=0; _tb_token_=e443e791757ad; JSESSIONID=64D61F996829CCD2B3B0A2BE383410FA; alimamapwag=TW96aWxsYS81LjAgKFdpbmRvd3MgTlQgMTAuMDsgV2luNjQ7IHg2NCkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzU4LjAuMzAyOS4xMTAgU2FmYXJpLzUzNy4zNg%3D%3D; cookie32=a0a52b1d30ec94b8d4f21c8bb38cb415; alimamapw=E3QFRHcHFCYDQHEGUFMBVFMHBgE8UQBVBVJXUgABAFYHWFBUUAdWUgICAgYHUgZXVlJUBAA%3D; cookie31=MTIyODYyNjM3LCVFOCU5MyU5RCVFOCVCMCU4MzIwMTUxMDA2LDEyMDc5MzA2ODNAcXEuY29tLFRC; login=UtASsssmOIJ0bQ%3D%3D; apushc959b9f7e6ba270304159d181db2e169=%7B%22ts%22%3A1545318405923%2C%22parentId%22%3A1545312950978%7D; l=aBeR_bk0yFEsFszKCMa4Asygu70jmB5zwnwy1MamITEhNaTLx1kEzH-o-VwRc_hC5JUy_7t5P; isg=BPf3nKC2Z9UlxuSIhBkOIiWLhuuBFAznDYZ1LEmkUkYt-Bc6UI-Ub7_e3hIDLqOW");
//        httpGet.setHeader("Connection", "keep-alive");
//
//        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
//

        // 发起请求
        CloseableHttpResponse response = httpClient.execute(httpGet);

        // 判断状态码是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            // 返回响应体的内容
            HttpEntity entity = response.getEntity();   // 获取网页内容
            //System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return null;
    }

    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url, Map<String, Object> map) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);

        if (map != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 调用不带参数的get请求
        return this.doGet(uriBuilder.build().toString());

    }

    /**
     * 带参数的post请求
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url, Map<String, Object> map) throws Exception {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (map != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

            // 把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);
        }

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }
    //获取头信息
    public String getLocation(String url) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext httpContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet(url);
        HttpParams params = new BasicHttpParams();
        params.setParameter("http.protocol.handle-redirects", false); // 默认不让重定向
        // params.setParameter("cookie","cna=m/sCFAFYXnUCAXOrPep/gomP; undefined_yxjh-filter-1=true; 122862637_yxjh-filter-1=true; account-path-guide-s1=true; t=83218a6bcaeb2b18b6f9ab8ebf54111e; cookie2=1acb1678ed1eb2a1c2de46effce94d16; v=0; _tb_token_=e443e791757ad; alimamapwag=TW96aWxsYS81LjAgKFdpbmRvd3MgTlQgMTAuMDsgV2luNjQ7IHg2NCkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzU4LjAuMzAyOS4xMTAgU2FmYXJpLzUzNy4zNg%3D%3D; cookie32=a0a52b1d30ec94b8d4f21c8bb38cb415; alimamapw=E3QFRHcHFCYDQHEGUFMBVFMHBgE8UQBVBVJXUgABAFYHWFBUUAdWUgICAgYHUgZXVlJUBAA%3D; cookie31=MTIyODYyNjM3LCVFOCU5MyU5RCVFOCVCMCU4MzIwMTUxMDA2LDEyMDc5MzA2ODNAcXEuY29tLFRC; login=URm48syIIVrSKA%3D%3D; JSESSIONID=3D3837611100F441797BB46BEA7E7F7E; apushc959b9f7e6ba270304159d181db2e169=%7B%22ts%22%3A1545578580322%2C%22parentId%22%3A1545578576164%7D; l=aBeR_bk0yFEsF32ByMaYQstFl70jmB5zf_1E1Mam5TEhNaTLx1kEzWroNMsko_xW7U1NuoutI-a..; isg=BDU17JnYheuLQuaCmsdsCFOdRLEvGi49G3D3prdalqz7jlGAfwBQlBlI3BgdyAF8");

        // 这样就能拿到Location头了

        httpGet.setParams(params);

        //将HttpContext对象作为参数传给execute()方法,则HttpClient会把请求响应交互过程中的状态信息存储在HttpContext中
        HttpResponse response = httpClient.execute(httpGet, httpContext);
        String location =  response.getFirstHeader("Location").getValue();
        return doGet("http://pub.alimama.com/items/search.json?q="+location);
    }

    /**
     * 不带参数post请求
     *
     * @param url
     * @return
     * @throws Exception
     */
   /* public HttpResult doPost(String url) throws Exception {
        return this.doPost(url, null);
    }*/
}