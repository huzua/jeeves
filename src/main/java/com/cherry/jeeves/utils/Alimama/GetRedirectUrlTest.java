package com.cherry.jeeves.utils.Alimama;



import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.net.URL;



public class GetRedirectUrlTest {
    @Test
    public void test_getRedirectUrl() throws Exception {
        String url="https://m.tb.cn/h.3pSoM2G?sm=42335b";
       // String expectUrl="http://www.zhihu.com/question/20583607/answer/16597802";
        String redictURL = getRedirectUrl(url);
        System.out.println(redictURL);
    }

    /**
     * 获取重定向地址
     * @param path
     * @return
     * @throws Exception
     */
    private String getRedirectUrl(String path) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(path)
                .openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(5000);
        return conn.getHeaderField("Location");
    }
}
