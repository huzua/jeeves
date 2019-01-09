
package com.cherry.jeeves.utils.Alimama;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;


public class Keep extends Thread{
    public CloseableHttpClient httpClient;
    public HttpGet httpGet;
    public HttpClientContext context;
    private int i;
    @Override
    public void run(){
        i += 1;
        System.out.println("第"+i+"次心跳");
        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet("http://pub.alimama.com/common/adzone/newSelfAdzone2.json?tag=29");
        context = HttpClientContext.create();
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

        //httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");

        httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");

        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        httpGet.setHeader("Cookie",  "t=6802c30ac83be12a5aad41230f2638c2; cna=LWW3FFH+ZEcCAXrrvpE7YESG; l=aB5n4YdJyYyDzkCKkMa5pSGPg70jmB5P8E7WEMayuTEhNaTksPAQkjto-Vw69_qC5Phy_X-iI; isg=BFNTjD0lC09oIcebHOKL1iRp4de9oCAztCslHQVwrHKphHMmjdkfGrDWujRPPz_C; 122862637_yxjh-filter-1=true; cookie2=11e4d8e8d4cb2f4fc47a37be3e6378dc; v=0; _tb_token_=3e69e3a13d010; alimamapwag=TW96aWxsYS81LjAgKFdpbmRvd3MgTlQgMTAuMDsgV2luNjQ7IHg2NDsgcnY6NjQuMCkgR2Vja28vMjAxMDAxMDEgRmlyZWZveC82NC4w; cookie32=a0a52b1d30ec94b8d4f21c8bb38cb415; alimamapw=RnBWFnIPFnUCQXEFBVQCVAgIUwVvAwVdBwFWUwACVVEEWAtbBQMFAAcKAFUGUwZUA1VXBFs%3D; cookie31=MTIyODYyNjM3LCVFOCU5MyU5RCVFOCVCMCU4MzIwMTUxMDA2LDEyMDc5MzA2ODNAcXEuY29tLFRC; login=W5iHLLyFOGW7aA%3D%3D; rurl=aHR0cHM6Ly9wdWIuYWxpbWFtYS5jb20v; apushc959b9f7e6ba270304159d181db2e169=%7B%22ts%22%3A1547041705248%2C%22heir%22%3A1547041261500%2C%22parentId%22%3A1547041250041%7D");
        httpGet.setHeader("Connection", "keep-alive");

        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0");
        String content = null;
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet, context);
            //HttpEntity entity = response.getEntity();   // 获取网页内容
            //System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
            content = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject messagebody = new JSONObject(content);
        } catch (JSONException e) {
            System.out.println("----------------掉线了");
        }

    }


    public static void keep() throws Exception{


     /*   try {
            CloseableHttpResponse response = httpClient.execute(httpGet, context);
            HttpEntity entity = response.getEntity();   // 获取网页内容
            //System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");


                JSONObject messagebody = new JSONObject(content);



            *//*System.out.println("&gt;&gt;&gt;&gt;&gt;&gt;headers:");
            Arrays.stream(response.getAllHeaders()).forEach(System.out::println);*//*
           *//* System.out.println("&gt;&gt;&gt;&gt;&gt;&gt;cookies:");
            context.getCookieStore().getCookies().forEach(System.out::println);
            context.setCookieStore(context.getCookieStore());
            CloseableHttpResponse
                    response2 = httpClient.execute(httpGet, context);*//*
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }
}
