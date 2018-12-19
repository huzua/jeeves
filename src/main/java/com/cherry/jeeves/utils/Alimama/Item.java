package com.cherry.jeeves.utils.Alimama;

import com.cherry.jeeves.utils.trulingRobot.HttpUtils;

public class Item {
    public static void getItemDetails(String key){
        //清洗字符串获得淘口令
        String[] arr = key.split("https://");
        String getidurl = "https://"+ arr[1].split(" ")[0];
        //调用淘宝api解析淘口令获得商品id
        //清洗出来的短链，用httpclient跳转，然后解析entity，用jsoup解析，正则表达式匹配url规则
        //1.得到entity里面url的id 拼接简书《4》广告位和站点id都在参数里面
        //2.或者直接拿到链接地址，自己访问自己解析。


//
        //将商品id接入url地址
       // HttpUtils.doGet();
                //解析json
    }
}
