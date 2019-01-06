package com.cherry.jeeves.utils.Alimama.pojo;

public class ExtendMessage {
    //推广位
    private String boothId;
    //账号
    private String account;
    //账户站点
    private String site;

    public ExtendMessage() {
    }

    public ExtendMessage(String boothId, String account, String site) {
        this.boothId = boothId;
        this.account = account;
        this.site = site;
    }

    public String getBoothId() {
        return boothId;
    }

    public void setBoothId(String boothId) {
        this.boothId = boothId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
