package com.furuida.model;

public class User {
    private Long id;

    private String userId;

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

    public String getWebchat() {
        return webchat;
    }

    public void setWebchat(String webchat) {
        this.webchat = webchat == null ? null : webchat.trim();
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
        this.receiveName = receiveName == null ? null : receiveName.trim();
    }

    public String getReceiveAddr() {
        return receiveAddr;
    }

    public void setReceiveAddr(String receiveAddr) {
        this.receiveAddr = receiveAddr == null ? null : receiveAddr.trim();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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
}