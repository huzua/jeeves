package com.cherry.jeeves.utils.Alimama;

public class Template {
    private static String mes;
    public static String getMessage(String couponInfo,String fee,String koulin,String coup){
        mes = "奖励金: "+fee+"\n"+
                "--------------" +"\n";
        if(couponInfo!=null){
            mes = "优惠卷: "+couponInfo+"\n"+
                    mes;
        }
        if(coup!=null){
            mes = mes +"复制 "+ coup+" 后打开淘宝，点击进入弹窗，下单成功即可领取奖励金。";
        }else{
            mes = mes +"复制 "+ koulin+" 后打开淘宝，点击进入弹窗，下单成功即可领取奖励金。";
        }
        return mes;
    }
}
