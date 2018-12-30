package com.cherry.jeeves.utils.Alimama;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class GetId{
    public static Map<String ,String> map = new HashMap<String, String>();
    public static Map<String ,String> getArg(String url) throws Exception {
        map.clear();
        Document doc = null;
        doc = Jsoup.connect(url).get();
        Elements elScripts = doc.getElementsByTag("script");
        //获取商品id
        String id = elScripts.get(1).data().toString().split("https://a.m.taobao.com/i")[1].split(".htm?")[0];

        String locationUrl ="https://a.m.taobao.com/i"+elScripts.get(1).data().toString().split("https://a.m.taobao.com/i")[1].split("';")[0];

        map.put("id",id);
        map.put("locationUrl",locationUrl);
        return map;

    }
    public static String getSearchUrl(String url) throws Exception {
        Document doc = null;
        doc = Jsoup.connect(url).get();
        Elements elScripts = doc.getElementsByTag("script");
        //获取商品id
        String id = elScripts.get(1).data().toString().split("https://a.m.taobao.com/i")[1].split(".htm?")[0];

        //String locationUrl =
        return id;

    }

}


