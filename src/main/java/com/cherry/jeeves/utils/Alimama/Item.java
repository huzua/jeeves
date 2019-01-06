package com.cherry.jeeves.utils.Alimama;

import com.cherry.jeeves.http.HttpApiService;
import com.cherry.jeeves.utils.Alimama.Constants.Urls;
import com.cherry.jeeves.utils.Alimama.Service.GetExtendMessage;
import com.cherry.jeeves.utils.Alimama.Service.GetId;
import com.cherry.jeeves.utils.Alimama.pojo.ExtendMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class Item {

    //把静态变量改为成员变量，否则上条优惠信息会缓存

    @Autowired
    /* public  HttpApiService httpApiService;*/
    public static String kouling = "";
    public static String couponLinkttoken;
    public static Double tkCommFee;
    public static String couponInfo;

    public Boolean getItemDetails(String key) {
        //清洗字符串获得淘口令短链
        String[] arr = key.split("https://");
        String getidurl = "https://" + arr[1].split(" ")[0];

        //清洗出来的短链，用httpclient跳转，然后解析entity，用jsoup解析，正则表达式匹配url规则
        //获取推广位信息
        ExtendMessage extendMessage = GetExtendMessage.getExtendMessage(Urls.EXTEND_URL,"tag=29");
        try {
            //调用淘宝api解析淘口令获得商品id
            String id = GetId.getArg(getidurl).get("id");
            String url = "http://pub.alimama.com/common/code/getAuctionCode.json?auctionid=" + id + "&adzoneid="+extendMessage.getBoothId()+"&siteid="+extendMessage.getSite()+"&scenes=1&t=1489238018764&_tb_token_=qO2Nj1Sk4Rq&pvid=10_122.233.43.77_1118_1489238002348";
            //这里会碰到301问题，试着用httpclient解决
            HttpApiService httpApiService = new HttpApiService();
            String jsondata = httpApiService.doGet(url);

            //这里返回两个false应该有冲突：宝贝失效和pagelist为null应该可以整合下

            JSONObject jsonObject = new JSONObject(jsondata);
            //宝贝失效时会发生data为null
            JSONObject data = null;
            try {
                data = (JSONObject) jsonObject.get("data");
            } catch (Exception e) {
               return false;
            }
            kouling = (String) data.get("taoToken");

                try {
                    couponLinkttoken = (String) data.get("couponLinkTaoToken");

                } catch (Exception e) {
                    couponLinkttoken = null;
                }
            String location = GetId.getArg(getidurl).get("locationUrl");
            String jsDataWithCoupon = httpApiService.getLocation(location);

            //获取优惠信息
            JSONObject jsoncoupon = new JSONObject(jsDataWithCoupon);
            //jsonObject pagelist拿不到。

            JSONArray dataarr = null;
            try {
                dataarr = (JSONArray) ((JSONObject) jsoncoupon.get("data")).getJSONArray("pageList");
                if(dataarr==null){
                    return false;
                }
            } catch (Exception e) {
                return false;
            }


            tkCommFee = (Double) dataarr.getJSONObject(0).get("tkCommFee");
            JSONObject jo = dataarr.getJSONObject(0);
            if(couponLinkttoken!=null){
                couponInfo = (String) jo.get("couponInfo");
            }


        } catch (Exception e) {
            System.out.println(e);
        }


        //1.得到entity里面url的id 拼接简书《4》广告位和站点id都在参数里面
        //2.或者直接拿到链接地址，自己访问自己解析。


//
        //将商品id接入url地址
        // HttpUtils.doGet();
        //解析json

        /*
        import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;
        * JSONObject jsonObject = new JSONObject(content);
            JSONArray result = (JSONArray) jsonObject.get("result");


            Gson gson = new Gson();
            for (int i = 0;i<result.length();i++){
                JSONObject cityJ = result.getJSONObject(i);//这个是每一个对象的json对象
                City city = gson.fromJson(cityJ.toString(), City.class);
                cityList.add(city);
            }*/
        /*
         *  OkHttpUtils.get().url(path).build().execute(new MyCallBack());*/
        /*private class MyCallBack extends Callback<List<City>> {
            //response 就是后台返回的全部数据
            //这个方法的目的就是让你自己去封装数据
            @Override
            public List<City> parseNetworkResponse(Response response, int id) throws Exception {
                cityList = new ArrayList<>();

                String content = response.body().string();//这个就是后台返回的数据json串
                JSONObject jsonObject = new JSONObject(content);
                JSONArray result = (JSONArray) jsonObject.get("result");


                Gson gson = new Gson();
                for (int i = 0;i<result.length();i++){
                    JSONObject cityJ = result.getJSONObject(i);//这个是每一个对象的json对象
                    City city = gson.fromJson(cityJ.toString(), City.class);
                    cityList.add(city);
                }
                return cityList;

            }*/
        return true;
    }

    public static Map returnMes() {
        Map map = new HashMap();
        if (couponLinkttoken != null) {
            map.put("shortkey", couponLinkttoken);
            map.put("tkCommFee", tkCommFee * 0.8);
            map.put("couponInfo", couponInfo);
            return map;
        }
        map.put("tkCommFee", tkCommFee * 0.8);
        map.put("koulin", kouling);
        return map;
    }
}