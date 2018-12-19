package com.cherry.jeeves.utils.trulingRobot;

import com.cherry.jeeves.domain.turlingRobot.ChatMessage;
import com.cherry.jeeves.domain.turlingRobot.Result;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by 胡振华 on 2017/11/10.
 */

public class HttpUtils {
    private static final String URL = "http://www.tuling123.com/openapi/api";
    private static final String API_KEY = "3b5799e78f634dcda736fa2d4be6bb03";

    public static ChatMessage sendMessage(String msg){
        ChatMessage chatMessage = new ChatMessage();
        String jsonRes= doGet(msg,null);
        Gson gson = new Gson();
        Result result = null;
        try {
            result = gson.fromJson(jsonRes,Result.class);
            chatMessage.setMsg(result.getText());
        }catch (JsonSyntaxException e){
            chatMessage.setMsg("抱歉，掉线了！");
        }
        chatMessage.setType(ChatMessage.Type.INCOMING);
        return chatMessage;
    }
    public static String doGet(String msg, String url){
        String result="";
        if(url==null){
            url = setParams(msg);
        }
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            java.net.URL urlNet = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)urlNet.openConnection();

            conn.setReadTimeout(5*1000);
            conn.setConnectTimeout(5*1000);
            conn.setRequestMethod("GET");
            //拿到服务器返回的InputStream
            is = conn.getInputStream();
            int len = -1;
            //声明一个缓冲区，为128个字节
            byte[] buf = new byte[128];
            //捕获缓冲区的数据转化为字节数组
           baos = new ByteArrayOutputStream();

            while((len = is.read(buf))!=-1){
                baos.write(buf,0,len);
            }
            //清除缓冲区，写到内存
            baos.flush();
            result = new String(baos.toByteArray());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            result = "抱歉！掉线了！";

        }finally{
            if(baos!=null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static String setParams(String msg) {
        String url = "";
        try {
            url = URL + "?key=" + API_KEY + "&info="
                     + URLEncoder.encode(msg,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
