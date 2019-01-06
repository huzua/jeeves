package com.cherry.jeeves.utils.Alimama.Service;

import com.cherry.jeeves.http.HttpApiService;
import com.cherry.jeeves.utils.Alimama.pojo.ExtendMessage;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetExtendMessage {

    //后期传入参数多的话可做扩展
    public static ExtendMessage getExtendMessage(String url,String args){
        String SrequestUrl = url+"?"+args;
        HttpApiService httpApiService = new HttpApiService();
        try {
            String emjsaondata = httpApiService.doGet(SrequestUrl);
            JSONObject messagebody = new JSONObject(emjsaondata);
            JSONObject messagedata = (JSONObject) messagebody.get("data");
            JSONArray otherAdzone = messagedata.getJSONArray("otherAdzones");
            JSONObject otherAdazoneobj = otherAdzone.getJSONObject(0);
            String boothid = otherAdazoneobj.getString("id");
            String accountid = messagedata.getJSONArray("otherList").getJSONObject(0).getLong("memberid")+"";
            String site = otherAdazoneobj.getJSONArray("sub").getJSONObject(0).getLong("id")+"";
            return new ExtendMessage(boothid,accountid,site);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
