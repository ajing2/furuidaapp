package com.furuida.model;

public class CashHistory {
    private Long id;

    private String userId;

    private Integer money;

    private String accNum;

    private String time;
    private String openid;

    private Integer state;


    private String webchat;

    private String phone;

    private String receiveName;

    private String receiveAddr;

    private String parentId;

    /**
     * 职务
     * level1:组长，level2:，主管，level3:副经理，level4:经理，level5:总经理
     */
    private int level;

    private String webchatUrl;

    private String webchatName;

    private int ispayed;

    public String getWebchat() {
        return webchat;
    }

    public void setWebchat(String webchat) {
        this.webchat = webchat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveAddr() {
        return receiveAddr;
    }

    public void setReceiveAddr(String receiveAddr) {
        this.receiveAddr = receiveAddr;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getWebchatUrl() {
        return webchatUrl;
    }

    public void setWebchatUrl(String webchatUrl) {
        this.webchatUrl = webchatUrl;
    }

    public String getWebchatName() {
        return webchatName;
    }

    public void setWebchatName(String webchatName) {
        this.webchatName = webchatName;
    }

    public int getIspayed() {
        return ispayed;
    }

    public void setIspayed(int ispayed) {
        this.ispayed = ispayed;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum == null ? null : accNum.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}