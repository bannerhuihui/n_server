package com.huihui.netty.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserPojo implements Serializable {

    private static final long serialVersionUID = -6372749224209391484L;

    private String from; //用户第一次登录的端口

    private int userId; //用户id

    private int ShopId; //店铺名称

    private String userName; //用户名称

    private String shopName; //店铺名称

    private String passWord; //私钥

    private long createTime; //创建时间

    private long lastUpdTime; //最后的活跃时间

    private List<Object> attachments; //其他信息存储

    private int state; //登录过的状态

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastUpdTime() {
        return lastUpdTime;
    }

    public void setLastUpdTime(long lastUpdTime) {
        this.lastUpdTime = lastUpdTime;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public UserPojo(){
        this.createTime = new Date().getTime();
    }
}
